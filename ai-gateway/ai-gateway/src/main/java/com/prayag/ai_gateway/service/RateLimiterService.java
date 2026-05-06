package com.prayag.ai_gateway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RateLimiterService {

    private final StringRedisTemplate redisTemplate;

    private static final int LIMIT = 10;

    public void checkLimit(String userId) {

        String key = "rate:" + userId;

        Long count = redisTemplate.opsForValue().increment(key);

        if (count == 1) {
            redisTemplate.expire(key, Duration.ofMinutes(1));
        }

        if (count > LIMIT) {
            throw new RuntimeException("Rate limit exceeded");
        }
    }
}