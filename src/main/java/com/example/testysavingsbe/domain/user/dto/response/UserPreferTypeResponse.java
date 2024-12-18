package com.example.testysavingsbe.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record UserPreferTypeResponse(
    @JsonProperty("prefer_type")
    List<String> preferTypes
) {

}
