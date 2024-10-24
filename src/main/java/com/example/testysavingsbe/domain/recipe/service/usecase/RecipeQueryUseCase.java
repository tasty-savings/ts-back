package com.example.testysavingsbe.domain.recipe.service.usecase;

import com.example.testysavingsbe.domain.recipe.dto.response.RecipeResponse;
import com.example.testysavingsbe.domain.user.entity.User;

import java.util.List;

public interface RecipeQueryUseCase {
    List<RecipeResponse> getBookMarkedRecipes(User user);
    List<RecipeResponse> getEatenRecipes(User user);

}
