package com.example.testysavingsbe.domain.user.entity;

public enum ActivityLevel {
    INACTIVE("비활동적"),
    LOW_ACTIVE("저활동적"),
    ACTIVE("활동적"),
    VERY_ACTIVE("매우 활동적");

    private final String description;

    ActivityLevel(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }


}
