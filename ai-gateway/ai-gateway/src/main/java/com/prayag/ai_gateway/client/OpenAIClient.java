package com.prayag.ai_gateway.client;

import com.prayag.ai_gateway.dto.AIRequest;
import com.prayag.ai_gateway.service.llm.LLMClient;
import org.springframework.stereotype.Service;

@Service
public class OpenAIClient implements LLMClient {

    @Override
    public String getName() {
        return "openai";
    }

    @Override
    public String call(AIRequest request, String model) {
        return "[OpenAI MOCK RESPONSE] " + request.getPrompt();
    }
}