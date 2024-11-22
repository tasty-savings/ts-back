package com.example.testysavingsbe.domain.user.dto.request;

import java.util.List;

public record RegisterAllergyRequest(
        List<String> allergy
) {
}
