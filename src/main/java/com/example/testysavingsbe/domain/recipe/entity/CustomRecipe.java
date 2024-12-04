package com.example.testysavingsbe.domain.recipe.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

@ToString
@Document(collection = "custom_recipe")
@Getter
public class CustomRecipe implements Serializable {

    @Id
    private String id;

    @Field("user_id")
    private final Long userId;

    @Field("title")
    private final String title;

    @Field("main_img")
    private final String mainImg;

    @Field("type_key")
    private final String typeKey;

    @Field("method_key")
    private final String methodKey;

    @Field("servings")
    private final String servings;

    @Field("cooking_time")
    private final String cookingTime;

    @Field("difficulty")
    private final String difficulty;

    @Field("ingredients")
    private final List<String> ingredients;

    @Field("cooking_order")
    private final List<String> cookingOrder;

    @Field("cooking_img")
    private final List<String> cookingImg;

    @Field("hashtag")
    private final List<String> hashtag;

    @Field("tips")
    private final List<String> tips;

    @Field("recipe_type")
    private final List<String> recipeType;

    @Builder
    public CustomRecipe(Long userId, String title, String mainImg, String typeKey, String methodKey,
        String servings, String cookingTime, String difficulty, List<String> ingredients,
        List<String> cookingOrder, List<String> cookingImg, List<String> hashtag, List<String> tips,
        List<String> recipeType) {
        this.userId = userId;
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
}
