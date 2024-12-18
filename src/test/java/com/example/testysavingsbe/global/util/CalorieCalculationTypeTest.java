package com.example.testysavingsbe.global.util;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.within;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


class CalorieCalculationTypeTest {
    @ParameterizedTest
    @CsvSource({
        "MALE, 30, 70, 1.75, 2434.1",
        "FEMALE, 30, 70, 1.75, 2072.4"
    })
    void testWithParameterized(String gender, int age, float weight, float height, double expected) {
        double actual = CalorieCalculationType.valueOf(gender).calculate(age, weight, height);
        assertThat(CalorieCalculationType.valueOf(gender).toString()).isEqualTo(gender);
        assertThat(actual).isCloseTo(expected, within(0.01));
    }

}