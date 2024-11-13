package com.example.testysavingsbe.domain.food.service;

import com.example.testysavingsbe.domain.food.dto.FoodInfoDto;
import com.example.testysavingsbe.domain.food.dto.FoodResponse;
import com.example.testysavingsbe.domain.food.entity.Food;
import com.example.testysavingsbe.domain.food.entity.FoodInfo;
import com.example.testysavingsbe.domain.food.entity.SavingType;
import com.example.testysavingsbe.domain.food.repository.FoodRepository;
import com.example.testysavingsbe.domain.food.repository.FoodInfoRepository;
import com.example.testysavingsbe.domain.food.service.usecase.FoodCommandUseCase;
import com.example.testysavingsbe.domain.food.service.usecase.FoodQueryUseCase;
import com.example.testysavingsbe.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService implements FoodQueryUseCase, FoodCommandUseCase {
    private final FoodRepository foodRepository;
    private final FoodInfoRepository foodInfoRepository;

    @Override
    @Cacheable(cacheNames = "foodSearchCache", key = "#foodName")
    public List<FoodInfoDto> searchFood(String foodName) {
        List<FoodInfo> byNameContaining = foodInfoRepository.findByNameContaining(foodName);
        return byNameContaining.stream()
                .map(info -> new FoodInfoDto(info.getName(), info.getFoodType().toString())
                ).toList();
    }

    @Override
    @Transactional
    public FoodResponse registerFood(User user, RegisterFoodRequest request) {
        FoodInfo foodInfo = foodInfoRepository.findByName(request.foodName()).orElse(null);

        Food food = Food.builder()
                .foodName(request.foodName())
                .foodInfo(foodInfo)
                .savingType(SavingType.of(request.savingType()))
                .expirationDate(request.expirationDate())
                .user(user)
                .build();

        foodRepository.save(food);

        return FoodResponse.builder()
                .id(food.getId())
                .foodName(food.getFoodName())
                .savingType(food.getSavingType().toString())
                .expirationDate(food.getExpirationDate())
                .build();
    }

    @Override
    @Transactional
    public FoodResponse updateFood(User user, UpdateFoodRequest request) {
        Food food = foodRepository.findByIdAndUser(request.id(), user)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 음식입니다."));

        if (request.savingType() != null) {
            food.updateSavingType(request.savingType());
        }
        if (request.expirationDate() != null) {
            food.updateExpirationDate(request.expirationDate());
        }

        foodRepository.save(food);
        return FoodResponse.builder()
                .id(food.getId())
                .foodName(food.getFoodName())
                .savingType(food.getSavingType().toString())
                .expirationDate(food.getExpirationDate())
                .build();
    }

    @Override
    @Transactional
    public void deleteFood(User user, DeleteFoodRequest deleteFoodRequest) {
        foodRepository.deleteById(deleteFoodRequest.foodId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FoodResponse> getAllFoods(User user) {
        List<Food> allByUser = foodRepository.findAllByUser(user);
        return allByUser.stream().map(food ->
                FoodResponse.builder()
                        .id(food.getId())
                        .foodName(food.getFoodName())
                        .savingType(food.getSavingType().toString())
                        .expirationDate(food.getExpirationDate())
                        .build()
        ).toList();
    }
}
