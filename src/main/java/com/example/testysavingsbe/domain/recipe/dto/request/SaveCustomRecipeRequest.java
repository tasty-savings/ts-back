package com.example.testysavingsbe.domain.recipe.dto.request;


import java.util.List;

public record SaveCustomRecipeRequest(
        String title,
        String mainImg,
        String typeKey,
        String methodKey,
        String servings,        // n인분
        String cookingTime,
        String difficulty,
        List<String> ingredients,
        List<String> cookingOrder,
        List<String> cookingImg,
        List<String> hashtag,
        List<String> tips,
        List<String> recipeType
) {
}
