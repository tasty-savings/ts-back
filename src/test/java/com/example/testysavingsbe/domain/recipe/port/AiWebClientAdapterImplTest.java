package com.example.testysavingsbe.domain.recipe.port;


import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;


class AiWebClientAdapterImplTest {

    private AiWebClientAdapterImpl aiService;
    private WebClient webClient;
    private MockWebServer server;

    private void startServer(ClientHttpConnector connector) {
        this.server = new MockWebServer();
        this.webClient = WebClient.builder()
            .clientConnector(connector)
            .baseUrl(server.url("/ai").toString())
            .build();
        this.aiService = new AiWebClientAdapterImpl(webClient);
    }

    @AfterEach
    void shutDownServer() throws IOException {
        if (server != null) {
            this.server.shutdown();
        }
    }

    @Test
    @DisplayName("추천 레시피 id를 정상적으로 가져온다")
    void requestRecommendRecipeSuccess() throws JsonProcessingException {
        startServer(new ReactorClientHttpConnector());
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> responseBody = List.of("Recipe1", "Recipe2", "Recipe3");
        String jsonBody = objectMapper.writeValueAsString(responseBody);

        prepareResponse(response -> response.setResponseCode(200)
            .addHeader("Content-Type", "application/json")
            .setBody(jsonBody));
        Map<String, List<String>> recommendRequest = new HashMap<>();
        recommendRequest.put("search_types", List.of("건강식"));

        List<String> recommendRecipeResponse = aiService.requestRecommendRecipeList(
            recommendRequest);

        expectRequest(request -> {
            assertThat(request.getPath()).isEqualTo("/ai/recommend");
            assertThat(request.getMethod()).isEqualTo("POST");
            assertThat(recommendRecipeResponse).containsExactly("Recipe1", "Recipe2", "Recipe3");
        });
    }

    @Test
    @DisplayName("AI 서버에서 500 에러가 나온다면 빈 리스트를 준다.")
    void statusHandleRecommendRecipeList() {
        startServer(new ReactorClientHttpConnector());
        // given
        prepareResponse(response -> response
            .setResponseCode(500)
        );

        // when
        Map<String, List<String>> recommendRequest = new HashMap<>();
        recommendRequest.put("search_types", List.of("건강식"));

        List<String> result = aiService.requestRecommendRecipeList(recommendRequest);

        assertThat(result).isEmpty();

        expectRequest(request -> {
            assertThat(request.getPath()).isEqualTo("/ai/recommend");
            assertThat(request.getMethod()).isEqualTo("POST");
        });
    }

    private void prepareResponse(Consumer<MockResponse> consumer) {
        MockResponse response = new MockResponse();
        consumer.accept(response);
        server.enqueue(response);
    }

    private void expectRequest(Consumer<RecordedRequest> consumer) {
        try {
            consumer.accept(this.server.takeRequest());
        } catch (InterruptedException ex) {
            throw new IllegalStateException(ex);
        }
    }

}