package com.ebit.microsoft.bot.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GenerativeAIService {
    private final WebClient webClient;

    public GenerativeAIService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1/engines/davinci/completions")
                .defaultHeader("Authorization", "Bearer YOUR_API_KEY")
                .build();
    }

    public Mono<String> generateResponse(String prompt) {
        return webClient.post()
                .bodyValue("{\"prompt\":\"" + prompt + "\",\"max_tokens\":50}")
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    // Parse the response and extract the generated text
                    return response;  // Customize the parsing based on your AI provider's response structure
                });
    }
}
