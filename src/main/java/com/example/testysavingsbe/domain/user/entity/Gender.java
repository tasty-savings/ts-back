package com.example.testysavingsbe.domain.user.entity;

public enum Gender {
    MALE,
    FEMALE;

    public static Gender of(String gender){
        return switch (gender) {
            case "male" -> Gender.MALE;
            case "female" -> Gender.FEMALE;
            default -> null;
        };
    }
}
