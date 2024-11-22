package com.example.testysavingsbe.domain.recipe.dto.response;


public record IsBookmarkedResponse(
        String recipeId,
        boolean isBookmarked
) {
}
