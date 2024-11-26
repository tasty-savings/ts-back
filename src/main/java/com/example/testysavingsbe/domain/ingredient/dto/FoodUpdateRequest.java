package com.example.testysavingsbe.domain.ingredient.dto;

import java.time.LocalDate;

public record FoodUpdateRequest(
        String savingType,
        LocalDate expirationDate
) {
}
