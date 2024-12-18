package com.example.testysavingsbe.domain.user.dto.request;

import com.example.testysavingsbe.domain.user.validator.ValidUserType;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record DeleteUserTypeRequest(
    @ValidUserType
    @JsonProperty("type")
    List<String> type
) {

}
