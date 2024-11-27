package com.example.testysavingsbe.domain.user.service;

import com.example.testysavingsbe.domain.user.dto.response.UserInfoResponse;
import com.example.testysavingsbe.domain.user.dto.response.UserPreferTypeResponse;
import com.example.testysavingsbe.domain.user.entity.*;
import com.example.testysavingsbe.domain.user.repository.AllergyRepository;
import com.example.testysavingsbe.domain.user.repository.UserPreferTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserInfoSettingUseCase, UserinfoQueryUseCase {
    private final UserPreferTypeRepository userPreferTypeRepository;
    private final AllergyRepository allergyRepository;

    @Override
    @Transactional
    public UserPreferTypeResponse registerPreferType(SettingPreferTypeRequest request) {
        User user = request.user();

        List<UserPreferType> userPreferTypeList = request.preferredTypes().stream()
                .map(type -> new UserPreferType(PreferType.fromKoreanName(type), user))
                .toList();
        userPreferTypeRepository.saveAll(userPreferTypeList);

        return new UserPreferTypeResponse(request.preferredTypes());
    }


    @Override
    @Transactional
    public List<String> registerAllergy(User user, List<String> allergy) {
        allergy.forEach(name ->{
            Allergy allergy1 = new Allergy(name);
            allergyRepository.save(allergy1);
            user.registerAllergy(allergy1);
            }
        );

        return allergy;
    }


    @Override
    @Transactional
    public String updateSpicyLevel(User user, SpicyLevel level) {
        user.updateSpicyLevel(level);
        return level.getDisplayName();
    }

    @Override
    @Transactional
    public String updateCookingLevel(User user, CookingLevel level) {
        user.updateCookingLevel(level);
        return level.getDisplayName();
    }

    @Override
    @Transactional(readOnly = true)
    public UserInfoResponse getUserInfo(User user) {
        return UserInfoResponse.builder()
            .username(user.getUsername())
            .cookingLevel(user.getCookingLevel().getDisplayName())
            .spicyLevel(user.getSpicyLevel().getDisplayName())
            .allergy(user.getAllergy().stream().map(Allergy::getAllergy).toList())
            .build();

    }
}
