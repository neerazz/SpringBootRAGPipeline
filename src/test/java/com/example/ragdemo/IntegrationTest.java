package com.example.ragdemo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".+")
class IntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testEndToEndFlow() {
        ResponseEntity<Map> healthResponse = restTemplate.getForEntity(
            "http://localhost:" + port + "/api/health",
            Map.class
        );

        assertThat(healthResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(healthResponse.getBody()).containsKey("status");
        assertThat(healthResponse.getBody().get("status")).isEqualTo("UP");

        String question = "What is the password policy?";
        ResponseEntity<Map> askResponse = restTemplate.getForEntity(
            "http://localhost:" + port + "/api/ask?question=" + question,
            Map.class
        );

        assertThat(askResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(askResponse.getBody()).containsKey("question");
        assertThat(askResponse.getBody()).containsKey("answer");
        assertThat(askResponse.getBody()).containsKey("documentsUsed");
    }
}
