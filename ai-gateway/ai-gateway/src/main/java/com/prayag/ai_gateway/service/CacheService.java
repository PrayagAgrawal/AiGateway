package com.prayag.ai_gateway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class CacheService {

    private final StringRedisTemplate redisTemplate;

    private static final String PREFIX = "cache:";
    private static final Duration TTL = Duration.ofHours(6);

    public String get(String prompt) {
        String key = PREFIX + hash(prompt);
        return redisTemplate.opsForValue().get(key);
    }

    public void save(String prompt, String response) {
        String key = PREFIX + hash(prompt);
        redisTemplate.opsForValue().set(key, response, TTL);
    }

    private String hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();

        } catch (Exception e) {
            throw new RuntimeException("Hashing failed");
        }
    }
}