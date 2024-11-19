package com.example.testysavingsbe.domain.recipe.service.usecase;

import com.example.testysavingsbe.domain.recipe.dto.response.RecipeResponse;
import com.example.testysavingsbe.domain.user.entity.User;


public interface RecipeCommandUseCase {
    RecipeResponse generateRecipe(RecipeGenerateServiceRequest request);

    RecipeResponse checkEatRecipe(RecipeUpdateServiceRequest request);

    RecipeResponse bookmarkRecipe(RecipeUpdateServiceRequest request);

    // todo
    // 1. 냉장고 파먹기 기능
    void createRecipeFromIngredients(User user);
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
