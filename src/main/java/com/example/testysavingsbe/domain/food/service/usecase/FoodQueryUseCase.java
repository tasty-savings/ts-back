package com.example.testysavingsbe.domain.food.service.usecase;

import com.example.testysavingsbe.domain.food.dto.FoodResponse;
import com.example.testysavingsbe.domain.user.entity.User;

import java.util.List;

public interface FoodQueryUseCase {
    List<FoodResponse> getAllFoods(User user);
}
