package com.example.testysavingsbe.domain.recipe.dto;

import com.example.testysavingsbe.domain.recipe.dto.response.CustomRecipeResponse;
import com.example.testysavingsbe.domain.recipe.dto.response.EatenRecipeResponse;
import com.example.testysavingsbe.domain.recipe.dto.response.OriginalRecipeResponse;
import com.example.testysavingsbe.domain.recipe.entity.CustomRecipe;
import com.example.testysavingsbe.domain.recipe.entity.Recipe;


public class ResponseBuilder {

    public static final String ORIGINAL_RECIPE_TYPE = "original";
    public static final String CUSTOM_RECIPE_TYPE = "custom";

    public static EatenRecipeResponse buildEatenRecipeResponse(Recipe recipe) {
        OriginalRecipeResponse originalRecipeResponse = buildOriginalRecipeResponse(recipe);
        return EatenRecipeResponse.builder().tag(ORIGINAL_RECIPE_TYPE).data(originalRecipeResponse).build();
    }

    public static EatenRecipeResponse buildEatenRecipeResponse(CustomRecipe customRecipe) {
        CustomRecipeResponse customRecipeResponse = buildCustomRecipeResponse(customRecipe);
        return EatenRecipeResponse.builder().tag(CUSTOM_RECIPE_TYPE).data(customRecipeResponse).build();
    }

    public static OriginalRecipeResponse buildOriginalRecipeResponse(Recipe orignalRecipe) {
        return OriginalRecipeResponse.builder().id(orignalRecipe.getId())
            .title(orignalRecipe.getTitle()).mainImg(orignalRecipe.getMainImg())
            .typeKey(orignalRecipe.getTypeKey()).methodKey(orignalRecipe.getMethodKey())
            .servings(orignalRecipe.getServings()).cookingTime(orignalRecipe.getCookingTime())
            .difficulty(orignalRecipe.getDifficulty()).ingredients(orignalRecipe.getIngredients())
            .cookingOrder(orignalRecipe.getCookingOrder()).cookingImg(orignalRecipe.getCookingImg())
            .hashtag(orignalRecipe.getHashtag()).tips(orignalRecipe.getTips())
            .recipeType(orignalRecipe.getRecipeType()).build();
    }

    public static CustomRecipeResponse buildCustomRecipeResponse(CustomRecipe customRecipe) {
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