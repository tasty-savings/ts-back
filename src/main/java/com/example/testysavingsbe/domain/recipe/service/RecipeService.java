package com.example.testysavingsbe.domain.recipe.service;

import com.example.testysavingsbe.domain.ingredient.entity.Food;
import com.example.testysavingsbe.domain.ingredient.repository.FoodRepository;
import com.example.testysavingsbe.domain.recipe.dto.ResponseBuilder;
import com.example.testysavingsbe.domain.recipe.dto.request.LeftoverCookingRequest;
import com.example.testysavingsbe.domain.recipe.dto.request.EatRecipeRequest;
import com.example.testysavingsbe.domain.recipe.dto.request.SaveCustomRecipeRequest;
import com.example.testysavingsbe.domain.recipe.dto.request.SimplifyRecipeToAiRequest;
import com.example.testysavingsbe.domain.recipe.dto.response.AIChangeRecipeResponse;
import com.example.testysavingsbe.domain.recipe.dto.response.AIRecipeResponse;
import com.example.testysavingsbe.domain.recipe.dto.response.EatenRecipeResponse;
import com.example.testysavingsbe.domain.recipe.dto.response.OriginalRecipeResponse;
import com.example.testysavingsbe.domain.recipe.entity.*;
import com.example.testysavingsbe.domain.recipe.entity.UserEaten.EatenRecipe;
import com.example.testysavingsbe.domain.recipe.port.AiWebClientAdapter;
import com.example.testysavingsbe.domain.recipe.repository.*;
import com.example.testysavingsbe.domain.recipe.service.usecase.RecipeCommandUseCase;
import com.example.testysavingsbe.domain.recipe.service.usecase.RecipeQueryUseCase;
import com.example.testysavingsbe.domain.user.entity.Allergy;
import com.example.testysavingsbe.domain.user.entity.User;
import com.example.testysavingsbe.domain.user.entity.UserPreferType;
import com.example.testysavingsbe.domain.user.repository.UserPreferTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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
    private final UserPreferTypeRepository userPreferTypeRepository;
    private final AiWebClientAdapter aiAdapter;
    private final ResponseBuilder responseBuilder;

    @Override
    public BookmarkedRecipe bookmarkRecipe(User user, String recipeId) {
        Optional<BookmarkedRecipe> byUserIdAndRecipeId = bookmarkedRepository.findByUserIdAndRecipeId(
            user.getId(), recipeId);

        if (byUserIdAndRecipeId.isPresent()) {
            throw new IllegalStateException("북마크된 레시피입니다.");
        }

        BookmarkedRecipe bookmarkedRecipe = BookmarkedRecipe
            .builder().userId(user.getId())
            .recipeId(recipeId).build();

        bookmarkedRepository.save(bookmarkedRecipe);

        return bookmarkedRecipe;
    }


    @Override
    public UserEaten checkEatRecipe(User user, EatRecipeRequest request) {
        UserEaten userEaten = userEatenRepository.findByUserId(user.getId()).orElse(
            UserEaten.userEatenBuilder().userId(user.getId()).eatenRecipes(new ArrayList<>())
                .build());

        userEaten.addEatenRecipe(request.recipeId(), request.recipeType(),
            LocalDateTime.now().toString());
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

        AIRecipeResponse after = aiAdapter.requestCreateRecipeUseIngredients(request,
            aiRequest);

        OriginalRecipeResponse before = responseBuilder.buildOriginalRecipeResponse(orignalRecipe);

        return new AIChangeRecipeResponse(before, after);
    }


    @Override
    public AIChangeRecipeResponse simplifyRecipe(User user, String recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 레시피입니다."));

        List<String> userAllergy = user.getAllergy().stream().map(Allergy::getAllergy).toList();

        SimplifyRecipeToAiRequest request = new SimplifyRecipeToAiRequest(userAllergy,
            user.getCookingLevel().getDisplayName());

        AIRecipeResponse after = aiAdapter.requestRecipeMakeSimplify(recipeId, request);

        OriginalRecipeResponse before = responseBuilder.buildOriginalRecipeResponse(recipe);

        return new AIChangeRecipeResponse(before, after);
    }


    @Override
    public boolean checkBookmarked(User user, String recipeId) {
        Optional<BookmarkedRecipe> byUserIdAndRecipeId = bookmarkedRepository.findByUserIdAndRecipeId(
            user.getId(), recipeId);
        return byUserIdAndRecipeId.isPresent();
    }

    @Override
    public List<OriginalRecipeResponse> getBookmarkedRecipes(User user) {
        List<BookmarkedRecipe> allByUserId = bookmarkedRepository.findAllByUserId(user.getId());
        List<OriginalRecipeResponse> response = new ArrayList<>();
        allByUserId.forEach(r -> {
            Optional<Recipe> recipe = recipeRepository.findById(r.getRecipeId());
            if (recipe.isPresent()) {
                OriginalRecipeResponse originalRecipeResponse = responseBuilder.buildOriginalRecipeResponse(
                    recipe.get());
                response.add(originalRecipeResponse);
            }
        });

        return response;
    }

    @Override
    public List<EatenRecipeResponse> getAllEatenRecipe(User user) {
        UserEaten userEaten = userEatenRepository.findByUserId(user.getId())
            .orElseThrow(() -> new EntityNotFoundException("먹은 레시피가 존재하지 않습니다."));

        List<EatenRecipe> eatenRecipes = userEaten.getEatenRecipes();

        // Recipe ID와 CustomRecipe ID를 분리하여 한 번에 조회
        Map<Boolean, List<String>> recipeIdsByType = eatenRecipes.stream().collect(
            Collectors.partitioningBy(r -> "original".equals(r.getRecipeType()),
                Collectors.mapping(EatenRecipe::getRecipeId, Collectors.toList())));

        // 각각의 타입에 대해 일괄 조회
        List<Recipe> recipes = recipeRepository.findAllById(recipeIdsByType.get(true));
        List<CustomRecipe> customRecipes = customRecipeRepository.findAllById(
            recipeIdsByType.get(false));

        // 조회된 데이터를 Map으로 변환해 빠르게 접근
        Map<String, Recipe> recipeMap = recipes.stream()
            .collect(Collectors.toMap(Recipe::getId, Function.identity()));
        Map<String, CustomRecipe> customRecipeMap = customRecipes.stream()
            .collect(Collectors.toMap(CustomRecipe::getId, Function.identity()));

        // 결과 생성
        return eatenRecipes.stream().map(r -> {
            if ("original".equals(r.getRecipeType())) {
                Recipe originalRecipe = recipeMap.get(r.getRecipeId());
                if (originalRecipe != null) {
                    return responseBuilder.buildEatenRecipeResponse(originalRecipe);
                }
            } else {
                CustomRecipe customRecipe = customRecipeMap.get(r.getRecipeId());
                if (customRecipe != null) {
                    return responseBuilder.buildEatenRecipeResponse(customRecipe);
                }
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
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
        CustomRecipe recipe = buildCustomRecipe(user, request);

        customRecipeRepository.save(recipe);
        return recipe;
    }

    private CustomRecipe buildCustomRecipe(User user, SaveCustomRecipeRequest request) {
        return CustomRecipe.builder().userId(user.getId()).title(request.title())
            .mainImg(request.mainImg()).typeKey(request.typeKey()).methodKey(request.methodKey())
            .servings(request.servings()).cookingTime(request.cookingTime())
            .difficulty(request.difficulty()).ingredients(request.ingredients())
            .cookingOrder(request.cookingOrder()).cookingImg(request.cookingImg())
            .hashtag(request.hashtag()).tips(request.tips()).recipeType(request.recipeType())
            .build();
    }

    @Override
    public void removeEatenRecipe(User user, String recipeId) {
        UserEaten userEaten = userEatenRepository.findByUserId(user.getId())
            .orElseThrow(() -> new EntityNotFoundException("먹은 레시피가 존재하지 않습니다."));
        if (!userEaten.isEaten(recipeId)) {
            throw new EntityNotFoundException("먹지않은 레시피입니다.");
        }
        userEaten.deleteEatenRecipe(recipeId);

        userEatenRepository.save(userEaten);
    }


    @Override
    public CustomRecipe getCustomRecipe(User user, String id) {
        return customRecipeRepository.findByIdAndUserId(id, user.getId())
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 커스텀 레시피입니다."));
    }


    @Override
    public Recipe getRecipeById(User user, String id) {
        return recipeRecommendRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 레시피입니다."));
    }

    @Override
    public List<OriginalRecipeResponse> getRecommendedRecipe(User user, int page, int size) {
        List<UserPreferType> userPreferTypes = userPreferTypeRepository.findAllByUser(user);
        List<String> userPreferTypeStrings = userPreferTypes.stream()
            .map(UserPreferType::getDisplayName).toList();
        Map<String, List<String>> request = new HashMap<>();
        request.put("search_types", userPreferTypeStrings);

        List<String> recipeIds = aiAdapter.requestRecommendRecipeList(request);

        List<Recipe> allById = recipeRepository.findAllById(recipeIds);

        return allById.stream()
            .map(responseBuilder::buildOriginalRecipeResponse).toList();

    }


}
