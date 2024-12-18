package com.example.testysavingsbe.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CheckSetUserInfoResponse(
    @JsonProperty("is_checked")
    Boolean isChecked
) {

}
