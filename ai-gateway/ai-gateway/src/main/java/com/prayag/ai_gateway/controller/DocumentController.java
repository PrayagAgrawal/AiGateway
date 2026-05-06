package com.prayag.ai_gateway.controller;

import com.prayag.ai_gateway.entity.Document;
import com.prayag.ai_gateway.repository.DocumentRepository;
import com.prayag.ai_gateway.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/docs")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/add")
    public String add(@RequestBody String content) {
        documentService.save(content);
        return "Document saved with embedding";
    }
}