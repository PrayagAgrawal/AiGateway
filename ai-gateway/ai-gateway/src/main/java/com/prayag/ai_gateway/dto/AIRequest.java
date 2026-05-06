package com.prayag.ai_gateway.dto;

import lombok.Data;

@Data
public class AIRequest {
    private String prompt;
    private String model;
    private Double temperature;
    private Boolean useRag;
}