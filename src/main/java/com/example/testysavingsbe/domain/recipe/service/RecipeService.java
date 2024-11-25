package com.example.testysavingsbe.domain.recipe.service;

import com.example.testysavingsbe.domain.ingredient.entity.Food;
import com.example.testysavingsbe.domain.ingredient.repository.FoodRepository;
import com.example.testysavingsbe.domain.recipe.dto.request.LeftoverCookingRequest;
import com.example.testysavingsbe.domain.recipe.dto.request.EatRecipeRequest;
import com.example.testysavingsbe.domain.recipe.dto.request.SaveCustomRecipeRequest;
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
    public BookmarkedRecipe bookmarkRecipe(User user, String recipeId){
        Optional<BookmarkedRecipe> byUserIdAndRecipeId = bookmarkedRepository.findByUserIdAndRecipeId(
            user.getId(), recipeId);

        if (byUserIdAndRecipeId.isPresent()){
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
            UserEaten.userEatenBuilder()
                .userId(user.getId())
                .eatenRecipes(new ArrayList<>())
                .build()
        );

        userEaten.getEatenRecipes().add(UserEaten.EatenRecipe.emptyEatenBuilder()
            .recipeId(request.recipeId())
            .recipeType(request.recipeType())
            .createAt(LocalDateTime.now().toString())
            .build());

        userEatenRepository.save(userEaten);

        return userEaten;
    }

    // todo
    @Override
    public AIRecipeResponse createRecipeFromIngredients(User user,
        RecipeFromIngredientsRequest request) {
        Recipe orignalRecipe = recipeRepository.findById(request.originalRecipeId())
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 레시피입니다."));
        List<String> userAllergy = user.getAllergy().stream()
            .map(Allergy::getAllergy)
            .toList();
        List<String> userIngredients = foodRepository.findAllByUser(user).stream()
            .map(Food::getFoodName)
            .toList();

        Mono<LeftoverCookingRequest> aiRequest = Mono.just(
            LeftoverCookingRequest.builder()
                .userAllergyIngredients(userAllergy)
                .userDislikeIngredients(request.dislikeIngredients())
                .userSpicyLevel(String.valueOf(user.getSpicyLevel()))
                .userCookingLevel(String.valueOf(user.getCookingLevel()))
                .userOwnedIngredients(userIngredients)
                .userBasicSeasoning(request.basicSeasoning())
                .mustUseIngredients(request.mustUseIngredients())
                .build()
        );

        AIRecipeResponse block = aiWebClient.post()
            .uri(urlBuilder -> urlBuilder
                .path("/recipe")
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

        OriginalRecipeResponse before = OriginalRecipeResponse.builder()
            .id(orignalRecipe.getId())
            .title(orignalRecipe.getTitle())


            .build();
        AIChangeRecipeResponse response = new AIChangeRecipeResponse(before, block);

        return block;
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

    // AI
    // before/after 같이 주기
    // 냉장고 파먹기 기능
    // 궁금증 -> 유저가 지정한 식재료를 주는 것인가
    //

    @Override
    public CustomRecipe saveCustomRecipe(User user, SaveCustomRecipeRequest request) {
        CustomRecipe recipe = CustomRecipe.builder()
            .userId(user.getId())
            .title(request.title())
            .mainImg(request.mainImg())
            .typeKey(request.typeKey())
            .methodKey(request.methodKey())
            .servings(request.servings())
            .cookingTime(request.cookingTime())
            .difficulty(request.difficulty())
            .ingredients(request.ingredients())
            .cookingOrder(request.cookingOrder())
            .cookingImg(request.cookingImg())
            .hashtag(request.hashtag())
            .tips(request.tips())
            .recipeType(request.recipeType())
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

}
