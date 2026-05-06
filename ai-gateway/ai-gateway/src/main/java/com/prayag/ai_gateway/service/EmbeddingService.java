package com.prayag.ai_gateway.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class EmbeddingService {

    private final RestTemplate restTemplate = new RestTemplate();

    public float[] embed(String text) {

        String url = "http://embedding:8000/embed";

        Map<String, String> request = Map.of("text", text);

        Map response = restTemplate.postForObject(url, request, Map.class);

        var list = (java.util.List<Double>) response.get("embedding");

        float[] vector = new float[list.size()];

        for (int i = 0; i < list.size(); i++) {
            vector[i] = list.get(i).floatValue();
        }

        return vector;
    }
}