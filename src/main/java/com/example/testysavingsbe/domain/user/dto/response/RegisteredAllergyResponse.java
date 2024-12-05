package com.example.testysavingsbe.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record RegisteredAllergyResponse(
    @JsonProperty("registered_allergy")
    List<String> registeredAllergy
) {

}
