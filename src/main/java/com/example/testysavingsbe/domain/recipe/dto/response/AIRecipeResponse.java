package com.example.testysavingsbe.domain.recipe.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AIRecipeResponse(
        @JsonProperty("main_changes_from_original_recipe")
        String mainChangesFromOriginalRecipe,

        @JsonProperty("reason_for_changes")
        String reasonForChanges,

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
        String recipeTips,

        @JsonProperty("recipe_type")
        String recipeType,

        @JsonProperty("unchanged_parts_and_reasons")
        String unchangedPartsAndReasons
) implements AIRecipe {
}
