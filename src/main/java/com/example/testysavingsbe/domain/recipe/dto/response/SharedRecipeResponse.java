package com.example.testysavingsbe.domain.recipe.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SharedRecipeResponse(
    @JsonProperty()
    String url
) {

}
