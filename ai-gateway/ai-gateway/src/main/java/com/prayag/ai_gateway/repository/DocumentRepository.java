package com.prayag.ai_gateway.repository;

import com.prayag.ai_gateway.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query(value = """
    SELECT * FROM documents
    ORDER BY embedding <-> CAST(:embedding AS vector)
    LIMIT 5
""", nativeQuery = true)
    List<Document> findTop5ByEmbedding(@Param("embedding") String embedding);
}