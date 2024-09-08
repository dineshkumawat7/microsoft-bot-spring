package com.ebit.microsoft.bot.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GenerativeAIService {

    private final String API_URL = "https://api.openai.com/v1/chat/completions";
    private final String API_KEY = "your-openai-api-key";

    public String generateText(String userInput) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        // Chat model request body, using gpt-3.5-turbo
        String requestBody = "{ \"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"" + userInput + "\"}], \"max_tokens\": 150 }";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to generate text: " + response.getStatusCode());
        }
    }
}
