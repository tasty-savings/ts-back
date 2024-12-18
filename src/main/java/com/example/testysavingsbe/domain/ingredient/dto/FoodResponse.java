package com.example.testysavingsbe.domain.ingredient.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record FoodResponse(
    @JsonProperty("id")
    Long id,
    @JsonProperty("food_name")
    String foodName,
    @JsonProperty("saving_type")
    String savingType,
    @JsonProperty("food_type")
    String foodType,
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("expiration_date")
    LocalDate expirationDate
) {

}
