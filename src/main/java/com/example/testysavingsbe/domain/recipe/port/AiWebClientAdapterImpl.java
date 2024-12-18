package com.example.testysavingsbe.domain.recipe.port;

import com.example.testysavingsbe.domain.recipe.dto.request.AIGenerateBasedOnNutrientsRequest;
import com.example.testysavingsbe.domain.recipe.dto.request.BasedOnNutrientsRequest;
import com.example.testysavingsbe.domain.recipe.dto.request.LeftoverCookingRequest;
import com.example.testysavingsbe.domain.recipe.dto.request.SimplifyRecipeToAiRequest;
import com.example.testysavingsbe.domain.recipe.dto.response.AIRecipe;
import com.example.testysavingsbe.domain.recipe.dto.response.AIRecipeResponse;
import com.example.testysavingsbe.domain.recipe.dto.response.NutritionBasedRecipeCreateResponse;
import com.example.testysavingsbe.domain.recipe.service.usecase.RecipeQueryUseCase.RecipeFromIngredientsRequest;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Slf4j
@Service
@RequiredArgsConstructor
public class AiWebClientAdapterImpl implements AiWebClientAdapter {
    private static final int USE_ALL_INGREDIENTS_REQUEST_TYPE = 1;
    private static final int SIMPLIFY_RECIPE_REQUEST_TYPE = 2;
    private static final int BASED_ON_NUTRIENTS_REQUEST_TYPE = 3;
    private static final int AI_REQUEST_TIMEOUT_MINUTE = 2;


    private final WebClient aiWebClient;

    @Override
    public AIRecipe requestCreateRecipeUseIngredients(RecipeFromIngredientsRequest request,
        Mono<LeftoverCookingRequest> aiRequest) {

        return aiWebClient.post()
            .uri(urlBuilder -> urlBuilder.path("/recipe")
                .queryParam("recipe_change_type", USE_ALL_INGREDIENTS_REQUEST_TYPE)
                .queryParam("recipe_info_index",
                    request.originalRecipeId())
                .build())
            .body(aiRequest, LeftoverCookingRequest.class)
            .exchangeToMono(response -> {
                log.info("Status code: {}", response.statusCode());
                log.info(response.toString());
                return response.bodyToMono(AIRecipeResponse.class);
            })
            .block();
    }

    @Override
    public AIRecipe requestRecipeMakeSimplify(String recipeId,
        SimplifyRecipeToAiRequest request) {
        return aiWebClient.post()
            .uri(urlBuilder -> urlBuilder.path("/recipe")
                .queryParam("recipe_change_type", SIMPLIFY_RECIPE_REQUEST_TYPE)
                .queryParam("recipe_info_index", recipeId)
                .build())
            .body(Mono.just(request), AIRecipeResponse.class)
            .retrieve()
            .bodyToMono(AIRecipeResponse.class)
            .block();
    }

    @Override
    public List<String> requestRecommendRecipeList(Map<String, List<String>> request) {
        return aiWebClient.post()
            .uri(uriBuilder -> uriBuilder.path("/recommend").build())
            .bodyValue(request)
            .exchangeToMono(response -> {
                if (response.statusCode().is2xxSuccessful()) {
                    return response.bodyToMono(new ParameterizedTypeReference<List<String>>() {});
                } else {
                    return Mono.just(Collections.emptyList());
                }
            })
            .block();
    }

    @Override
    public NutritionBasedRecipeCreateResponse requestRecipeForUserNutrition(String recipeId, AIGenerateBasedOnNutrientsRequest request) {
        return aiWebClient.post()
            .uri(urlBuilder -> urlBuilder.path("/recipe")
                .queryParam("recipe_change_type", BASED_ON_NUTRIENTS_REQUEST_TYPE)
                .queryParam("recipe_info_index", recipeId)
                .build())
            .body(Mono.just(request), AIGenerateBasedOnNutrientsRequest.class)
            .retrieve()
            .bodyToMono(NutritionBasedRecipeCreateResponse.class)
            .timeout(Duration.ofMinutes(AI_REQUEST_TIMEOUT_MINUTE))
            .block();
    }
}