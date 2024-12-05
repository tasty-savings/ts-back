package com.example.testysavingsbe.domain.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record RegisterAllergyRequest(
    @JsonProperty("allergy")
    List<String> allergy
) {

}
