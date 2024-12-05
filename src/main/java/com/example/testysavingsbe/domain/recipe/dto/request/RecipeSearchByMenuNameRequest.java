package com.example.testysavingsbe.domain.recipe.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;

public record RecipeSearchByMenuNameRequest(
        @NonNull
        @NotBlank(message = "빈 문자열 입니다.")
            @JsonProperty("menu_name")
        String menuName
) {
}
