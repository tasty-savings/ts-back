package com.example.testysavingsbe.domain.recipe.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record NutritionBasedRecipeCreateResponse(
    @JsonProperty("main_changes_from_original_recipe")
    List<String> mainChangesFromOriginalRecipe,
    @JsonProperty("new_recipe_food_group_composition")
    List<FoodConsumption> newRecipeFoodGroupComposition,
    @JsonProperty("original_recipe_food_group_composition")
    List<FoodConsumption> originalRecipeFoodGroupComposition,
    @JsonProperty("reason_for_changes")
    List<String> reasonForChanges,
    @JsonProperty("recipe_cooking_order")
    List<String> recipeCookingOrder,
    @JsonProperty("recipe_cooking_time")
    String recipeCookingTime,
    @JsonProperty("recipe_difficulty")
    String recipeDifficulty,
    @JsonProperty("recipe_ingredients")
    List<String> recipeIngredients,
    @JsonProperty("recipe_menu_name")
    String recipeMenuName,
    @JsonProperty("recipe_tips")
    List<String> recipeTips,
    @JsonProperty("recipe_type")
    String recipeType,
    @JsonProperty("unchanged_parts_and_reasons")
    List<String> unchangedPartsAndReasons,
    @JsonProperty("user_meal_food_group_requirements")
    List<FoodConsumption> userMealFoodGroupRequirements
) implements AIRecipe
{
    public record FoodConsumption(
        @JsonProperty("food_group") String foodGroup,
        @JsonProperty("amount") Float amount
    ){

    }
}