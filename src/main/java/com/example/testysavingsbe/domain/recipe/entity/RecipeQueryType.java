package com.example.testysavingsbe.domain.recipe.entity;

public enum RecipeQueryType {
    BOOKMARK, EATEN;

    public static RecipeQueryType fromString(String type) {
        try{
            return RecipeQueryType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid query type: " + type);
        }

    }
}
