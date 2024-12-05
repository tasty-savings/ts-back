package com.example.testysavingsbe.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserCookingLevelResponse(
    @JsonProperty("cooking_level")
    String cookingLevel
) {

}
