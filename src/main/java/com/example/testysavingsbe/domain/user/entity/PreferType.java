package com.example.testysavingsbe.domain.user.entity;

import java.util.Arrays;

public enum PreferType {
    HEALTHY_FOOD("건강식"),
    HIGH_PROTEIN("고단백"),
    KOREAN_FOOD("한식"),
    MAIN_DISH("주식"),
    VEGETARIAN("채식"),
    LOW_CALORIE("저칼로리"),
    SIDE_DISH("반찬"),
    SEAFOOD("해산물"),
    CONVENIENT_FOOD("간편식"),
    GRILLED("구이"),
    TRADITIONAL("전통식"),
    HIGH_FIBER("고섬유"),
    SWEET_AND_SOUR("새콤달콤한맛"),
    STIR_FRIED("볶음"),
    RAW_FOOD("생식"),
    LOW_SUGAR("저당식"),
    SEASONED("무침"),
    WESTERN_FOOD("양식"),
    SPICY_LEVEL_1("매운맛1단계"),
    SPICY_LEVEL_2("매운맛2단계"),
    SPICY_LEVEL_3("매운맛3단계"),
    SPICY_LEVEL_4("매운맛4단계"),
    SPICY_LEVEL_5("매운맛5단계"),
    LOW_SODIUM("저염식"),
    FUSION("퓨전"),
    LIGHT_FLAVOR("담백한맛"),
    SOUP("국"),
    STEAMED("찜"),
    DESSERT("디저트"),
    SALAD("샐러드"),
    LOW_CARB("저탄수화물"),
    LIGHT("가벼운"),
    FRIED("튀김"),
    VITAMIN_RICH("비타민"),
    BRAISED("조림"),
    DIGESTIVE("소화촉진"),
    BEVERAGE("음료"),
    GUT_HEALTH("장건강"),
    WEIGHT_CONTROL("체중조절"),
    CHINESE_FOOD("중식"),
    PAN_FRIED("부침"),
    MINERAL_RICH("무기질"),
    BOILED("삶기"),
    SPECIAL_MEAL("특별식"),
    PORRIDGE("죽"),
    GLUTEN_FREE("글루텐프리"),
    JEON("전"),
    JAPANESE_FOOD("일식"),
    SOUP_DISH("스프");

    private final String koreanName;

    PreferType(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    // 한글 이름으로 PreferType 변환 메서드
    public static PreferType fromKoreanName(String koreanName) {
        return Arrays.stream(PreferType.values())
                .filter(type -> type.getKoreanName().equals(koreanName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 한글 이름입니다: " + koreanName));
    }
}
