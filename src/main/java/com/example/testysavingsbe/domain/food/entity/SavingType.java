package com.example.testysavingsbe.domain.food.entity;

public enum SavingType {
    ROOM_TEMPERATURE,
    REFRIGERATED,
    FROZEN;

    public static SavingType of(String savingType) {
        return switch (savingType) {
            case "ROOM_TEMPERATURE" -> ROOM_TEMPERATURE;
            case "REFRIGERATED" -> REFRIGERATED;
            case "FROZEN" -> FROZEN;
            default -> throw new IllegalArgumentException("Invalid saving type: " + savingType);
        };
    }
}
