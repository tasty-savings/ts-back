package com.example.testysavingsbe.domain.recipe.service;

import com.example.testysavingsbe.domain.recipe.dto.LeftoverCookingRequest;
import com.example.testysavingsbe.domain.recipe.dto.response.RecipeResponse;
import com.example.testysavingsbe.domain.recipe.entity.Recipe;
import com.example.testysavingsbe.domain.recipe.entity.RecipeQueryType;
import com.example.testysavingsbe.domain.recipe.entity.RecommendedRecipe;
import com.example.testysavingsbe.domain.recipe.repository.RecipeRepository;
import com.example.testysavingsbe.domain.recipe.repository.RecommendRecipeRepository;
import com.example.testysavingsbe.domain.recipe.service.usecase.RecipeCommandUseCase;
import com.example.testysavingsbe.domain.recipe.service.usecase.RecipeQueryUseCase;
import com.example.testysavingsbe.domain.user.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService implements RecipeQueryUseCase, RecipeCommandUseCase {
    private final RecipeRepository recipeRepository;
    private final RecommendRecipeRepository recipeRecommendRepository;

    @Override
    @Transactional
    public RecipeResponse generateRecipe(RecipeGenerateServiceRequest request) {
        String recipeValue = generateRecipeByAI(request.recipeName());
        Recipe recipe = Recipe.builder()
                .user(request.user())
                .content(recipeValue)
                .build();
        recipeRepository.save(recipe);

        return mapToRecipeResponse(recipe);
    }

    @Override
    @Transactional
    public RecipeResponse checkEatRecipe(RecipeUpdateServiceRequest request) {
        Recipe recipe = recipeRepository.findById(request.recipeId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 레시피입니다."));
        recipe.updateEaten();

        return mapToRecipeResponse(recipe);
    }

    @Override
    @Transactional
    public RecipeResponse bookmarkRecipe(RecipeUpdateServiceRequest request) {
        Recipe recipe = recipeRepository.findById(request.recipeId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 레시피입니다."));
        recipe.updateBookMarked();

        return mapToRecipeResponse(recipe);
    }


    // 냉장고 파먹기 기능
    // 궁금증 -> 유저가 지정한 식재료를 주는 것인가
    //
    @Override
    public void createRecipeFromIngredients(User user) {
//        request = new
        // 냉장고 파먹기 리퀘스트
//        Mono<LeftoverCookingRequest> request = Mono.just(
//                LeftoverCookingRequest.builder()
//                        .userAllergyIngredients()
//                        .userDislikeIngredients()
//                        .userSpicyLevel()
//                        .userCookingLevel()
//                        .userOwnedIngredients()
//                        .userBasicSeasoning()
//                        .mustUseIngredients()
//                        .build()
//        );
//        aiWebClient.post()
//                .uri(urlBuilder -> urlBuilder
//                        .path("/ai/recipe")
//                        .queryParam("recipe_change_type", 1)
//                        .queryParam("recipe_info_index", 0) // 몽고 DB에 저장되어 있는 레시피 id
//                        .build())
//                .body(request, )
//                .retrieve()
//                .bodyToMono(AIRecipeResponse.class)
//
//                .block();
    }


    @Override
    @Transactional(readOnly = true)
    public List<RecipeResponse> getRecipeByQuery(String type, User user) {
        return switch (RecipeQueryType.fromString(type)) {
            case BOOKMARK -> getBookMarkedRecipes(user);
            case EATEN -> getEatenRecipes(user);
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

    @Override
    public Page<RecipeResponse> getRecipes(User user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Recipe> allByUser = recipeRepository.findAllByUser(user, pageable);
        return allByUser.map(this::mapToRecipeResponse);
    }

    @Override
    public Page<RecommendedRecipe> getRecommendedRecipe(User user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RecommendedRecipe> all = recipeRecommendRepository.findAll(pageable);
        return all;
    }

    private List<RecipeResponse> getEatenRecipes(User user) {
        List<Recipe> eatenRecipes = recipeRepository.findAllEatenRecipeByUser(user);
        return eatenRecipes.stream()
                .map(this::mapToRecipeResponse)
                .toList();
    }

    private List<RecipeResponse> getBookMarkedRecipes(User user) {
        List<Recipe> bookMarkedRecipes = recipeRepository.findAllBookMarkedRecipeByUser(user);
        return bookMarkedRecipes.stream()
                .map(this::mapToRecipeResponse)
                .toList();
    }

    private RecipeResponse mapToRecipeResponse(Recipe recipe) {
        return RecipeResponse.builder()
                .id(recipe.getId())
                .content(recipe.getContent())
                .isEaten(recipe.getIsEaten())
                .isBookMarked(recipe.getIsBookMarked())
                .userName(recipe.getUser().getUsername())
                .build();
    }

    // TODO AI 모델 완성시 연결
    private String generateRecipeByAI(String recipeName) {
        return "Recipe Name: " + recipeName + "\n" + "This recipe generated by AI model";
    }


}
