package com.example.testysavingsbe.domain.user.service;

import com.example.testysavingsbe.domain.user.dto.response.CheckSetUserInfoResponse;
import com.example.testysavingsbe.domain.user.dto.response.UserInfoResponse;
import com.example.testysavingsbe.domain.user.dto.response.UserPreferTypeResponse;
import com.example.testysavingsbe.domain.user.entity.User;

public interface UserinfoQueryUseCase {
    UserInfoResponse getUserInfo(User user);

    CheckSetUserInfoResponse checkSetUserPrefer(User user);

    CheckSetUserInfoResponse checkSetUserHealthInfo(User user);

    UserPreferTypeResponse getUserPreferInfo(User user);
}
