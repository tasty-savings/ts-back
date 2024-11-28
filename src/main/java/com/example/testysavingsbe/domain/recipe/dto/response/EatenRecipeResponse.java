package com.example.testysavingsbe.domain.recipe.dto.response;

import com.example.testysavingsbe.domain.recipe.entity.CustomRecipe;
import com.example.testysavingsbe.domain.recipe.entity.Recipe;
import com.fasterxml.jackson.annotation.JsonProperty;

public record EatenRecipeResponse(
    @JsonProperty("tag")
    String tag,
    @JsonProperty("data")
    RecipeResponse data
) {

    public static final String ORIGINAL_RECIPE_TYPE = "original";
    public static final String CUSTOM_RECIPE_TYPE = "custom";

    public static EatenRecipeResponse parseOriginal(Recipe recipe) {
        return new EatenRecipeResponse(ORIGINAL_RECIPE_TYPE, OriginalRecipeResponse.fromRecipe(recipe));
    }

    public static EatenRecipeResponse parseCustom(CustomRecipe customRecipe) {
        return new EatenRecipeResponse(CUSTOM_RECIPE_TYPE, CustomRecipeResponse.from(customRecipe));
    }
}
