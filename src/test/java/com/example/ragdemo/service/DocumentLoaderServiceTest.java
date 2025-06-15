package com.example.ragdemo.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
class DocumentLoaderServiceTest {

    @Autowired
    private DocumentLoaderService documentLoaderService;

    @MockBean
    private VectorStore vectorStore;

    @Test
    void testAddDocument() {
        String content = "Test security policy content";
        Map<String, Object> metadata = Map.of(
            "source", "test-policy.md",
            "type", "security-policy"
        );

        documentLoaderService.addDocument(content, metadata);

        ArgumentCaptor<List<Document>> captor = ArgumentCaptor.forClass(List.class);
        verify(vectorStore).add(captor.capture());

        List<Document> captured = captor.getValue();
        assertThat(captured).hasSize(1);
        assertThat(captured.get(0).getContent()).isEqualTo(content);
        assertThat(captured.get(0).getMetadata()).isEqualTo(metadata);
    }

    @Test
    void testAddMultipleDocuments() {
        List<String> contents = List.of("Policy 1", "Policy 2", "Policy 3");

        documentLoaderService.addDocuments(contents);

        ArgumentCaptor<List<Document>> captor = ArgumentCaptor.forClass(List.class);
        verify(vectorStore).add(captor.capture());

        List<Document> captured = captor.getValue();
        assertThat(captured).hasSize(3);
        assertThat(captured.stream().map(Document::getContent))
            .containsExactlyElementsOf(contents);
    }
}
