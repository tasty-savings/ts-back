package com.example.testysavingsbe.domain.recipe.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record SimplifyRecipeToAiRequest(
    @JsonProperty("user_allergy_ingredients") List<String> userAllergy,
    @JsonProperty("user_cooking_level") String userPreferType
) {
}
