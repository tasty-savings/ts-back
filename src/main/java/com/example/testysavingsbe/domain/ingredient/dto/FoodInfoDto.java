package com.example.testysavingsbe.domain.ingredient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FoodInfoDto(
        @JsonProperty("food_name") String foodName,
        @JsonProperty("food_type") String foodType) {
}
