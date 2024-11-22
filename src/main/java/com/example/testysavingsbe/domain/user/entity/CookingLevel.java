package com.example.testysavingsbe.domain.user.entity;

public enum CookingLevel {
    BEGINNER("초급"),
    INTERMEDIATE("중급"),
    ADVANCED("고급");

    private final String displayName;

    // 생성자
    CookingLevel(String displayName) {
        this.displayName = displayName;
    }

    // Getter 메서드
    public String getDisplayName() {
        return displayName;
    }

    // 문자열로 Enum을 찾는 메서드
    public static CookingLevel fromDisplayName(String displayName) {
        for (CookingLevel level : CookingLevel.values()) {
            if (level.displayName.equals(displayName)) {
                return level;
            }
        }
        throw new IllegalArgumentException("Invalid cooking level: " + displayName);
    }
}
