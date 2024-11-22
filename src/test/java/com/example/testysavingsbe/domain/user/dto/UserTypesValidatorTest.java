package com.example.testysavingsbe.domain.user.dto;

import com.example.testysavingsbe.domain.user.validator.UserTypesValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


class UserTypesValidatorTest {
    UserTypesValidator validator = new UserTypesValidator();

    @ParameterizedTest
    @MethodSource("provideTypesAndExpectedResults")
    @DisplayName("커스텀한 유저 선호 타입 validator 테스트")
    void customUserTypesValidatorTest(List<String> types, boolean expectedResult) {
        // given
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);

        // when
        boolean output = validator.isValid(types, context);
        // then
        assertEquals(expectedResult, output);
    }

    static Stream<Arguments> provideTypesAndExpectedResults() {
        return Stream.of(
                Arguments.of(List.of("건강식", "삶기", "죽"), true),
                Arguments.of(List.of("InvalidType1", "InvalidType2"), false),
                Arguments.of(List.of("건강식", "UnknownType"), false)
                // 추가 데이터
        );
    }

}