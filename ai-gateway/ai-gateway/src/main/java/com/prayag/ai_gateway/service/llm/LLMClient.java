package com.prayag.ai_gateway.service.llm;

import com.prayag.ai_gateway.dto.AIRequest;

public interface LLMClient {

    String getName(); // identifies model (gemini, openai, etc)

    String call(AIRequest request, String model);
}