package com.example.testysavingsbe.domain.user.entity;

public enum CookingLevel {
    BEGINNER("초급"),
    INTERMEDIATE("중급"),
    ADVANCED("고급");

    private final String displayName;

    CookingLevel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static CookingLevel fromDisplayName(String displayName) {
        for (CookingLevel level : CookingLevel.values()) {
            if (level.displayName.equals(displayName)) {
                return level;
            }
        }
        throw new IllegalArgumentException("Invalid cooking level: " + displayName);
    }
}
