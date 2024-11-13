package com.example.testysavingsbe.domain.user.service;

import com.example.testysavingsbe.domain.user.dto.response.UserPreferTypeResponse;
import com.example.testysavingsbe.domain.user.entity.User;

import java.util.List;

public interface UserInfoSettingUseCase {
    UserPreferTypeResponse registerPreferType(SettingPreferTypeRequest request);

    record SettingPreferTypeRequest(
            User user,
            List<String> preferredTypes
    ){

    }
}
