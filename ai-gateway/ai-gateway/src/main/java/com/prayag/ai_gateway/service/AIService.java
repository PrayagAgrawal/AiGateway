package com.prayag.ai_gateway.service;

import com.prayag.ai_gateway.client.GeminiClient;
import com.prayag.ai_gateway.dto.AIRequest;
import com.prayag.ai_gateway.dto.AIResponse;
import com.prayag.ai_gateway.service.llm.LLMClient;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AIService {

    private final List<LLMClient> clients;
    private Map<String, LLMClient> clientMap;
    private final IdempotencyService idempotencyService;
    private final RateLimiterService rateLimiterService;
    private final CacheService cacheService;
    private final LoggingService loggingService;
    private final RoutingService routingService;
    private final RagService ragService;

    @PostConstruct
    public void init() {
        clientMap = clients.stream()
                .collect(Collectors.toMap(LLMClient::getName, c -> c));
    }



    public AIResponse generate(AIRequest request, String key) {

        String user = (String) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        rateLimiterService.checkLimit(user);

        // RAG: modify prompt with context
        String finalPrompt = request.getPrompt();

        if (Boolean.TRUE.equals(request.getUseRag())) {
            String context = ragService.getContext(request.getPrompt());

            if (!context.isEmpty()) {
                finalPrompt = "Use the following context to answer:\n"
                        + context
                        + "\nQuestion: "
                        + request.getPrompt();
            }
        }

        long start = System.currentTimeMillis();

        // STEP 1: Check real cache (based on prompt)
        String cachedResponse = cacheService.get(finalPrompt);
        if (cachedResponse != null) {

            long time = System.currentTimeMillis() - start;

            loggingService.log(
                    user,
                    request.getPrompt(),
                    cachedResponse,
                    "CACHE",
                    time
            );

            return new AIResponse(cachedResponse);
        }

        // STEP 2: Idempotency (optional layer)
        if (key != null) {
            String cached = idempotencyService.get(key);
            if (cached != null) {
                return new AIResponse(cached);
            }
        }

        // STEP 3: Call LLM
        // Decide model
        String model = routingService.chooseModel(finalPrompt);

        // Call selected model
        AIRequest newRequest = new AIRequest();
        newRequest.setPrompt(finalPrompt);
        newRequest.setModel(request.getModel());
        newRequest.setTemperature(request.getTemperature());

        LLMClient client = clientMap.get(model);

        if (client == null) {
            throw new RuntimeException("Model not supported: " + model);
        }

        String result;

        try {
            // PRIMARY CALL
            result = client.call(newRequest, model);

        } catch (Exception e) {

            System.out.println("Primary model failed: " + model);

            // FALLBACK LOGIC
            String fallbackModel = getFallbackModel(model);
            LLMClient fallbackClient = clientMap.get(fallbackModel);

            if (fallbackClient == null) {
                throw new RuntimeException("Fallback model not available");
            }

            System.out.println("Switching to fallback: " + fallbackModel);

            result = fallbackClient.call(request, fallbackModel);
        }

        long time = System.currentTimeMillis() - start;

        // STEP 4: Store in both caches
        cacheService.save(finalPrompt, result);
        if (key != null) {
            idempotencyService.save(key, result);
        }

        // LOGGING (IMPORTANT)
        loggingService.log(
                user,
                request.getPrompt(),
                result,
                model + " (fallback used)",
                time
        );

        return new AIResponse(result);
    }

    private String getFallbackModel(String model) {

        if ("gemini".equals(model)) {
            return "gemini";
        }

        if ("openai".equals(model)) {
            return "gemini";
        }

        return "gemini"; // default fallback
    }
}