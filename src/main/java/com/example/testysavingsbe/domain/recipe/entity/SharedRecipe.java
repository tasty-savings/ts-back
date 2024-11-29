package com.example.testysavingsbe.domain.recipe.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("shared_recipe")
@NoArgsConstructor
@Getter
public class SharedRecipe {
    String id;
    String uuid;
    String customRecipeId;

    @Builder
    public SharedRecipe(String uuid, String customRecipeId) {
        this.uuid = uuid;
        this.customRecipeId = customRecipeId;
    }
}
