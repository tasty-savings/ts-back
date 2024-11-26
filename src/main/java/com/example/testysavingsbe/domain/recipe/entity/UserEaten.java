package com.example.testysavingsbe.domain.recipe.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "userBookmark")
@Builder
@Getter
public class UserEaten {

    @Id
    private Long userId;

    @Field("eaten_recipes")
    private List<EatenRecipe> eatenRecipes;

    @Builder(builderMethodName = "userEatenBuilder")
    public UserEaten(Long userId, List<EatenRecipe> eatenRecipes) {
        this.userId = userId;
        this.eatenRecipes = eatenRecipes;
    }

    public boolean isEaten(String recipeId) {
        if (this.eatenRecipes == null || this.eatenRecipes.isEmpty()) {
            return false;
        }

        return this.eatenRecipes.stream()
            .anyMatch(recipe ->
                recipe.recipeId.equals(recipeId));
    }

    public void deleteEatenRecipe(String recipeId) {
        if (this.eatenRecipes == null || this.eatenRecipes.isEmpty()) {
            return;
        }
        this.eatenRecipes.stream()
            .filter(recipe -> recipe.recipeId.equals(recipeId))
            .findFirst()
            .ifPresent(recipe -> this.eatenRecipes.remove(recipe));
    }

    @Getter
    public static class EatenRecipe {

        private String recipeId;
        private String recipeType;
        private String createAt;

        @Builder(builderMethodName = "emptyEatenBuilder")
        public EatenRecipe(String recipeId, String recipeType, String createAt) {
            this.recipeId = recipeId;
            this.recipeType = recipeType;
            this.createAt = createAt;
        }

    }

}
