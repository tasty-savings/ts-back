package com.example.testysavingsbe.domain.ingredient.entity;

public enum FoodType {
    SOURCE("소스"),
    SEAFOOD("해산물"),
    GRAIN_AND_STARCH("곡류 및 전분류"),
    SEASONING_AND_SPICE("조미료 및 향신료"),
    PROCESSED_FOOD("가공식품"),
    ETC("기타"),
    VEGETABLE("채소류"),
    BEVERAGE("음료류"),
    FRUIT("과일류"),
    DAIRY("유제품"),
    MEAT("육류"),
    FAT_AND_OIL("지방 및 오일류"),
    EGG("달걀류"),
    LEGUME_AND_NUT("콩류 및 견과류");

    private final String description;

    FoodType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
