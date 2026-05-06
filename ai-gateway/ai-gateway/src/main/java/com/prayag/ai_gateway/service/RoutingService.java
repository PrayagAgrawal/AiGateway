package com.prayag.ai_gateway.service;

import org.springframework.stereotype.Service;

@Service
public class RoutingService {

    public String chooseModel(String prompt) {

        prompt = prompt.toLowerCase();

        // Coding / complex → OpenAI (mock)
        if (prompt.contains("code") || prompt.contains("java") || prompt.contains("algorithm")) {
            return "gemini";
        }

        // Short/simple → Gemini
        if (prompt.length() < 50) {
            return "gemini";
        }

        // Default → Gemini
        return "gemini";
    }
}