package com.example.testysavingsbe.domain.user.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.testysavingsbe.domain.user.dto.response.UserPreferTypeResponse;
import com.example.testysavingsbe.domain.user.entity.PreferType;
import com.example.testysavingsbe.domain.user.entity.User;
import com.example.testysavingsbe.domain.user.entity.UserPreferType;
import com.example.testysavingsbe.domain.user.repository.UserPreferTypeRepository;
import com.example.testysavingsbe.domain.user.service.UserInfoSettingUseCase.SettingPreferTypeRequest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserPreferTypeRepository preferTypeRepository;
    @InjectMocks
    private UserService userService;

    @DisplayName("중복된 선호 타입은 저장되면 안된다.")
    @Test
    void registerPreferTypeCanNotDuplicate(){
        // given
        User user = mock(User.class);
        SettingPreferTypeRequest request = new SettingPreferTypeRequest(
            user,
            List.of("건강식", "고단백", "한식")
        );

        when(preferTypeRepository.findAllByUser(any())).thenReturn(List.of(
            new UserPreferType(PreferType.HEALTHY_FOOD, user)
        ));

        // when
        UserPreferTypeResponse response = userService.registerPreferType(request);

        // then
        then(response).isNotNull();
        then(response.preferTypes()).isEqualTo(List.of("고단백", "한식"));
    }

}