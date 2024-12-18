package com.example.testysavingsbe.domain.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;

public record PhysicalInfoRegisterRequest(
    @JsonProperty("gender")
    @Pattern(regexp = "male|female", message = "male or female required")
    String gender,
    @JsonProperty("age")
    int age,
    @JsonProperty("height")
    float height,
    @JsonProperty("weight")
    float weight,
    @JsonProperty("activity_level")
    @Pattern(regexp = "비활동적|저활동적|활동적|매우 활동적", message = "활동 수준은 '비활동적', '저활동적', '활동적', '매우 활동적' 중 하나여야 합니다.")
    String activityLevel
) {

}
