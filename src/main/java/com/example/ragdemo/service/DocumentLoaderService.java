package com.example.ragdemo.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DocumentLoaderService {

    @Autowired
    private VectorStore vectorStore;

    public void addDocument(String content, Map<String, Object> metadata) {
        Document document = new Document(content, metadata);
        vectorStore.add(List.of(document));
    }

    public void addDocuments(List<String> contents) {
        List<Document> docs = contents.stream()
                .map(Document::new)
                .collect(Collectors.toList());
        vectorStore.add(docs);
    }

    public void removeDocument(String documentId) {
        // Implementation depends on your vector store
    }

    public void clearAllDocuments() {
        // Implementation depends on your vector store
        System.out.println("Document clearing not supported in SimpleVectorStore");
    }
}
