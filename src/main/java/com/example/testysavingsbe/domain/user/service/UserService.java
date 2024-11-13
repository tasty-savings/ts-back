package com.example.testysavingsbe.domain.user.service;

import com.example.testysavingsbe.domain.user.dto.response.UserPreferTypeResponse;
import com.example.testysavingsbe.domain.user.entity.User;
import com.example.testysavingsbe.domain.user.entity.UserPreferType;
import com.example.testysavingsbe.domain.user.repository.UserPreferTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserInfoSettingUseCase {
    private final UserPreferTypeRepository userPreferTypeRepository;

    @Override
    public UserPreferTypeResponse registerPreferType(SettingPreferTypeRequest request) {
        User user = request.user();

        List<UserPreferType> userPreferTypeList = request.preferredTypes().stream()
                .map(type -> new UserPreferType(type, user))
                .toList();
        userPreferTypeRepository.saveAll(userPreferTypeList);

        return new UserPreferTypeResponse(request.preferredTypes());
    }
}
