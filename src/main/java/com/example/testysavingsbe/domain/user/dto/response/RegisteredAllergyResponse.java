package com.example.testysavingsbe.domain.user.dto.response;

import java.util.List;

public record RegisteredAllergyResponse(
        List<String> registeredAllergy
) {
}
