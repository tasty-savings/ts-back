package com.example.testysavingsbe.domain.recipe.dto.request;

public record EatRecipeRequest(
        String recipeId,
        String recipeType
) {

    public EatRecipeRequest {
        if (!recipeType.equals("original") && !recipeType.equals("custom")) {
            throw new IllegalArgumentException("Invalid recipeType: " + recipeType + ". Allowed values are 'original' or 'custom'.");
        }
    }
}
