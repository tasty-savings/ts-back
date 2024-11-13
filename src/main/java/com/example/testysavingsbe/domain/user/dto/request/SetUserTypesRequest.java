package com.example.testysavingsbe.domain.user.dto.request;

import com.example.testysavingsbe.domain.user.dto.validator.ValidUserType;

import java.util.List;

public record SetUserTypesRequest(
        @ValidUserType List<String> types
) {

}
