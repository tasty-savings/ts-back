package com.example.testysavingsbe.domain.recipe.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record UseAllIngredientsRequest(
    @JsonProperty("original_recipe_id")
    String originalRecipeId,
    @JsonProperty("dislike_ingredients")
    List<String> dislikeIngredients,
    @JsonProperty("basic_seasoning")
    List<String> basicSeasoning,
    @JsonProperty("must_ues_ingredients")
    List<String> mustUseIngredients
) {

}
