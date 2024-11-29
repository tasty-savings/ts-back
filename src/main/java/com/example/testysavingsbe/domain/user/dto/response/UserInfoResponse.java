package com.example.testysavingsbe.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;

@Builder
public record UserInfoResponse(
    @JsonProperty("username")
    String username,
    @JsonProperty("cooking_level")
    String cookingLevel,
    @JsonProperty("spicy_level")
    String spicyLevel,
    @JsonProperty("allergy")
    List<String> allergy
) {

}
