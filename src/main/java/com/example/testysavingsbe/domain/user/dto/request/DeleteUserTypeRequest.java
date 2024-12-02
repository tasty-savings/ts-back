package com.example.testysavingsbe.domain.user.dto.request;

import com.example.testysavingsbe.domain.user.validator.ValidUserType;
import java.util.List;

public record DeleteUserTypeRequest(
    @ValidUserType List<String> type
) {

}