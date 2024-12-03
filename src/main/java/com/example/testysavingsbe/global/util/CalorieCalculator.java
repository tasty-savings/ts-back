package com.example.testysavingsbe.global.util;

@FunctionalInterface
public interface CalorieCalculator {
    double calculateCalorie(int age, float weight, float height);
}
