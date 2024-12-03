package com.example.testysavingsbe.domain.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PhysicalInfoRegisterRequest(
    @JsonProperty("age")
    int age,
    @JsonProperty("height")
    float height,
    @JsonProperty("weight")
    float weight,
    @JsonProperty("activity_level")
    int activityLevel
) {

}
