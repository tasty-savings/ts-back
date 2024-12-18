package com.example.testysavingsbe.domain.ingredient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public record FoodRegisterRequest(
        @JsonProperty("food_name")
        String foodName,
        @JsonProperty("saving_type")
        String savingType,
        @JsonProperty("expiration_date")
        LocalDate expirationDate
) {
}
