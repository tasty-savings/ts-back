package com.example.testysavingsbe.domain.food.controller;

import com.example.testysavingsbe.domain.food.dto.*;
import com.example.testysavingsbe.domain.food.service.FoodService;
import com.example.testysavingsbe.domain.food.service.usecase.FoodCommandUseCase;
import com.example.testysavingsbe.domain.food.service.usecase.FoodQueryUseCase;
import com.example.testysavingsbe.global.config.PrincipalDetails;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/food")
public class FoodController {
    private final FoodCommandUseCase foodCommandUseCase;
    private final FoodQueryUseCase foodQueryUseCase;


    @GetMapping("/search")
    public ResponseEntity<List<FoodInfoDto>> search(@RequestParam(name = "food") @NotBlank(message = "빈 문자열 입니다.") String foodName) {
        return ResponseEntity.ok(foodQueryUseCase.searchFood(foodName));
    }

    @GetMapping("/my_foods")
    public ResponseEntity<List<FoodResponse>> getAllFood(@AuthenticationPrincipal PrincipalDetails principal) {
        List<FoodResponse> allFoods = foodQueryUseCase.getAllFoods(principal.getUser());
        return ResponseEntity.ok(allFoods);
    }

    @PostMapping
    public ResponseEntity<FoodResponse> registerFood(
            @AuthenticationPrincipal PrincipalDetails principal,
            @RequestBody FoodRegisterRequest request) {
        FoodResponse foodResponse = foodCommandUseCase.registerFood(principal.getUser(),
                new FoodCommandUseCase.RegisterFoodRequest(request.foodName(),
                        request.savingType(),
                        request.expirationDate()));

        return ResponseEntity.ok(foodResponse);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFood(@AuthenticationPrincipal PrincipalDetails principal,
                                           @PathVariable Long id) {
        foodCommandUseCase.deleteFood(principal.getUser(), new FoodCommandUseCase.DeleteFoodRequest(id));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodResponse> updateFood(@AuthenticationPrincipal PrincipalDetails principal,
                                                   @PathVariable Long id,
                                                   @RequestBody FoodUpdateRequest request) {
        FoodResponse foodResponse = foodCommandUseCase.updateFood(principal.getUser(),
                new FoodCommandUseCase.UpdateFoodRequest(id,
                        request.savingType(),
                        request.expirationDate())
        );
        return ResponseEntity.ok(foodResponse);
    }
}
