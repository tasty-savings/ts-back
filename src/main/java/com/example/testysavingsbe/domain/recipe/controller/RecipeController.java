package com.example.testysavingsbe.domain.recipe.controller;

import com.example.testysavingsbe.domain.recipe.dto.request.RecipeSearchByMenuNameRequest;
import com.example.testysavingsbe.domain.recipe.dto.response.RecipeResponse;
import com.example.testysavingsbe.domain.recipe.entity.RecipeQueryType;
import com.example.testysavingsbe.domain.recipe.service.usecase.RecipeCommandUseCase;
import com.example.testysavingsbe.domain.recipe.service.usecase.RecipeQueryUseCase;
import com.example.testysavingsbe.domain.user.entity.User;
import com.example.testysavingsbe.global.config.PrincipalDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeCommandUseCase recipeCommandUseCase;
    private final RecipeQueryUseCase recipeQueryUseCase;

    /**
     * 메뉴 이름으로 레시피 생성
     */
    @PostMapping
    public ResponseEntity<RecipeResponse> getRecipeByMenuName(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody @Valid RecipeSearchByMenuNameRequest request) {
        RecipeResponse recipeResponse = recipeCommandUseCase.generateRecipe(new RecipeCommandUseCase.RecipeGenerateServiceRequest(principalDetails.getUser(), request.menuName()));

        return ResponseEntity.ok().body(recipeResponse);
    }

    /**
     * 레시피 북마크
     */
    @PutMapping("/bookmark/{recipeId}")
    public ResponseEntity<RecipeResponse> bookmarkRecipe(@PathVariable Long recipeId) {
        RecipeResponse recipeResponse = recipeCommandUseCase.bookmarkRecipe(new RecipeCommandUseCase.RecipeUpdateServiceRequest(recipeId));
        return ResponseEntity.ok().body(recipeResponse);
    }

    /**
     * 레시피 먹었어요
     */
    @PutMapping("/eat/{recipeId}")
    public ResponseEntity<RecipeResponse> eatRecipe(@PathVariable Long recipeId) {
        RecipeResponse recipeResponse = recipeCommandUseCase.checkEatRecipe(new RecipeCommandUseCase.RecipeUpdateServiceRequest(recipeId));
        return ResponseEntity.ok().body(recipeResponse);
    }


    /**
     * 북마크 or 먹은 레시피 전체 가져오기
     */
    @GetMapping
    public ResponseEntity<List<RecipeResponse>> getRecipeListByQuery(@RequestParam(name = "type") String queryType, @AuthenticationPrincipal PrincipalDetails principalDetails) {
         List<RecipeResponse> responses  = recipeQueryUseCase.getRecipeByQuery(queryType, principalDetails.getUser());
        return ResponseEntity.ok(responses);
    }
}
