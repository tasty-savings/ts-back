package com.example.testysavingsbe.domain.recipe.controller;

import com.example.testysavingsbe.domain.recipe.dto.request.EatRecipeRequest;
import com.example.testysavingsbe.domain.recipe.dto.request.SaveCustomRecipeRequest;
import com.example.testysavingsbe.domain.recipe.dto.request.UseAllIngredientsRequest;
import com.example.testysavingsbe.domain.recipe.dto.response.AIChangeRecipeResponse;
import com.example.testysavingsbe.domain.recipe.dto.response.EatenRecipeResponse;
import com.example.testysavingsbe.domain.recipe.dto.response.IsBookmarkedResponse;
import com.example.testysavingsbe.domain.recipe.entity.BookmarkedRecipe;
import com.example.testysavingsbe.domain.recipe.entity.CustomRecipe;
import com.example.testysavingsbe.domain.recipe.entity.Recipe;
import com.example.testysavingsbe.domain.recipe.entity.UserEaten;
import com.example.testysavingsbe.domain.recipe.service.usecase.RecipeCommandUseCase;
import com.example.testysavingsbe.domain.recipe.service.usecase.RecipeQueryUseCase;
import com.example.testysavingsbe.domain.recipe.service.usecase.RecipeQueryUseCase.RecipeFromIngredientsRequest;
import com.example.testysavingsbe.global.config.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
     * user prefer 가 없을시 백엔드에서 아무거나 주기 RAG를 통해 추천된 레시피 전송
     */
    @GetMapping("/recommend")
    public ResponseEntity<?> getRecommendedRecipe(
        @AuthenticationPrincipal PrincipalDetails principalDetails) {

        List<OriginalRecipeResponse> response = recipeQueryUseCase.getRecommendedRecipe(
            principalDetails.getUser(),
            0, 10);

        return ResponseEntity.ok(response);
    }

    /**
     * 레시피 단축하기
     */
    @GetMapping("/custom/simplify/{recipeId}")
    public ResponseEntity<AIChangeRecipeResponse> simplifyRecipe(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable("recipeId") String recipeId) {
        AIChangeRecipeResponse response = recipeQueryUseCase.simplifyRecipe(
            principalDetails.getUser(), recipeId);

        return ResponseEntity.ok(response);
    }


    /**
     * 냉장고 파먹기
     */
    @PostMapping("/custom/ai/generate")
    public ResponseEntity<AIChangeRecipeResponse> createRecipeFromUserIngredients(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody UseAllIngredientsRequest request
    ) {

        AIChangeRecipeResponse response = recipeQueryUseCase.createRecipeFromIngredients(
            principalDetails.getUser(),
            RecipeFromIngredientsRequest.builder()
                .originalRecipeId(request.originalRecipeId())
                .dislikeIngredients(request.dislikeIngredients())
                .basicSeasoning(request.basicSeasoning())
                .mustUseIngredients(request.mustUseIngredients())
                .build());

        return ResponseEntity.ok(response);
    }

    /**
     * 단일 레시피 가져오기(원본)
     *
     * @param principalDetails
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getRecipe(@AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable("id") String id) {
        Recipe recipeById = recipeQueryUseCase.getRecipeById(principalDetails.getUser(), id);
        return ResponseEntity.ok(recipeById);
    }

    @GetMapping("/custom/all")
    public ResponseEntity<List<CustomRecipe>> getRecipesByUser(
        @AuthenticationPrincipal PrincipalDetails details,
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "page_size", defaultValue = "10") int pageSize) {
        Page<CustomRecipe> mongoRecipeByUser = recipeQueryUseCase.getCustomRecipeByUser(
            details.getUser(), page, pageSize);
        return ResponseEntity.ok(mongoRecipeByUser.getContent());
    }

    @GetMapping("/custom/{recipeId}")
    public ResponseEntity<CustomRecipe> getCustomRecipe(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable(name = "recipeId") String id) {
        CustomRecipe response = recipeQueryUseCase.getCustomRecipe(principalDetails.getUser(), id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/custom/save")
    public ResponseEntity<CustomRecipe> saveCustomRecipe(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody SaveCustomRecipeRequest request) {
        CustomRecipe mongoRecipe = recipeCommandUseCase.saveCustomRecipe(principalDetails.getUser(),
            request);
        return ResponseEntity.status(HttpStatus.CREATED).body(mongoRecipe);
    }

    @PutMapping("/{recipeId}/bookmark")
    public ResponseEntity<BookmarkedRecipe> bookMarkRecipe(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable(name = "recipeId") String recipeId) {
        BookmarkedRecipe bookmarkedRecipe = recipeCommandUseCase.bookmarkRecipe(
            principalDetails.getUser(), recipeId);
        return ResponseEntity.ok(bookmarkedRecipe);
    }

    @GetMapping("/{recipeId}/bookmark")
    public ResponseEntity<IsBookmarkedResponse> checkBookmarked(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable(name = "recipeId") String recipeId) {
        boolean isBookmarked = recipeQueryUseCase.checkBookmarked(principalDetails.getUser(),
            recipeId);
        IsBookmarkedResponse response = new IsBookmarkedResponse(recipeId, isBookmarked);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/bookmark/all")
    public ResponseEntity<List<OriginalRecipeResponse>> getAllBookmarkedRecipes(
        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<OriginalRecipeResponse> response = recipeQueryUseCase.getBookmarkedRecipes(
            principalDetails.getUser());
        return ResponseEntity.ok(response);
    }


    @GetMapping("/eat/all")
    public ResponseEntity<List<EatenRecipeResponse>> getAllEatenRecipe(
        @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        List<EatenRecipeResponse> allEatenRecipe = recipeQueryUseCase.getAllEatenRecipe(
            principalDetails.getUser());

        return ResponseEntity.ok(allEatenRecipe);

    }


    @PutMapping("/{recipeId}/eat")
    public ResponseEntity<UserEaten> eatRecipe(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable(name = "recipeId") String recipeId,
        @RequestParam(name = "type") String recipeType) {    // [original, custom]
        EatRecipeRequest request = new EatRecipeRequest(recipeId, recipeType);
        UserEaten userEaten = recipeCommandUseCase.checkEatRecipe(principalDetails.getUser(),
            request);
        return ResponseEntity.ok(userEaten);
    }


    @DeleteMapping("/{recipeId}/eat")
    public ResponseEntity<Void> deleteRecipe(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable(name = "recipeId") String recipeId
    ) {
        recipeCommandUseCase.removeEatenRecipe(principalDetails.getUser(), recipeId);

        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> shareCustomRecipe(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable() String recipeId
    ) {
        // 1. 존재하는지 확인
        // 2. 링크 생성

        return ResponseEntity.ok(null);
    }

    // 최종적으로 편집 창에서 편집 완료를 누르게 되면 내 레시피로 레시피를 완료되게
    // 2. AI 맞춤형
    // 1. 수동으로 레시피 편집
    /* todo 레시피 편집
     * 레시피 단락 별로 인공지능을 이용해서 편집한다.
     * */
    // 내가 저장한 레시피 전부 가져오기

}
