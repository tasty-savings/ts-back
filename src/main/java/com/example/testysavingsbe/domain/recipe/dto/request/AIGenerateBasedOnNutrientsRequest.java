package com.example.testysavingsbe.domain.recipe.dto.request;

import com.example.testysavingsbe.domain.user.entity.ActivityLevel;
import com.example.testysavingsbe.global.util.MealPattern;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

public record AIGenerateBasedOnNutrientsRequest(
    @JsonProperty("유저 정보")
    UserHealthInfo userHealthInfo,
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

    private static float roundToOneDecimal(int value, int mealsADay) {
        float divisionResult = (float) value / mealsADay;
        return Math.round(divisionResult * 10f) / 10f;
    }

    private static float roundToOneDecimal(float value, int mealsADay) {
        float divisionResult = value / mealsADay;
        return Math.round(divisionResult * 10f) / 10f;
    }


    public static AIGenerateBasedOnNutrientsRequest toNutrientsRequestDivideByMeals(
        int age,
        String gender,
        float height,
        float weight,
        int activityLevel,
        MealPattern.Pattern pattern,
        int mealsADay
    ) {
        return AIGenerateBasedOnNutrientsRequest.builder()
            .grains(roundToOneDecimal(pattern.getGrains(), mealsADay))
            .proteinSources(roundToOneDecimal(pattern.getProteinSources(), mealsADay))
            .vegetables(roundToOneDecimal(pattern.getVegetables(), mealsADay))
            .fruits(roundToOneDecimal(pattern.getFruits(), mealsADay))
            .dairy(roundToOneDecimal(pattern.getDairy(), mealsADay))
            .fatsAndSugars(roundToOneDecimal(pattern.getFatsAndSugars(), mealsADay))
            .userHealthInfo(new UserHealthInfo(age, gender, height, weight, activityLevel))
            .build();
    }

    public record UserHealthInfo(
        @JsonProperty("나이")
        int age,
        @JsonProperty("성별")
        String gender,
        @JsonProperty("키")
        float height,
        @JsonProperty("몸무게")
        float weight,
        @JsonProperty("활동계수")
        String activityLevel
    ) {

        public UserHealthInfo(int age, String gender, float height, float weight,
            int activityLevelInt) {
            this(
                age,
                gender,
                height,
                weight,
                switch (activityLevelInt) {
                    case 1 -> "비활동적";
                    case 2 -> "저활동적";
                    case 3 -> "활동적";
                    case 4 -> "매우 활동적";
                    default ->
                        throw new IllegalArgumentException("유효하지 않은 활동 수준입니다: " + activityLevelInt);
                }
            );
        }
    }


}
