package com.example.ragdemo.config;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class VectorStoreConfig {
    
    @Bean
    public VectorStore vectorStore(EmbeddingClient embeddingClient) {
        return new SimpleVectorStore(embeddingClient);
    }
    
    @Bean
    ApplicationRunner documentLoader(VectorStore vectorStore, ResourceLoader resourceLoader) {
        return args -> {
            System.out.println("Loading documents into vector store...");
            
            Resource[] resources = ResourcePatternUtils
                    .getResourcePatternResolver(resourceLoader)
                    .getResources("classpath:docs/*.md");
            
            TokenTextSplitter textSplitter = new TokenTextSplitter();
            List<Document> documents = new ArrayList<>();
            
            for (Resource resource : resources) {
                System.out.println("Loading document: " + resource.getFilename());
                
                TextReader textReader = new TextReader(resource);
                textReader.setCharset(StandardCharsets.UTF_8);
                
                List<Document> splitDocuments = textSplitter.apply(textReader.get());
                documents.addAll(splitDocuments);
            }
            
            vectorStore.add(documents);
            
            System.out.println("Successfully loaded " + resources.length + 
                    " documents into vector store (split into " + documents.size() + " chunks)");
        };
    }
}
