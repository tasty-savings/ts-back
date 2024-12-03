package com.example.testysavingsbe.domain.recipe.port;

import com.example.testysavingsbe.domain.recipe.dto.request.LeftoverCookingRequest;
import com.example.testysavingsbe.domain.recipe.dto.request.SimplifyRecipeToAiRequest;
import com.example.testysavingsbe.domain.recipe.dto.response.AIRecipeResponse;
import com.example.testysavingsbe.domain.recipe.service.usecase.RecipeQueryUseCase.RecipeFromIngredientsRequest;
import java.util.List;
import java.util.Map;
import reactor.core.publisher.Mono;

public interface AiWebClientAdapter {

    AIRecipeResponse requestCreateRecipeUseIngredients(RecipeFromIngredientsRequest request,
        Mono<LeftoverCookingRequest> aiRequest);

    AIRecipeResponse requestRecipeMakeSimplify(String recipeId,
        SimplifyRecipeToAiRequest request);

    List<String> requestRecommendRecipeList(Map<String, List<String>> request);

    void requestRecipeForUserNutrition();

}
