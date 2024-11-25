package com.example.testysavingsbe.domain.recipe.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AIChangeRecipeResponse(@JsonProperty("before") OriginalRecipeResponse before,
                                     @JsonProperty("after") AIRecipeResponse after) {

}
