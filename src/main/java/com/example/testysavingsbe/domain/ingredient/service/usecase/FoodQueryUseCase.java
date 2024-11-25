package com.example.testysavingsbe.domain.ingredient.service.usecase;

import com.example.testysavingsbe.domain.ingredient.dto.FoodInfoDto;
import com.example.testysavingsbe.domain.ingredient.dto.FoodResponse;
import com.example.testysavingsbe.domain.user.entity.User;

import java.util.List;

public interface FoodQueryUseCase {
    List<FoodResponse> getAllFoods(User user);

    List<FoodInfoDto> searchFood(String foodName);
}
