package com.prayag.ai_gateway.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "llm_logs")
@Data
public class LLMLog {

    @Id
    @GeneratedValue
    private Long id;

    private String userId;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String prompt;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String response;

    private String model;

    private Long responseTimeMs;

    private LocalDateTime createdAt;
}