package com.example.testysavingsbe.domain.user.service;

import com.example.testysavingsbe.domain.user.dto.response.UserPreferTypeResponse;
import com.example.testysavingsbe.domain.user.entity.CookingLevel;
import com.example.testysavingsbe.domain.user.entity.SpicyLevel;
import com.example.testysavingsbe.domain.user.entity.User;

import java.util.List;

public interface UserInfoSettingUseCase {
    UserPreferTypeResponse registerPreferType(SettingPreferTypeRequest request);
    List<String> registerAllergy(User user, List<String> allergy);
    String updateSpicyLevel(User user, SpicyLevel level);
    String updateCookingLevel(User user, CookingLevel level);

    record SettingPreferTypeRequest(
            User user,
            List<String> preferredTypes
    ){

    }
}
