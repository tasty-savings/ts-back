package com.example.testysavingsbe.domain.recipe.service.usecase;

import com.example.testysavingsbe.domain.recipe.dto.request.EatRecipeRequest;
import com.example.testysavingsbe.domain.recipe.dto.request.SaveCustomRecipeRequest;
import com.example.testysavingsbe.domain.recipe.entity.BookmarkedRecipe;
import com.example.testysavingsbe.domain.recipe.entity.CustomRecipe;
import com.example.testysavingsbe.domain.recipe.entity.UserEaten;
import com.example.testysavingsbe.domain.user.entity.User;


public interface RecipeCommandUseCase {
    BookmarkedRecipe bookmarkRecipe(User user, String recipeId);

    UserEaten checkEatRecipe(User user, EatRecipeRequest request);

    // todo
    // 1. 냉장고 파먹기 기능
    void createRecipeFromIngredients(User user);

    CustomRecipe saveCustomRecipe(User user, SaveCustomRecipeRequest request);
    // 2. 레시피 간소화

    record RecipeGenerateServiceRequest(
            User user,
            String recipeName
    ){

    }

    record RecipeUpdateServiceRequest(
            Long recipeId
    ){
    }

}
