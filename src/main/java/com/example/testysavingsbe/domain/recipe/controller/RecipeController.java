package com.example.testysavingsbe.domain.recipe.controller;

import com.example.testysavingsbe.domain.recipe.dto.request.EatRecipeRequest;
import com.example.testysavingsbe.domain.recipe.dto.request.SaveCustomRecipeRequest;
import com.example.testysavingsbe.domain.recipe.dto.request.UseAllIngredientsRequest;
import com.example.testysavingsbe.domain.recipe.dto.response.AIChangeRecipeResponse;
import com.example.testysavingsbe.domain.recipe.dto.response.IsBookmarkedResponse;
import com.example.testysavingsbe.domain.recipe.entity.BookmarkedRecipe;
import com.example.testysavingsbe.domain.recipe.entity.CustomRecipe;
import com.example.testysavingsbe.domain.recipe.entity.Recipe;
import com.example.testysavingsbe.domain.recipe.entity.UserEaten;
import com.example.testysavingsbe.domain.recipe.service.usecase.RecipeCommandUseCase;
import com.example.testysavingsbe.domain.recipe.service.usecase.RecipeCommandUseCase.RecipeFromIngredientsRequest;
import com.example.testysavingsbe.domain.recipe.service.usecase.RecipeQueryUseCase;
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
     * todo 추천 레시피 가져오기
     * user prefer 가 없을시 백엔드에서 아무거나 주기
     * RAG를 통해 추천된 레시피 전송
     */
    @GetMapping("/recommend")
    public ResponseEntity<?> getRecommendedRecipe(
        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Page<Recipe> response = recipeQueryUseCase.getRecommendedRecipe(principalDetails.getUser(),
            0, 10);

        return ResponseEntity.ok(response.getContent());
    }

    /**
     * 레시피 단축하기
     */

    /**
     * 냉장고 파먹기
     */
    @PostMapping("/custom/ai/generate")
    public ResponseEntity<?> createRecipeFromUserIngredients(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody UseAllIngredientsRequest request
    ) {

        AIChangeRecipeResponse response = recipeCommandUseCase.createRecipeFromIngredients(
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

    // todo 1. 편집 레시피 전부 가져오기
    @GetMapping("/custom/all")
    public ResponseEntity<List<CustomRecipe>> getRecipesByUser(
        @AuthenticationPrincipal PrincipalDetails details,
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "page_size", defaultValue = "10") int pageSize) {
        Page<CustomRecipe> mongoRecipeByUser = recipeQueryUseCase.getCustomRecipeByUser(
            details.getUser(), page, pageSize);
        return ResponseEntity.ok(mongoRecipeByUser.getContent());
    }

    // todo 1.1 커스텀한 레시피 상제 정보 가져오기
    @GetMapping("/custom/{recipeId}")
    public ResponseEntity<CustomRecipe> getCustomRecipe(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable(name = "recipeId") String id) {
        CustomRecipe response = recipeQueryUseCase.getCustomRecipe(principalDetails.getUser(), id);
        return ResponseEntity.ok(response);
    }

    // todo 2. 편집 레시피 저장
    @PostMapping("/custom/save")
    public ResponseEntity<CustomRecipe> saveCustomRecipe(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody SaveCustomRecipeRequest request) {
        CustomRecipe mongoRecipe = recipeCommandUseCase.saveCustomRecipe(principalDetails.getUser(),
            request);
        return ResponseEntity.status(HttpStatus.CREATED).body(mongoRecipe);
    }

    // todo 3. 레시피 북마크(원본)
    @PutMapping("/{recipeId}/bookmark")
    public ResponseEntity<BookmarkedRecipe> bookMarkRecipe(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable(name = "recipeId") String recipeId) {
        BookmarkedRecipe bookmarkedRecipe = recipeCommandUseCase.bookmarkRecipe(
            principalDetails.getUser(), recipeId);
        return ResponseEntity.ok(bookmarkedRecipe);
    }

    // todo 3.1 북마크했는지 조회
    @GetMapping("/{recipeId}/bookmark")
    public ResponseEntity<IsBookmarkedResponse> checkBookmarked(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable(name = "recipeId") String recipeId) {
        boolean isBookmarked = recipeQueryUseCase.checkBookmarked(principalDetails.getUser(),
            recipeId);
        IsBookmarkedResponse response = new IsBookmarkedResponse(recipeId, isBookmarked);

        return ResponseEntity.ok(response);
    }


    // todo 3.2 북마크한 레시피 전부 가져오기
    @GetMapping("/bookmark/all")
    public ResponseEntity<List<Recipe>> getAllBookmarkedRecipes(
        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<Recipe> response = recipeQueryUseCase.getBookmarkedRecipes(principalDetails.getUser());
        return ResponseEntity.ok(response);
    }


    // todo 4. 레시피 먹기 기능(커스텀 + 원본)
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

    // todo 5. 레시피 공유하기(인증된 사용자 아니여도 접근가능, 편집 불가능)
    public ResponseEntity<?> shareCustomRecipe() {
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
