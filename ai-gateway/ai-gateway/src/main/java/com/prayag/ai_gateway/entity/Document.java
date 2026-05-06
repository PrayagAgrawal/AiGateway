package com.prayag.ai_gateway.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "documents")
@Data
public class Document {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 5000)
    private String content;

    @Column(columnDefinition = "vector(384)")
    private float[] embedding;
}