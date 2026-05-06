package com.prayag.ai_gateway.repository;

import com.prayag.ai_gateway.entity.LLMLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LLMLogRepository extends JpaRepository<LLMLog, Long> {
}