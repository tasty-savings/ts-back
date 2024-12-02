package com.example.testysavingsbe.domain.user.entity;

public enum AgeGroup {
    AGE_1_9("1~9", 1, 10),
    AGE_10_14("10~14", 10, 15),
    AGE_15_19("15~19", 15, 20),
    AGE_20_29("20~29", 20, 30),
    AGE_30_39("30~39", 30, 40),
    AGE_40_49("40~49", 40, 50),
    AGE_50_59("50~59", 50, 60),
    AGE_60_69("60~69", 60, 70),
    AGE_70_79("70~79", 70, 80),
    AGE_80_89("80~89", 80, 90),
    AGE_90_PLUS("90+", 90, Integer.MAX_VALUE);

    private final String label; // 연령대 레이블
    private final int minAge;   // 최소 나이
    private final int maxAge;   // 최대 나이 (exclusive)

    AgeGroup(String label, int minAge, int maxAge) {
        this.label = label;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public static AgeGroup fromAgeGroup(String label) {
        for (AgeGroup ageGroup : AgeGroup.values()) {
            if (ageGroup.label.equals(label)) {
                return ageGroup;
            }
        }
        return null;
    }
}
