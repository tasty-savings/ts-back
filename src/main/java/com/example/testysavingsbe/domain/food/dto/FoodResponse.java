package com.example.testysavingsbe.domain.food.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record FoodResponse(
        Long id,
        String foodName,
        String savingType,
        String foodType,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate expirationDate
) {
}
