package com.prayag.ai_gateway.service;

import com.prayag.ai_gateway.entity.Document;
import com.prayag.ai_gateway.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final EmbeddingService embeddingService;

    public Document save(String content) {

        // 🧠 Generate embedding
        float[] embedding = embeddingService.embed(content);

        // 📦 Create document
        Document document = new Document();
        document.setContent(content);
        document.setEmbedding(embedding);

        // 💾 Save to DB
        return documentRepository.save(document);
    }
}