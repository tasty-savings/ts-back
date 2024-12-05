package com.example.testysavingsbe.domain.recipe.service.usecase;

import com.example.testysavingsbe.domain.recipe.dto.request.EatRecipeRequest;
import com.example.testysavingsbe.domain.recipe.dto.request.SaveCustomRecipeRequest;
import com.example.testysavingsbe.domain.recipe.dto.response.CustomRecipeResponse;
import com.example.testysavingsbe.domain.recipe.dto.response.SharedRecipeResponse;
import com.example.testysavingsbe.domain.recipe.entity.UserEaten;
import com.example.testysavingsbe.domain.user.entity.User;


public interface RecipeCommandUseCase {

    void bookmarkRecipe(User user, String recipeId);

    UserEaten checkEatRecipe(User user, EatRecipeRequest request);

    CustomRecipeResponse saveCustomRecipe(User user, SaveCustomRecipeRequest request);

    void removeEatenRecipe(User user, String recipeId);

    SharedRecipeResponse generateCustomRecipeShareUrl(User user, String recipeId);

    void deleteSharedRecipe(String customRecipeId);

    void generateRecipeBasedOnNutrients(User user, int mealsADay);

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
