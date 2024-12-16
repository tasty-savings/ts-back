package com.example.testysavingsbe.domain.recipe.port;

import com.example.testysavingsbe.domain.recipe.dto.request.AIGenerateBasedOnNutrientsRequest;
import com.example.testysavingsbe.domain.recipe.dto.request.LeftoverCookingRequest;
import com.example.testysavingsbe.domain.recipe.dto.request.SimplifyRecipeToAiRequest;
import com.example.testysavingsbe.domain.recipe.dto.response.AIRecipe;
import com.example.testysavingsbe.domain.recipe.dto.response.NutritionBasedRecipeCreateResponse;
import com.example.testysavingsbe.domain.recipe.service.usecase.RecipeQueryUseCase.RecipeFromIngredientsRequest;
import java.util.List;
import java.util.Map;
import reactor.core.publisher.Mono;

public interface AiWebClientAdapter {

    AIRecipe requestCreateRecipeUseIngredients(RecipeFromIngredientsRequest request,
        Mono<LeftoverCookingRequest> aiRequest);

    AIRecipe requestRecipeMakeSimplify(String recipeId,
        SimplifyRecipeToAiRequest request);

    List<String> requestRecommendRecipeList(Map<String, List<String>> request);


    NutritionBasedRecipeCreateResponse requestRecipeForUserNutrition(String recipeId, AIGenerateBasedOnNutrientsRequest request);
}
