package com.example.testysavingsbe.domain.recipe.dto.response;

import lombok.Builder;

@Builder
public record RecipeResponse(
        Long id,
        String content,
        Boolean isEaten,
        Boolean isBookMarked,
        String userName
) {
}
