package com.prayag.ai_gateway.service;

import com.prayag.ai_gateway.entity.LLMLog;
import com.prayag.ai_gateway.repository.LLMLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoggingService {

    private final LLMLogRepository logRepository;

    public void log(
            String user,
            String prompt,
            String response,
            String model,
            long responseTime
    ) {
        LLMLog log = new LLMLog();
        log.setUserId(user);
        log.setPrompt(prompt);
        log.setResponse(response);
        log.setModel(model);
        log.setResponseTimeMs(responseTime);
        log.setCreatedAt(LocalDateTime.now());

        logRepository.save(log);
    }
}