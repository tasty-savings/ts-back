package com.example.testysavingsbe.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CheckSetPreferFoodResponse(
    @JsonProperty("is_checked")
    Boolean isChecked
) {

}
