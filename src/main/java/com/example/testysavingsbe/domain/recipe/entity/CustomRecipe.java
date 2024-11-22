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
    private Long userId;

    @Field("title")
    private String title;

    @Field("main_img")
    private String mainImg;

    @Field("type_key")
    private String typeKey;

    @Field("method_key")
    private String methodKey;

    @Field("servings")
    private String servings;

    @Field("cooking_time")
    private String cookingTime;

    @Field("difficulty")
    private String difficulty;

    @Field("ingredients")
    private List<String> ingredients;

    @Field("cooking_order")
    private List<String> cookingOrder;

    @Field("cooking_img")
    private List<String> cookingImg;

    @Field("hashtag")
    private List<String> hashtag;

    @Field("tips")
    private List<String> tips;

    @Field("recipe_type")
    private List<String> recipeType;

    @Builder
    public CustomRecipe(Long userId, String title, String mainImg, String typeKey, String methodKey, String servings, String cookingTime, String difficulty, List<String> ingredients, List<String> cookingOrder, List<String> cookingImg, List<String> hashtag, List<String> tips, List<String> recipeType) {
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
