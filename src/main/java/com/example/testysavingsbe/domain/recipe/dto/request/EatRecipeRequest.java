package com.example.testysavingsbe.domain.recipe.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EatRecipeRequest(
    @JsonProperty("recipe_id")
    String recipeId,
    @JsonProperty("recipe_type")
    String recipeType
) {

    public EatRecipeRequest {
        if (!recipeType.equals("original") && !recipeType.equals("custom")) {
            throw new IllegalArgumentException("Invalid recipeType: " + recipeType
                + ". Allowed values are 'original' or 'custom'.");
        }
    }
}
