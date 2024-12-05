package com.example.testysavingsbe.domain.ingredient.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public record SearchFoodResponse(
    @JsonProperty("food_name")
    String foodName,
    @JsonProperty("food_type")
    String foodType
) {

}
