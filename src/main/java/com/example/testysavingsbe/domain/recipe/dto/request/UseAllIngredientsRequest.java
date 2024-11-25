package com.example.testysavingsbe.domain.recipe.dto.request;

import java.util.List;

public record UseAllIngredientsRequest(
        String originalRecipeId,
        List<String> dislikeIngredients,
        List<String> basicSeasoning,
        List<String> mustUseIngredients
) {
}
