package com.example.testysavingsbe.domain.user.service;

import com.example.testysavingsbe.domain.user.dto.response.CheckSetPreferFoodResponse;
import com.example.testysavingsbe.domain.user.dto.response.UserInfoResponse;
import com.example.testysavingsbe.domain.user.entity.User;

public interface UserinfoQueryUseCase {
    UserInfoResponse getUserInfo(User user);

    CheckSetPreferFoodResponse checkSetUserPrefer(User user);
}
