package com.example.testysavingsbe.domain.recipe.dto.request;

import com.example.testysavingsbe.global.util.MealPattern;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

public record AIGenerateBasedOnNutrientsRequest(
    @JsonProperty("곡류")
    float grains,
    @JsonProperty("고기·생선·달걀 종류")
    float proteinSources,
    @JsonProperty("채소류")
    float vegetables,
    @JsonProperty("과일류")
    float fruits,
    @JsonProperty("우유·유제품")
    float dairy,
    @JsonProperty("유자·당류")
    float fatsAndSugars
) {

    @Builder
    public AIGenerateBasedOnNutrientsRequest {
    }

    public static AIGenerateBasedOnNutrientsRequest toNutrientsRequestDivideByMeals(
        MealPattern.Pattern pattern, int mealsADay) {
        return AIGenerateBasedOnNutrientsRequest.builder()
            .grains(pattern.getGrains() / mealsADay)
            .proteinSources(pattern.getProteinSources() / mealsADay)
            .vegetables((float) pattern.getVegetables() / mealsADay)
            .fruits((float) pattern.getFruits() / mealsADay)
            .dairy((float) pattern.getDairy() / mealsADay)
            .fatsAndSugars((float) pattern.getFatsAndSugars() / mealsADay)
            .build();
    }
}
