package com.example.testysavingsbe.domain.recipe.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;

public record IsBookmarkedResponse(
    @JsonProperty("recipe_id")
        String recipeId,
        @JsonProperty("is_bookmarked")
        boolean isBookmarked
) {
}
