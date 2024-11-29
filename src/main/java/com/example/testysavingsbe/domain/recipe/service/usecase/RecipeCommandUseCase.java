package com.example.testysavingsbe.domain.recipe.service.usecase;

import com.example.testysavingsbe.domain.recipe.dto.request.EatRecipeRequest;
import com.example.testysavingsbe.domain.recipe.dto.request.SaveCustomRecipeRequest;
import com.example.testysavingsbe.domain.recipe.dto.response.SharedRecipeResponse;
import com.example.testysavingsbe.domain.recipe.entity.BookmarkedRecipe;
import com.example.testysavingsbe.domain.recipe.entity.CustomRecipe;
import com.example.testysavingsbe.domain.recipe.entity.UserEaten;
import com.example.testysavingsbe.domain.user.entity.User;


public interface RecipeCommandUseCase {

    BookmarkedRecipe bookmarkRecipe(User user, String recipeId);

    UserEaten checkEatRecipe(User user, EatRecipeRequest request);

    CustomRecipe saveCustomRecipe(User user, SaveCustomRecipeRequest request);

    void removeEatenRecipe(User user, String recipeId);

    SharedRecipeResponse generateCustomRecipeShareUrl(User user, String recipeId);

    void deleteSharedRecipe(String customRecipeId);

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
