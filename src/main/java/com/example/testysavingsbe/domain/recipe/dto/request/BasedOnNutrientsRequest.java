package com.example.testysavingsbe.domain.recipe.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record BasedOnNutrientsRequest(
    @JsonProperty("meals_a_day")
    int mealsADay,
    @JsonProperty("basic_seasoning")
    List<String>userBasicSeasoning
) {

}
