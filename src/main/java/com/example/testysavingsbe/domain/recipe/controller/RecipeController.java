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
        Recipe recipeById = recipeQueryUseCase.getRecipeById(principalDetails.getUser(), id);
        return ResponseEntity.ok(recipeById);
    }

    // todo 1. 편집 레시피 전부 가져오기
    @GetMapping("/custom/all")
    public ResponseEntity<List<CustomRecipe>> getRecipesByUser(@AuthenticationPrincipal PrincipalDetails details, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "page_size", defaultValue = "10") int pageSize) {
        Page<CustomRecipe> mongoRecipeByUser = recipeQueryUseCase.getCustomRecipeByUser(details.getUser(), page, pageSize);
        return ResponseEntity.ok(mongoRecipeByUser.getContent());
    }

    // todo 1.1 커스텀한 레시피 상제 정보 가져오기
    @GetMapping("/custom/{recipeId}")
    public ResponseEntity<CustomRecipe> getCustomRecipe(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable(name = "recipeId") String id) {
        CustomRecipe response = recipeQueryUseCase.getCustomRecipe(principalDetails.getUser(), id);
        return ResponseEntity.ok(response);
    }

    // todo 2. 편집 레시피 저장
    @PostMapping("/custom/save")
    public ResponseEntity<CustomRecipe> saveCustomRecipe(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody SaveCustomRecipeRequest request) {
        CustomRecipe mongoRecipe = recipeCommandUseCase.saveCustomRecipe(principalDetails.getUser(), request);
        log.info(mongoRecipe.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(mongoRecipe);
    }

    // todo 3. 레시피 북마크(원본)
    @PutMapping("/{recipeId}/bookmark")
    public ResponseEntity<BookmarkedRecipe> bookMarkRecipe(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable(name = "recipeId") String recipeId) {
        BookmarkedRecipe bookmarkedRecipe = recipeCommandUseCase.bookmarkRecipe(principalDetails.getUser(), recipeId);
        return ResponseEntity.ok(bookmarkedRecipe);
    }

    // todo 3.1 북마크했는지 조회
    @GetMapping("/{recipeId}/bookmark")
    public ResponseEntity<IsBookmarkedResponse> checkBookmarked(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable(name = "recipeId") String recipeId) {
        boolean isBookmarked = recipeQueryUseCase.checkBookmarked(principalDetails.getUser(), recipeId);
        IsBookmarkedResponse response = new IsBookmarkedResponse(recipeId, isBookmarked);

        return ResponseEntity.ok(response);
    }


    // todo 3.2 북마크한 레시피 전부 가져오기
    @GetMapping("/bookmark/all")
    public ResponseEntity<List<Recipe>> getAllBookmarkedRecipes(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<Recipe> response = recipeQueryUseCase.getBookmarkedRecipes(principalDetails.getUser());
        return ResponseEntity.ok(response);
    }


    // todo 4. 레시피 먹기 기능(커스텀 + 원본)
    @PutMapping("/{recipeId}/eat")
    public ResponseEntity<UserEaten> eatRecipe(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                       @PathVariable(name = "recipeId") String recipeId,
                                       @RequestParam(name = "type") String recipeType) {    // [original, custom]
        EatRecipeRequest request = new EatRecipeRequest(recipeId, recipeType);
        UserEaten userEaten = recipeCommandUseCase.checkEatRecipe(principalDetails.getUser(), request);
        return ResponseEntity.ok(userEaten);
    }
}
