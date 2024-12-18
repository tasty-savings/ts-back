package com.example.testysavingsbe.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserSpicyLevelResponse(
    @JsonProperty("spicy_level")
    String spicyLevel
) {

}
