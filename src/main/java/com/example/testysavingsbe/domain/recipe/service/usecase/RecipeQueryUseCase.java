package com.example.testysavingsbe.domain.recipe.service.usecase;

import com.example.testysavingsbe.domain.recipe.dto.response.RecipeResponse;
import com.example.testysavingsbe.domain.recipe.entity.RecommendedRecipe;
import com.example.testysavingsbe.domain.user.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RecipeQueryUseCase {
    List<RecipeResponse> getRecipeByQuery(String type, User user);
    Page<RecipeResponse> getRecipes(User user, int page, int size);
    Page<RecommendedRecipe> getRecommendedRecipe(User user, int page, int size);
}
