package com.example.testysavingsbe.domain.recipe.service.usecase;

import com.example.testysavingsbe.domain.recipe.dto.response.AIChangeRecipeResponse;
import com.example.testysavingsbe.domain.recipe.dto.response.CustomRecipeResponse;
import com.example.testysavingsbe.domain.recipe.dto.response.OriginalRecipeResponse;
import com.example.testysavingsbe.domain.recipe.dto.response.RecipeResponse;
import com.example.testysavingsbe.domain.user.entity.User;
import lombok.Builder;

import java.util.List;

public interface RecipeQueryUseCase {

    CustomRecipeResponse getCustomRecipe(User user, String id);

    AIChangeRecipeResponse createRecipeFromIngredients(User user,
        RecipeFromIngredientsRequest request);

    AIChangeRecipeResponse simplifyRecipe(User user, String recipeId);

    List<OriginalRecipeResponse> getRecommendedRecipe(User user);

    OriginalRecipeResponse getRecipeById(String id);

    List<CustomRecipeResponse> getCustomRecipeByUser(User user, int page, int pageSize);

    boolean checkBookmarked(User user, String recipeId);

    List<OriginalRecipeResponse> getBookmarkedRecipes(User user, int page, int pageSize);

    List<RecipeResponse> getAllEatenRecipe(User user, int page, int pageSize);

    CustomRecipeResponse getCustomRecipeBySharedLink(String uuid);

    List<OriginalRecipeResponse> searchRecipe(String recipeName);

    @Builder
    record RecipeFromIngredientsRequest(
        String originalRecipeId,
        List<String> dislikeIngredients,
        List<String> mustUseIngredients
    ) {

    }
}
