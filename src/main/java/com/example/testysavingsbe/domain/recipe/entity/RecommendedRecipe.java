package com.example.testysavingsbe.domain.recipe.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Document(collection = "recipe")
public class RecommendedRecipe {
    @Id
    private String id;

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
}
