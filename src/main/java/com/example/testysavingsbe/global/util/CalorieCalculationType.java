package com.example.testysavingsbe.global.util;


public enum CalorieCalculationType {
    MALE((age, weight, height) -> 662 - 9.53 * age + (15.91 * weight + 539.6 * height)),
    FEMALE((age, weight, height) -> 354 - 6.91 * age + (9.36 * weight + 726 * height));

    private final CalorieCalculator calculator;

    CalorieCalculationType(CalorieCalculator calculator) {
        this.calculator = calculator;
    }

    public double calculate(int age, float weight, float height) {
        return calculator.calculateCalorie(age, weight, height);
    }
}
