package com.prayag.ai_gateway.controller;

import com.prayag.ai_gateway.dto.AIRequest;
import com.prayag.ai_gateway.dto.AIResponse;
import com.prayag.ai_gateway.service.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AIController {

    private final AIService aiService;

    @PostMapping("/generate")
    public ResponseEntity<AIResponse> generate(
            @RequestBody AIRequest request,
            @RequestHeader(value = "Idempotency-Key", required = false) String key
    ) {
        return ResponseEntity.ok(aiService.generate(request, key));
    }
}