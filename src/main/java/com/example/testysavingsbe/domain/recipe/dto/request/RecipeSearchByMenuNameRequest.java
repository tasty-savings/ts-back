package com.example.testysavingsbe.domain.recipe.dto.request;

import lombok.NonNull;

public record RecipeSearchByMenuNameRequest(
        @NonNull String menuName
) {
}
