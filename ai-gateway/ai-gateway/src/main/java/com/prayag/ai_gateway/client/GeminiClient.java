package com.prayag.ai_gateway.client;

import com.prayag.ai_gateway.dto.AIRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.prayag.ai_gateway.service.llm.LLMClient;

import java.util.*;

@Component
@RequiredArgsConstructor
@Service
public class GeminiClient implements LLMClient {

    @Override
    public String getName() {
        return "gemini";
    }
    @Value("${gemini.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public String call(AIRequest request, String model) {

        String modelName;

        if (model.equals("GEMINI_PRO")) {
            modelName = "gemini-2.5-flash";
        } else {
            modelName = "gemini-2.5-flash-lite";
        }

        String url = "https://generativelanguage.googleapis.com/v1beta/models/" + modelName + ":generateContent";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-goog-api-key", apiKey);

        Map<String, Object> part = Map.of("text", request.getPrompt());

        Map<String, Object> content = Map.of(
                "parts", List.of(part)
        );

        Map<String, Object> body = Map.of(
                "contents", List.of(content)
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response;

        try {
            response = restTemplate.postForEntity(url, entity, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Gemini API call failed: " + e.getMessage());
        }

        // extract response
        Map bodyMap = response.getBody();

        if (bodyMap == null || !bodyMap.containsKey("candidates")) {
            throw new RuntimeException("Invalid response from Gemini: " + bodyMap);
        }


        List<Map> candidates = (List<Map>) bodyMap.get("candidates");
        Map first = candidates.get(0);
        Map contentMap = (Map) first.get("content");
        List<Map> parts = (List<Map>) contentMap.get("parts");

        return (String) parts.get(0).get("text");
    }
}