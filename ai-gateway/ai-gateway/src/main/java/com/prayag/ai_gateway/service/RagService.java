package com.prayag.ai_gateway.service;

import com.prayag.ai_gateway.entity.Document;
import com.prayag.ai_gateway.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RagService {

    private final DocumentRepository documentRepository;
    private final EmbeddingService embeddingService;

    public String getContext(String query) {

        float[] queryEmbedding = embeddingService.embed(query);

        if (queryEmbedding == null) {
            return "";
        }

        String embeddingStr = toPgVector(queryEmbedding);

        List<Document> docs = documentRepository
                .findTop5ByEmbedding(embeddingStr);

        if (docs.isEmpty()) {
            return "";
        }

        StringBuilder context = new StringBuilder();

        for (Document doc : docs) {
            context.append(doc.getContent()).append("\n---\n");
        }

        return context.toString();
    }

    private String toPgVector(float[] vector) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < vector.length; i++) {
            sb.append(vector[i]);
            if (i != vector.length - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }
}