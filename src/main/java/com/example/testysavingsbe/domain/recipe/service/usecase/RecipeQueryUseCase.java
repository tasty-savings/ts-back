package com.example.testysavingsbe.domain.recipe.service.usecase;

import com.example.testysavingsbe.domain.recipe.dto.response.AIChangeRecipeResponse;
import com.example.testysavingsbe.domain.recipe.dto.response.EatenRecipeResponse;
import com.example.testysavingsbe.domain.recipe.entity.CustomRecipe;
import com.example.testysavingsbe.domain.recipe.entity.Recipe;
import com.example.testysavingsbe.domain.user.entity.User;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RecipeQueryUseCase {

    CustomRecipe getCustomRecipe(User user, String id);

    AIChangeRecipeResponse createRecipeFromIngredients(User user,
        RecipeFromIngredientsRequest request);

    AIChangeRecipeResponse simplifyRecipe(User user, String recipeId);

    List<Recipe> getRecommendedRecipe(User user, int page, int size);

    Recipe getRecipeById(User user, String id);

    Page<CustomRecipe> getCustomRecipeByUser(User user, int page, int pageSize);

    boolean checkBookmarked(User user, String recipeId);

    List<Recipe> getBookmarkedRecipes(User user);

    List<EatenRecipeResponse> getAllEatenRecipe(User user);

    @Builder
    record RecipeFromIngredientsRequest(
        String originalRecipeId,
        List<String> dislikeIngredients,
        List<String> basicSeasoning,
        List<String> mustUseIngredients
    ) {

    }
}
