package com.example.testysavingsbe.domain.user.dto;

import com.example.testysavingsbe.domain.user.dto.validator.UserTypesValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;


class UserTypesValidatorTest {
    UserTypesValidator validator = new UserTypesValidator();

    @Test
    @DisplayName("커스텀한 유저 선호 타입 validator 성공한다.")
    void customUserTypesValidatorWillSuccess(){
        // given
        List<String> types = List.of("건강식", "삶기", "죽");
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        // when
        boolean output =  validator.isValid(types, context);

        // then
        assertTrue(output);
    }

    @Test
    @DisplayName("커스텀한 유저 선호 타입 validator 실패한다.")
    void customUserTypesValidatorFail(){
        // given
        List<String> types = List.of("건강식", "돈", "");
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        // when
        boolean output =  validator.isValid(types, context);

        // then
        assertFalse(output);
    }


}