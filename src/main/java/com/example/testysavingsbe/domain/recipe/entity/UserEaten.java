package com.example.testysavingsbe.domain.recipe.entity;

import jakarta.persistence.Id;
import java.util.ArrayList;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "user_eaten")
@Getter
public class UserEaten {

    @Id
    private String id;

    private Long userId;

    @Field("eaten_recipes")
    private List<EatenRecipe> eatenRecipes;

    @Builder(builderMethodName = "userEatenBuilder")
    public UserEaten(Long userId, List<EatenRecipe> eatenRecipes) {
        this.userId = userId;
        this.eatenRecipes =
            (eatenRecipes != null) ? new ArrayList<>(eatenRecipes) : new ArrayList<>();
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

    public void addEatenRecipe(String recipeId, String recipeType, String createAt) {
        if (this.eatenRecipes == null || this.eatenRecipes.isEmpty()) {
            this.eatenRecipes = new ArrayList<>();
        }

        if (isEaten(recipeId)) {
            throw new IllegalArgumentException("Recipe with ID " + recipeId + " is already eaten.");
        }

        EatenRecipe eatenRecipe = EatenRecipe.emptyEatenBuilder()
            .recipeId(recipeId)
            .recipeType(recipeType)
            .createAt(createAt)
            .build();

        this.eatenRecipes.add(eatenRecipe);
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
