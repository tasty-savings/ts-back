package com.example.testysavingsbe.domain.recipe.service.usecase;

import com.example.testysavingsbe.domain.recipe.entity.CustomRecipe;
import com.example.testysavingsbe.domain.recipe.entity.Recipe;
import com.example.testysavingsbe.domain.user.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RecipeQueryUseCase {
    CustomRecipe getCustomRecipe(User user, String id);


    // todo 추천 레시피 받기
    Page<Recipe> getRecommendedRecipe(User user, int page, int size);

    Recipe getRecipeById(User user, String id);

    Page<CustomRecipe> getCustomRecipeByUser(User user, int page, int pageSize);

    boolean checkBookmarked(User user, String recipeId);

    List<Recipe> getBookmarkedRecipes(User user);
}
