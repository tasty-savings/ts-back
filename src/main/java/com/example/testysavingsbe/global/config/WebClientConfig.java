package com.example.testysavingsbe.global.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Slf4j
public class WebClientConfig {
    @Value("${server_host.ai}")
    String AI_URL;

    @Bean
    public WebClient aiWebClient() {
        return WebClient.builder()
                .baseUrl(AI_URL)
                .defaultHeader("Accept", "application/json")
                .filter((request, next) -> {
                    log.info("Request: " + request.method() + " " + request.url());
                    return next.exchange(request);
                }) // 요청 로깅
                .filter(((request, next) -> {
                    log.info("Request: " + request.method() + " " + request.url());
                    return next.exchange(request);
                })) // 응답 로깅
                .build();
    }
}
