package com.example.ragdemo.controller;

import com.example.ragdemo.controller.QAController.QAResponse;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class QAControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VectorStore vectorStore;

    @MockBean
    private ChatClient chatClient;

    @Test
    void testAskWithRelevantDocuments() throws Exception {
        String question = "What is the password policy?";
        List<Document> mockDocuments = Arrays.asList(
            new Document("Password must be minimum 12 characters"),
            new Document("Must contain uppercase, lowercase, numbers, and special characters")
        );
        when(vectorStore.similaritySearch(any(SearchRequest.class)))
            .thenReturn(mockDocuments);
        Generation generation = new Generation("Passwords must be at least 12 characters long and contain a mix of character types.");
        ChatResponse chatResponse = new ChatResponse(List.of(generation));
        when(chatClient.call(any(Prompt.class))).thenReturn(chatResponse);

        mockMvc.perform(get("/api/ask").param("question", question))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.question").value(question))
                .andExpect(jsonPath("$.answer").exists())
                .andExpect(jsonPath("$.documentsUsed").value(2));
    }

    @Test
    void testAskWithNoRelevantDocuments() throws Exception {
        String question = "What is the meaning of life?";
        when(vectorStore.similaritySearch(any(SearchRequest.class)))
            .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/ask").param("question", question))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.question").value(question))
                .andExpect(jsonPath("$.answer").value("I couldn't find any relevant information in the knowledge base to answer your question."))
                .andExpect(jsonPath("$.documentsUsed").value(0));
    }

    @Test
    void testHealthEndpoint() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.service").value("RAG Q&A API"));
    }
}
