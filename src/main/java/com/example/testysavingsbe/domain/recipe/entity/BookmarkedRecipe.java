package com.example.testysavingsbe.domain.recipe.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bookmarks")
@Getter
public class BookmarkedRecipe {
    @Id
    private String id;
    private final Long userId;
    private final String recipeId;

    @Builder
    public BookmarkedRecipe(Long userId, String recipeId) {
        this.userId = userId;
        this.recipeId = recipeId;
    }
}
