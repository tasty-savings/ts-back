package com.example.testysavingsbe.domain.food.dto;

import java.time.LocalDate;

public record FoodUpdateRequest(
        String savingType,
        LocalDate expirationDate
) {
}
