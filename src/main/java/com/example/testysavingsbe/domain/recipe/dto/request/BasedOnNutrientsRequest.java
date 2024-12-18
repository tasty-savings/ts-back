package com.example.testysavingsbe.domain.recipe.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BasedOnNutrientsRequest(
    @JsonProperty("meals_a_day")
    int mealsADay
) {

}
