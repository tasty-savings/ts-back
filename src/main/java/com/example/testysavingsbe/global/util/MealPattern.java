package com.example.testysavingsbe.global.util;


import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

public class MealPattern {

    private Map<Integer, Pattern> patterns;

    // Constructor
    public MealPattern() {
        this.patterns = new HashMap<>();
    }

    // Getter
    public Pattern searchByKcal(int kcal) {
        return patterns.get(kcal);
    }

    // 데이터 추가 메서드
    public void addPattern(int kcal, Pattern pattern) {
        this.patterns.put(kcal, pattern);
    }

    // Pattern 클래스 정의
    @Getter
    public static class Pattern {
        private int kcal;             // 에너지(kcal)
        private float grains;         // 곡류
        private float proteinSources; // 고기·생선·달걀·콩류
        private int vegetables;       // 채소류
        private int fruits;           // 과일류
        private int dairy;            // 우유·유제품
        private int fatsAndSugars;    // 유지·당류

        // Constructor
        public Pattern(int kcal, float grains, float proteinSources, int vegetables,
            int fruits, int dairy, int fatsAndSugars) {
            this.kcal = kcal;
            this.grains = grains;
            this.proteinSources = proteinSources;
            this.vegetables = vegetables;
            this.fruits = fruits;
            this.dairy = dairy;
            this.fatsAndSugars = fatsAndSugars;
        }

    }
}