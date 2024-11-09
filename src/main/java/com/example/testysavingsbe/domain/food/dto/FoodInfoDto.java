package com.example.testysavingsbe.domain.food.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FoodInfoDto(
        @JsonProperty("foodName") String foodName,
        @JsonProperty("foodType") String foodType) {
}
