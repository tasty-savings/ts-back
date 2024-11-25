package com.example.testysavingsbe.domain.ingredient.dto;

import java.time.LocalDate;

public record FoodRegisterRequest(
        String foodName,
        String savingType,
        LocalDate expirationDate
) {
}
