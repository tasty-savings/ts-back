package com.example.testysavingsbe.domain.ingredient.service.usecase;

import com.example.testysavingsbe.domain.ingredient.dto.FoodResponse;
import com.example.testysavingsbe.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public interface FoodCommandUseCase {
    FoodResponse registerFood(User user, RegisterFoodRequest request);
    FoodResponse updateFood(User user, UpdateFoodRequest request);
    void deleteFood(User user, DeleteFoodRequest deleteFoodRequest);


    record RegisterFoodRequest(
            String foodName,
            String savingType,
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate expirationDate
    ){
    }

    record UpdateFoodRequest(
            Long id,
            String savingType,
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate expirationDate
    ){
    }

    record DeleteFoodRequest(
            Long foodId
    ) {
    }
}
