package com.example.testysavingsbe.domain.food.dto;

import java.time.LocalDate;

public record FoodRegisterRequest(
        String foodName,
        String savingType,
        LocalDate expirationDate
) {
}
