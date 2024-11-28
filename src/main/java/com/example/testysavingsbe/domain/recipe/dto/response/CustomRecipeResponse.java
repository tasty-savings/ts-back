package com.example.testysavingsbe.domain.recipe.dto.response;

import com.example.testysavingsbe.domain.recipe.entity.CustomRecipe;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;

public class CustomRecipeResponse implements RecipeResponse{
    @JsonProperty("id")
    String id;

    @JsonProperty("title")
    String title;

    @JsonProperty("main_img")
    String mainImg;

    @JsonProperty("type_key")
    String typeKey;

    @JsonProperty("method_key")
    String methodKey;

    @JsonProperty("servings")
    String servings;

    @JsonProperty("cooking_time")
    String cookingTime;

    @JsonProperty("difficulty")
    String difficulty;

    @JsonProperty("ingredients")
    List<String> ingredients;

    @JsonProperty("cooking_order")
    List<String> cookingOrder;

    @JsonProperty("cooking_img")
    List<String> cookingImg;

    @JsonProperty("hashtag")
    List<String> hashtag;

    @JsonProperty("tips")
    List<String> tips;

    @JsonProperty("recipe_type")
    List<String> recipeType;

    @Builder
    public CustomRecipeResponse(String id, String title, String mainImg, String typeKey,
        String methodKey, String servings, String cookingTime, String difficulty,
        List<String> ingredients, List<String> cookingOrder, List<String> cookingImg,
        List<String> hashtag, List<String> tips, List<String> recipeType) {
        this.id = id;
        this.title = title;
        this.mainImg = mainImg;
        this.typeKey = typeKey;
        this.methodKey = methodKey;
        this.servings = servings;
        this.cookingTime = cookingTime;
        this.difficulty = difficulty;
        this.ingredients = ingredients;
        this.cookingOrder = cookingOrder;
        this.cookingImg = cookingImg;
        this.hashtag = hashtag;
        this.tips = tips;
        this.recipeType = recipeType;
    }

    public static CustomRecipeResponse from(CustomRecipe customRecipe) {
        return CustomRecipeResponse.builder().id(customRecipe.getId())
            .title(customRecipe.getTitle()).mainImg(customRecipe.getMainImg())
            .typeKey(customRecipe.getTypeKey()).methodKey(customRecipe.getMethodKey())
            .servings(customRecipe.getServings()).cookingTime(customRecipe.getCookingTime())
            .difficulty(customRecipe.getDifficulty()).ingredients(customRecipe.getIngredients())
            .cookingOrder(customRecipe.getCookingOrder()).cookingImg(customRecipe.getCookingImg())
            .hashtag(customRecipe.getHashtag()).tips(customRecipe.getTips())
            .recipeType(customRecipe.getRecipeType()).build();
    }
}
