package com.example.testysavingsbe.domain.user.service;

import com.example.testysavingsbe.domain.user.dto.request.DeleteUserTypeRequest;
import com.example.testysavingsbe.domain.user.dto.request.PhysicalInfoRegisterRequest;
import com.example.testysavingsbe.domain.user.dto.response.CheckSetUserInfoResponse;
import com.example.testysavingsbe.domain.user.dto.response.UserInfoResponse;
import com.example.testysavingsbe.domain.user.dto.response.UserPreferTypeResponse;
import com.example.testysavingsbe.domain.user.entity.*;
import com.example.testysavingsbe.domain.user.repository.AllergyRepository;
import com.example.testysavingsbe.domain.user.repository.UserPreferTypeRepository;
import com.example.testysavingsbe.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserInfoSettingUseCase, UserinfoQueryUseCase {

    private final UserPreferTypeRepository userPreferTypeRepository;
    private final AllergyRepository allergyRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public UserPreferTypeResponse registerPreferType(SettingPreferTypeRequest request) {
        User user = request.user();
        List<UserPreferType> userPreferTypeList = userPreferTypeRepository.findAllByUser(user);
        List<String> userPreferTypeStringList = userPreferTypeList.stream()
            .map(UserPreferType::getDisplayName).toList();

        List<UserPreferType> registerUserPreferType = request.preferredTypes().stream()
            .filter(preferType -> !userPreferTypeStringList.contains(preferType))
            .map(type -> new UserPreferType(PreferType.fromKoreanName(type), user))
            .toList();

        userPreferTypeRepository.saveAll(registerUserPreferType);

        if (!user.getSetPreferType()) {
            user.doneUserPreferType();
            userRepository.save(user);
        }

        List<String> response = registerUserPreferType.stream()
            .map(UserPreferType::getDisplayName)
            .toList();
        return new UserPreferTypeResponse(response);
    }


    @Override
    @Transactional
    public List<String> registerAllergy(User user, List<String> allergy) {
        allergy.forEach(name -> {
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
    @Transactional
    public void updateUserPhysicalAttribute(User user, PhysicalInfoRegisterRequest request) {
        user.updatePhysicalAttributes(request.age(), request.height(), request.weight(),
            ActivityLevel.from(request.activityLevel()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deletePreferType(User user, DeleteUserTypeRequest request) {
        List<PreferType> preferTypes = request.type().stream()
            .map(PreferType::fromKoreanName)
            .toList();
        userPreferTypeRepository.deleteAllByUserAndType(user, preferTypes);
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

    @Override
    public CheckSetUserInfoResponse checkSetUserPrefer(User user) {
        return new CheckSetUserInfoResponse(user.getSetPreferType());
    }

    @Override
    public CheckSetUserInfoResponse checkSetUserHealthInfo(User user) {
        if (user.getPhysicalAttributes().getWeight() == null
            || user.getPhysicalAttributes().getHeight() == null
            || user.getPhysicalAttributes().getActivityLevel() == null
            || user.getAge() == null
        ) {
            return new CheckSetUserInfoResponse(false);
        }
        return new CheckSetUserInfoResponse(true);
    }

}
