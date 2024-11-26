package com.example.testysavingsbe.domain.recipe.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;

@Builder
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
}
