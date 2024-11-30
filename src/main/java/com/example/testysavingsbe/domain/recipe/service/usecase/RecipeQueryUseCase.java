package com.example.testysavingsbe.domain.recipe.service.usecase;

import com.example.testysavingsbe.domain.recipe.dto.response.AIChangeRecipeResponse;
import com.example.testysavingsbe.domain.recipe.dto.response.CustomRecipeResponse;
import com.example.testysavingsbe.domain.recipe.dto.response.OriginalRecipeResponse;
import com.example.testysavingsbe.domain.recipe.dto.response.RecipeResponse;
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

    List<OriginalRecipeResponse> getRecommendedRecipe(User user);

    Recipe getRecipeById(User user, String id);

    Page<CustomRecipe> getCustomRecipeByUser(User user, int page, int pageSize);

    boolean checkBookmarked(User user, String recipeId);

    List<OriginalRecipeResponse> getBookmarkedRecipes(User user);

    List<RecipeResponse> getAllEatenRecipe(User user);

    CustomRecipeResponse getCustomRecipeBySharedLink(String uuid);

    @Builder
    record RecipeFromIngredientsRequest(
        String originalRecipeId,
        List<String> dislikeIngredients,
        List<String> basicSeasoning,
        List<String> mustUseIngredients
    ) {

    }
}
