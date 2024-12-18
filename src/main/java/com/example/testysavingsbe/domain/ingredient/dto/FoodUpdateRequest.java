package com.example.testysavingsbe.domain.ingredient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public record FoodUpdateRequest(
    @JsonProperty("saving_type")
    String savingType,
    @JsonProperty("expiration_date")
    LocalDate expirationDate
) {

}
