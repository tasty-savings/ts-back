package com.example.testysavingsbe.domain.recipe.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class LeftoverCookingRequest {
    @JsonProperty("user_allergy_ingredients")
    private List<String> userAllergyIngredients;

    @JsonProperty("user_dislike_ingredients")
    private List<String> userDislikeIngredients;

    @JsonProperty("user_spicy_level")
    private String userSpicyLevel;

    @JsonProperty("user_cooking_level")
    private String userCookingLevel;

    @JsonProperty("user_owned_ingredients")
    private List<String> userOwnedIngredients;

    @JsonProperty("user_basic_seasoning")
    private List<String> userBasicSeasoning;

    @JsonProperty("must_use_ingredients")
    private List<String> mustUseIngredients;


    @Builder
    public LeftoverCookingRequest(List<String> userAllergyIngredients, List<String> userDislikeIngredients, String userSpicyLevel, String userCookingLevel, List<String> userOwnedIngredients, List<String> userBasicSeasoning, List<String> mustUseIngredients) {
        this.userAllergyIngredients = userAllergyIngredients;
        this.userDislikeIngredients = userDislikeIngredients;
        this.userSpicyLevel = userSpicyLevel;
        this.userCookingLevel = userCookingLevel;
        this.userOwnedIngredients = userOwnedIngredients;
        this.userBasicSeasoning = userBasicSeasoning;
        this.mustUseIngredients = mustUseIngredients;
    }
}
