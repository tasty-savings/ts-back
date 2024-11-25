package com.example.testysavingsbe.domain.recipe.service;

import com.example.testysavingsbe.domain.ingredient.entity.Food;
import com.example.testysavingsbe.domain.ingredient.repository.FoodRepository;
import com.example.testysavingsbe.domain.recipe.dto.request.LeftoverCookingRequest;
import com.example.testysavingsbe.domain.recipe.dto.request.EatRecipeRequest;
import com.example.testysavingsbe.domain.recipe.dto.request.SaveCustomRecipeRequest;
import com.example.testysavingsbe.domain.recipe.dto.request.SimplifyRecipeToAiRequest;
import com.example.testysavingsbe.domain.recipe.dto.response.AIChangeRecipeResponse;
import com.example.testysavingsbe.domain.recipe.dto.response.AIRecipeResponse;
import com.example.testysavingsbe.domain.recipe.dto.response.OriginalRecipeResponse;
import com.example.testysavingsbe.domain.recipe.entity.*;
import com.example.testysavingsbe.domain.recipe.repository.*;
import com.example.testysavingsbe.domain.recipe.service.usecase.RecipeCommandUseCase;
import com.example.testysavingsbe.domain.recipe.service.usecase.RecipeQueryUseCase;
import com.example.testysavingsbe.domain.user.entity.Allergy;
import com.example.testysavingsbe.domain.user.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecipeService implements RecipeQueryUseCase, RecipeCommandUseCase {

    private final RecipeRepository recipeRecommendRepository;
    private final CustomRecipeRepository customRecipeRepository;
    private final UserEatenRepository userEatenRepository;
    private final BookmarkedRepository bookmarkedRepository;
    private final RecipeRepository recipeRepository;
    private final FoodRepository foodRepository;
    private final WebClient aiWebClient;

    @Override
    public BookmarkedRecipe bookmarkRecipe(User user, String recipeId) {
        Optional<BookmarkedRecipe> byUserIdAndRecipeId = bookmarkedRepository.findByUserIdAndRecipeId(
            user.getId(), recipeId);

        if (byUserIdAndRecipeId.isPresent()) {
            throw new IllegalStateException("북마크된 레시피입니다.");
        }

        BookmarkedRecipe bookmarkedRecipe = BookmarkedRecipe.builder().userId(user.getId())
            .recipeId(recipeId).build();

        bookmarkedRepository.save(bookmarkedRecipe);

        return bookmarkedRecipe;
    }


    @Override
    public UserEaten checkEatRecipe(User user, EatRecipeRequest request) {
        UserEaten userEaten = userEatenRepository.findById(user.getId()).orElse(
            UserEaten.userEatenBuilder().userId(user.getId()).eatenRecipes(new ArrayList<>())
                .build());

        userEaten.getEatenRecipes().add(
            UserEaten.EatenRecipe.emptyEatenBuilder().recipeId(request.recipeId())
                .recipeType(request.recipeType()).createAt(LocalDateTime.now().toString()).build());

        userEatenRepository.save(userEaten);

        return userEaten;
    }

    /**
     * 유저가 현재 가지고 있는 식재료를 이용하여 AI를 통해 변환된 레시피를 생성합니다.
     *
     * <p>이 메서드는 다음을 수행합니다:
     * <ul>
     *     <li>유저 알레르기 정보와 소유한 재료를 기반으로 요청을 생성</li>
     *     <li>AI API를 호출하여 변환된 레시피를 가져옴</li>
     *     <li>원본 레시피와 변환된 레시피를 함께 반환</li>
     * </ul>
     * </p>
     *
     * @param user    유저 엔티티, 알레르기 및 소유 재료 정보를 포함
     * @param request {@code RecipeFromIngredientsRequest} 요청 데이터, 변환할 레시피 및 사용자 설정 정보 포함
     * @return 변환된 레시피와 원본 레시피 정보를 포함하는 {@code AIChangeRecipeResponse}
     * @throws EntityNotFoundException 원본 레시피가 존재하지 않는 경우
     */
    @Override
    public AIChangeRecipeResponse createRecipeFromIngredients(User user,
        RecipeFromIngredientsRequest request) {
        Recipe orignalRecipe = recipeRepository.findById(request.originalRecipeId())
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 레시피입니다."));
        List<String> userAllergy = user.getAllergy().stream().map(Allergy::getAllergy).toList();
        List<String> userIngredients = foodRepository.findAllByUser(user).stream()
            .map(Food::getFoodName).toList();

        Mono<LeftoverCookingRequest> aiRequest = Mono.just(
            LeftoverCookingRequest.builder().userAllergyIngredients(userAllergy)
                .userDislikeIngredients(request.dislikeIngredients())
                .userSpicyLevel(String.valueOf(user.getSpicyLevel()))
                .userCookingLevel(String.valueOf(user.getCookingLevel()))
                .userOwnedIngredients(userIngredients).userBasicSeasoning(request.basicSeasoning())
                .mustUseIngredients(request.mustUseIngredients()).build());

        // 요청
        AIRecipeResponse after = aiWebClient.post()
            .uri(urlBuilder -> urlBuilder.path("/recipe")
                .queryParam("recipe_change_type", 1)
                .queryParam("recipe_info_index",
                    request.originalRecipeId()) // 몽고 DB에 저장되어 있는 레시피 id
                .build())
            .body(aiRequest, LeftoverCookingRequest.class)
//                .retrieve()
            .exchangeToMono(response -> {
                log.info("Status code: {}", response.statusCode());
                log.info(response.toString());
                return response.bodyToMono(AIRecipeResponse.class);
            })
//                .bodyToMono(AIRecipeResponse.class)
            .block();

        OriginalRecipeResponse before = convertOriginalRecipeToDto(orignalRecipe);

        return new AIChangeRecipeResponse(before, after);
    }



    @Override
    public AIChangeRecipeResponse simplifyRecipe(User user, String recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 레시피입니다."));
        List<String> userAllergy = user.getAllergy().stream().map(Allergy::getAllergy).toList();

        SimplifyRecipeToAiRequest request = new SimplifyRecipeToAiRequest(
            userAllergy, user.getCookingLevel().getDisplayName());

        AIRecipeResponse after = aiWebClient.post()
            .uri(urlBuilder -> urlBuilder.path("/recipe")
                .queryParam("recipe_change_type", 2)
                .queryParam("recipe_info_index", recipeId)
                .build())
            .body(request, AIRecipeResponse.class)
            .retrieve()
            .bodyToMono(AIRecipeResponse.class)
            .block();

        OriginalRecipeResponse before = convertOriginalRecipeToDto(recipe);

        return new AIChangeRecipeResponse(before, after);
    }


    @Override
    public boolean checkBookmarked(User user, String recipeId) {
        Optional<BookmarkedRecipe> byUserIdAndRecipeId = bookmarkedRepository.findByUserIdAndRecipeId(
            user.getId(), recipeId);
        return byUserIdAndRecipeId.isPresent();
    }

    @Override
    public List<Recipe> getBookmarkedRecipes(User user) {
        List<BookmarkedRecipe> allByUserId = bookmarkedRepository.findAllByUserId(user.getId());
        List<Recipe> response = new ArrayList<>();
        allByUserId.forEach(r -> {
            Optional<Recipe> recipe = recipeRepository.findById(r.getRecipeId());
            recipe.ifPresent(response::add);
        });

        return response;
    }

    /**
     * 유저가 커스텀한 레시피 전부 가져오기
     *
     * @return
     */
    @Override
    public Page<CustomRecipe> getCustomRecipeByUser(User user, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return customRecipeRepository.findMongoRecipesByUserId(user.getId(), pageable);
    }

    @Override
    public CustomRecipe saveCustomRecipe(User user, SaveCustomRecipeRequest request) {
        CustomRecipe recipe = CustomRecipe.builder().userId(user.getId()).title(request.title())
            .mainImg(request.mainImg()).typeKey(request.typeKey()).methodKey(request.methodKey())
            .servings(request.servings()).cookingTime(request.cookingTime())
            .difficulty(request.difficulty()).ingredients(request.ingredients())
            .cookingOrder(request.cookingOrder()).cookingImg(request.cookingImg())
            .hashtag(request.hashtag()).tips(request.tips()).recipeType(request.recipeType())
            .build();

        customRecipeRepository.save(recipe);
        return recipe;
    }


    @Override
    public CustomRecipe getCustomRecipe(User user, String id) {
        CustomRecipe customRecipe = customRecipeRepository.findByIdAndUserId(id, user.getId())
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 커스텀 레시피입니다."));
        return customRecipe;
    }

    // todo 유저에게 받아와야할 정보 정하기

    @Override
    public Recipe getRecipeById(User user, String id) {
        Recipe recipe = recipeRecommendRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 레시피입니다."));
        return recipe;
    }
    // todo

    @Override
    public Page<Recipe> getRecommendedRecipe(User user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Recipe> response = recipeRecommendRepository.findAll(pageable);
        return response;
    }

    private OriginalRecipeResponse convertOriginalRecipeToDto(Recipe orignalRecipe) {
        return OriginalRecipeResponse.builder()
            .id(orignalRecipe.getId())
            .title(orignalRecipe.getTitle())
            .mainImg(orignalRecipe.getMainImg())
            .typeKey(orignalRecipe.getTypeKey())
            .methodKey(orignalRecipe.getMethodKey())
            .servings(orignalRecipe.getServings())
            .cookingTime(orignalRecipe.getCookingTime())
            .difficulty(orignalRecipe.getDifficulty())
            .ingredients(orignalRecipe.getIngredients())
            .cookingOrder(orignalRecipe.getCookingOrder())
            .cookingImg(orignalRecipe.getCookingImg())
            .hashtag(orignalRecipe.getHashtag())
            .tips(orignalRecipe.getTips())
            .recipeType(orignalRecipe.getRecipeType())
            .build();
    }
}
