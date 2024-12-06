package com.example.testysavingsbe.domain.recipe.controller;

import com.example.testysavingsbe.domain.recipe.dto.request.EatRecipeRequest;
import com.example.testysavingsbe.domain.recipe.dto.request.SaveCustomRecipeRequest;
import com.example.testysavingsbe.domain.recipe.dto.request.UseAllIngredientsRequest;
import com.example.testysavingsbe.domain.recipe.dto.response.AIChangeRecipeResponse;
import com.example.testysavingsbe.domain.recipe.dto.response.CustomRecipeResponse;
import com.example.testysavingsbe.domain.recipe.dto.response.IsBookmarkedResponse;
import com.example.testysavingsbe.domain.recipe.dto.response.OriginalRecipeResponse;
import com.example.testysavingsbe.domain.recipe.dto.response.RecipeResponse;
import com.example.testysavingsbe.domain.recipe.dto.response.SharedRecipeResponse;
import com.example.testysavingsbe.domain.recipe.entity.UserEaten;
import com.example.testysavingsbe.domain.recipe.service.usecase.RecipeCommandUseCase;
import com.example.testysavingsbe.domain.recipe.service.usecase.RecipeQueryUseCase;
import com.example.testysavingsbe.domain.recipe.service.usecase.RecipeQueryUseCase.RecipeFromIngredientsRequest;
import com.example.testysavingsbe.global.config.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
            principalDetails.getUser()
        );

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
        log.info("냉장고 파먹기 요청");
        AIChangeRecipeResponse response = recipeQueryUseCase.createRecipeFromIngredients(
            principalDetails.getUser(),
            RecipeFromIngredientsRequest.builder()
                .originalRecipeId(request.originalRecipeId())
                .dislikeIngredients(request.dislikeIngredients())
                .basicSeasoning(request.basicSeasoning())
                .mustUseIngredients(request.mustUseIngredients())
                .build());

        log.info("응답 요청");
        return ResponseEntity.ok(response);
    }

    /**
     * 단일 레시피 가져오기(원본)
     *
     * @param id 원본 레시피 Id
     */
    @GetMapping("/original/{id}")
    public ResponseEntity<OriginalRecipeResponse> getRecipe(
        @PathVariable("id") String id) {
        OriginalRecipeResponse recipeById = recipeQueryUseCase.getRecipeById(id);
        return ResponseEntity.ok(recipeById);
    }

    @GetMapping("/custom/all")
    public ResponseEntity<List<CustomRecipeResponse>> getRecipesByUser(
        @AuthenticationPrincipal PrincipalDetails details,
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "page_size", defaultValue = "10") int pageSize) {
        List<CustomRecipeResponse> responses = recipeQueryUseCase.getCustomRecipeByUser(
            details.getUser(), page, pageSize);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/custom/{recipeId}")
    public ResponseEntity<CustomRecipeResponse> getCustomRecipe(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable(name = "recipeId") String id) {
        CustomRecipeResponse response = recipeQueryUseCase.getCustomRecipe(principalDetails.getUser(), id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/custom/save")
    public ResponseEntity<CustomRecipeResponse> saveCustomRecipe(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody SaveCustomRecipeRequest request) {
        CustomRecipeResponse mongoRecipe = recipeCommandUseCase.saveCustomRecipe(principalDetails.getUser(),
            request);
        return ResponseEntity.status(HttpStatus.CREATED).body(mongoRecipe);
    }

    @PutMapping("/{recipeId}/bookmark")
    public ResponseEntity<Void> bookMarkRecipe(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable(name = "recipeId") String recipeId) {
        recipeCommandUseCase.bookmarkRecipe(principalDetails.getUser(), recipeId);
        return ResponseEntity.noContent().build();
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
    public ResponseEntity<List<RecipeResponse>> getAllEatenRecipe(
        @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        List<RecipeResponse> allEatenRecipe = recipeQueryUseCase.getAllEatenRecipe(
            principalDetails.getUser());

        return ResponseEntity.ok(allEatenRecipe);

    }


    @PutMapping("/{recipeId}/eat")
    public ResponseEntity<UserEaten> eatRecipe(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable(name = "recipeId") String recipeId,
        @RequestParam(name = "type") String recipeType) {
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


    @PostMapping("/custom/share/{customRecipeId}")
    public ResponseEntity<SharedRecipeResponse> shareCustomRecipe(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable("customRecipeId") String customRecipeId
    ) {
        SharedRecipeResponse sharedRecipeResponse = recipeCommandUseCase.generateCustomRecipeShareUrl(
            principalDetails.getUser(), customRecipeId);
        return ResponseEntity.ok(sharedRecipeResponse);
    }

    @GetMapping("/share/{uuid}")
    public ResponseEntity<CustomRecipeResponse> getCustomRecipes(
        @PathVariable("uuid") String uuid
    ) {
        CustomRecipeResponse customRecipeBySharedLink = recipeQueryUseCase.getCustomRecipeBySharedLink(
            uuid);

        return ResponseEntity.ok(customRecipeBySharedLink);
    }

    @DeleteMapping("/custom/share/{customRecipeId}")
    public ResponseEntity<Void> deleteSharedRecipe(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable("customRecipeId") String customRecipeId
    ) {
        recipeCommandUseCase.deleteSharedRecipe(customRecipeId);
        return ResponseEntity.noContent().build();

    }

}

