package com.example.testysavingsbe.domain.recipe.service.usecase;

import com.example.testysavingsbe.domain.recipe.dto.request.EatRecipeRequest;
import com.example.testysavingsbe.domain.recipe.dto.request.SaveCustomRecipeRequest;
import com.example.testysavingsbe.domain.recipe.dto.response.AIChangeRecipeResponse;
import com.example.testysavingsbe.domain.recipe.dto.response.CustomRecipeResponse;
import com.example.testysavingsbe.domain.recipe.dto.response.SharedRecipeResponse;
import com.example.testysavingsbe.domain.recipe.entity.UserEaten;
import com.example.testysavingsbe.domain.user.entity.User;
import java.util.List;


public interface RecipeCommandUseCase {

    void bookmarkRecipe(User user, String recipeId);

    UserEaten checkEatRecipe(User user, EatRecipeRequest request);

    CustomRecipeResponse saveCustomRecipe(User user, SaveCustomRecipeRequest request);

    void removeEatenRecipe(User user, String recipeId);

    SharedRecipeResponse generateCustomRecipeShareUrl(User user, String recipeId);

    void deleteSharedRecipe(String customRecipeId);

    AIChangeRecipeResponse generateRecipeBasedOnNutrients(User user, int mealsADay, String recipeId, List<String> userBasicSeasoning);

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
