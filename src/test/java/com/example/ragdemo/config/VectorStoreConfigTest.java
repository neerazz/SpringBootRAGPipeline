package com.example.ragdemo.config;

import org.junit.jupiter.api.Test;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.ai.openai.api-key=test-key"
})
class VectorStoreConfigTest {

    @MockBean
    private EmbeddingClient embeddingClient;

    @Test
    void testVectorStoreBean(ApplicationContext context) {
        VectorStore vectorStore = context.getBean(VectorStore.class);
        assertThat(vectorStore).isNotNull();
        assertThat(vectorStore).isInstanceOf(SimpleVectorStore.class);
    }
}
