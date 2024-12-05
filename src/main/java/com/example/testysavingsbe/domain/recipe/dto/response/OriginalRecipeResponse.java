package com.example.testysavingsbe.domain.recipe.dto.response;

import com.example.testysavingsbe.domain.recipe.entity.Recipe;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

public class OriginalRecipeResponse implements RecipeResponse {

    @JsonProperty("id")
    String id;

    @JsonProperty("tag")
    String tag = "original";

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
    public OriginalRecipeResponse(String id,String title, String mainImg,
        String typeKey,
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

    public static OriginalRecipeResponse fromRecipe(Recipe recipe) {
        return OriginalRecipeResponse.builder().id(recipe.getId())
            .title(recipe.getTitle()).mainImg(recipe.getMainImg())
            .typeKey(recipe.getTypeKey()).methodKey(recipe.getMethodKey())
            .servings(recipe.getServings()).cookingTime(recipe.getCookingTime())
            .difficulty(recipe.getDifficulty()).ingredients(recipe.getIngredients())
            .cookingOrder(recipe.getCookingOrder()).cookingImg(recipe.getCookingImages())
            .hashtag(recipe.getHashtags()).tips(recipe.getTips())
            .recipeType(recipe.getRecipeType()).build();
    }
}
