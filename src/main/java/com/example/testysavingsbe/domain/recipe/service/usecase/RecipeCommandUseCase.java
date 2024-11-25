package com.example.testysavingsbe.domain.recipe.service.usecase;

import com.example.testysavingsbe.domain.recipe.dto.request.EatRecipeRequest;
import com.example.testysavingsbe.domain.recipe.dto.request.SaveCustomRecipeRequest;
import com.example.testysavingsbe.domain.recipe.dto.response.AIChangeRecipeResponse;
import com.example.testysavingsbe.domain.recipe.entity.BookmarkedRecipe;
import com.example.testysavingsbe.domain.recipe.entity.CustomRecipe;
import com.example.testysavingsbe.domain.recipe.entity.UserEaten;
import com.example.testysavingsbe.domain.user.entity.User;
import lombok.Builder;

import java.util.List;


public interface RecipeCommandUseCase {

    BookmarkedRecipe bookmarkRecipe(User user, String recipeId);

    UserEaten checkEatRecipe(User user, EatRecipeRequest request);

    // todo
    // 1. 냉장고 파먹기 기능
    AIChangeRecipeResponse createRecipeFromIngredients(User user,
        RecipeFromIngredientsRequest request);

    CustomRecipe saveCustomRecipe(User user, SaveCustomRecipeRequest request);
    // 2. 레시피 간소화

    @Builder
    record RecipeFromIngredientsRequest(
        String originalRecipeId,
        List<String> dislikeIngredients,
        List<String> basicSeasoning,
        List<String> mustUseIngredients
    ) {

    }

    record RecipeGenerateServiceRequest(
        User user,
        String recipeName
    ) {

    }

    record RecipeUpdateServiceRequest(
        Long recipeId
    ) {

    }

}
