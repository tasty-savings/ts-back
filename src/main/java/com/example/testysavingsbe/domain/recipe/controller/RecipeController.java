package com.example.testysavingsbe.domain.recipe.controller;

import com.example.testysavingsbe.domain.recipe.dto.request.RecipeSearchByMenuNameRequest;
import com.example.testysavingsbe.domain.recipe.dto.response.RecipeResponse;
import com.example.testysavingsbe.domain.recipe.entity.Recipe;
import com.example.testysavingsbe.domain.recipe.service.usecase.RecipeCommandUseCase;
import com.example.testysavingsbe.domain.recipe.service.usecase.RecipeQueryUseCase;
import com.example.testysavingsbe.global.config.PrincipalDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    // 내가 저장한 레시피 전부 가져오기
    @GetMapping("/all")
    public ResponseEntity<Page<RecipeResponse>> getSavedRecipe(@AuthenticationPrincipal PrincipalDetails principalDetails){
        Page<RecipeResponse> recipes = recipeQueryUseCase.getRecipes(principalDetails.getUser(), 0, 10);
         return ResponseEntity.ok(recipes);
    }

    /* todo 추천된 레시피 가져오기
    * RAG를 통해 추천된 레시피 전송
    * */
    @GetMapping("/recommend")
    public ResponseEntity<?> getRecommendedRecipe(@AuthenticationPrincipal PrincipalDetails principalDetails){
        Page<Recipe> response = recipeQueryUseCase.getRecommendedRecipe(principalDetails.getUser(), 0, 10);

        return ResponseEntity.ok(response.getContent());
    }

    /**
    * 냉장고 파먹기
    * */
    public ResponseEntity<?> getRecommendedRecipes(@AuthenticationPrincipal PrincipalDetails principalDetails){
//        recipeQueryUseCase.getRecommendedRecipe(principalDetails.getUser());

        return ResponseEntity.ok(null);
    }

    /* todo 레시피 편집
    * 레시피 단락 별로 인공지능을 이용해서 편집한다.
    * */


    /**
    * 단일 레시피 가져오기
     * @param principalDetails
    * */
    @GetMapping("/{id}")
    public ResponseEntity<?> getRecipe(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                       @PathVariable("id") String id
    ){
        recipeQueryUseCase.getRecipeById(principalDetails.getUser(), id);
        return ResponseEntity.ok(null);
    }

    /**
     * 메뉴 이름으로 레시피 생성
     * @param principalDetails
     * @param request
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
