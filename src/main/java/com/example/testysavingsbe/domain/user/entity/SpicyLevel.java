package com.example.testysavingsbe.domain.user.entity;

public enum SpicyLevel {
    LEVEL_1("1단계"),
    LEVEL_2("2단계"),
    LEVEL_3("3단계"),
    LEVEL_4("4단계"),
    LEVEL_5("5단계");

    private final String displayName;

    // 생성자
    SpicyLevel(String displayName) {
        this.displayName = displayName;
    }

    // Getter 메서드
    public String getDisplayName() {
        return displayName;
    }

    // 문자열로 Enum을 찾는 메서드
    public static SpicyLevel of(int level) {
        return switch (level) {
            case 1 -> LEVEL_1;
            case 2 -> LEVEL_2;
            case 3 -> LEVEL_3;
            case 4 -> LEVEL_4;
            case 5 -> LEVEL_5;
            default -> throw new IllegalArgumentException("Invalid spicy level: " + level);
        };
    }
}
