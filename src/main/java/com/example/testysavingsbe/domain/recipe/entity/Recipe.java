package com.example.testysavingsbe.domain.recipe.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Document(collection = "recipe")
public class Recipe {
    @Id
    private String id;

    @Field("title")
    @Indexed
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

    @Field("cooking_steps")
    private List<String> cookingOrder;

    @Field("cooking_images")
    private List<String> cookingImages;

    @Field("hashtags")
    private List<String> hashtags;

    @Field("tips")
    private List<String> tips;

    @Field("recipe_type")
    private List<String> recipeType;
}
