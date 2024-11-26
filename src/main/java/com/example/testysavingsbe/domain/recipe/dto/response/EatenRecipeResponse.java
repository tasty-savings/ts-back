package com.example.testysavingsbe.domain.recipe.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record EatenRecipeResponse(
    @JsonProperty("tag")
    String tag,
    @JsonProperty("data")
    RecipeResponse data
) {

}
