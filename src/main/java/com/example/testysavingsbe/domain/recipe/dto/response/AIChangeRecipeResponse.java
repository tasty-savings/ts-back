package com.example.testysavingsbe.domain.recipe.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AIChangeRecipeResponse(
    @JsonProperty("custom_recipe_id") String customRecipeId,
    @JsonProperty("before") OriginalRecipeResponse before,
    @JsonProperty("after") AIRecipe after
){

}
