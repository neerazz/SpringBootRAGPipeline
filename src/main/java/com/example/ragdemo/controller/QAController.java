package com.example.ragdemo.controller;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class QAController {
    
    @Autowired
    private ChatClient chatClient;
    
    @Autowired
    private VectorStore vectorStore;
    
    @Value("classpath:prompts/qa-prompt.st")
    private Resource promptResource;
    
    @GetMapping("/ask")
    public QAResponse ask(@RequestParam String question) {
        List<Document> relevantDocs = vectorStore.similaritySearch(
                SearchRequest.query(question).withTopK(5)
        );
        
        if (relevantDocs.isEmpty()) {
            return new QAResponse(
                    question,
                    "I couldn't find any relevant information in the knowledge base to answer your question.",
                    0
            );
        }
        
        String context = relevantDocs.stream()
                .map(Document::getContent)
                .collect(Collectors.joining("\n\n"));
        
        String promptText = loadPromptTemplate();
        PromptTemplate promptTemplate = new PromptTemplate(promptText);
        Prompt prompt = promptTemplate.create(Map.of(
                "context", context,
                "question", question
        ));
        
        ChatResponse response = chatClient.call(prompt);
        String answer = response.getResult().getOutput().getContent();
        
        return new QAResponse(question, answer, relevantDocs.size());
    }
    
    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP", "service", "RAG Q&A API");
    }
    
    private String loadPromptTemplate() {
        try {
            return promptResource.getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            return """
                    You are a helpful security assistant. Answer the following question based ONLY on the provided context.
                    If the answer is not in the context, say 'I don't have enough information to answer that.'
                    
                    CONTEXT:
                    {context}
                    
                    QUESTION:
                    {question}
                    
                    ANSWER:
                    """;
        }
    }
    
    public record QAResponse(String question, String answer, int documentsUsed) {}
}
