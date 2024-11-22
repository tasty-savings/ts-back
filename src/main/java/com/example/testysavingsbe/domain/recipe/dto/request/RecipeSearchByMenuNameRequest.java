package com.example.testysavingsbe.domain.recipe.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;

public record RecipeSearchByMenuNameRequest(
        @NonNull
        @NotBlank(message = "빈 문자열 입니다.")
        String menuName
) {
}
