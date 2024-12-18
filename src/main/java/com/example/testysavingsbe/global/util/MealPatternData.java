package com.example.testysavingsbe.global.util;


public class MealPatternData {

    private static final MealPattern TYPE_A = new MealPattern();
    private static final MealPattern TYPE_B = new MealPattern();

    static {
        // Type A
        TYPE_A.addPattern(900, new MealPattern.Pattern(900, 1f, 1.5f, 4, 1, 2, 2));
        TYPE_A.addPattern(1000, new MealPattern.Pattern(1000, 1f, 1.5f, 4, 1, 2, 3));
        TYPE_A.addPattern(1100, new MealPattern.Pattern(1100, 1.5f, 1.5f, 4, 1, 2, 3));
        TYPE_A.addPattern(1200, new MealPattern.Pattern(1200, 1.5f, 2f, 5, 1, 2, 3));
        TYPE_A.addPattern(1300, new MealPattern.Pattern(1300, 1.5f, 2f, 6, 1, 2, 4));
        TYPE_A.addPattern(1400, new MealPattern.Pattern(1400, 2f, 2f, 6, 1, 2, 4));
        TYPE_A.addPattern(1500, new MealPattern.Pattern(1500, 2f, 2.5f, 6, 1, 2, 5));
        TYPE_A.addPattern(1600, new MealPattern.Pattern(1600, 2.5f, 2.5f, 6, 1, 2, 5));
        TYPE_A.addPattern(1700, new MealPattern.Pattern(1700, 2.5f, 3f, 6, 1, 2, 5));
        TYPE_A.addPattern(1800, new MealPattern.Pattern(1800, 3f, 3f, 6, 1, 2, 5));
        TYPE_A.addPattern(1900, new MealPattern.Pattern(1900, 3f, 3.5f, 7, 1, 2, 5));
        TYPE_A.addPattern(2000, new MealPattern.Pattern(2000, 3f, 3.5f, 7, 2, 2, 6));
        TYPE_A.addPattern(2100, new MealPattern.Pattern(2100, 3f, 4f, 8, 2, 2, 6));
        TYPE_A.addPattern(2200, new MealPattern.Pattern(2200, 3.5f, 4f, 8, 2, 2, 6));
        TYPE_A.addPattern(2300, new MealPattern.Pattern(2300, 3.5f, 5f, 8, 2, 2, 6));
        TYPE_A.addPattern(2400, new MealPattern.Pattern(2400, 3.5f, 5f, 8, 3, 2, 6));
        TYPE_A.addPattern(2500, new MealPattern.Pattern(2500, 3.5f, 5.5f, 8, 3, 2, 7));
        TYPE_A.addPattern(2600, new MealPattern.Pattern(2600, 3.5f, 5.5f, 8, 4, 2, 8));
        TYPE_A.addPattern(2700, new MealPattern.Pattern(2700, 4f, 5.5f, 8, 4, 2, 8));
        TYPE_A.addPattern(2800, new MealPattern.Pattern(2800, 4f, 6f, 8, 4, 2, 8));

        // Type B
        TYPE_B.addPattern(1000, new MealPattern.Pattern(1000, 1.5f, 1.5f, 5, 1, 1, 2));
        TYPE_B.addPattern(1100, new MealPattern.Pattern(1100, 1.5f, 2f, 5, 1, 1, 3));
        TYPE_B.addPattern(1200, new MealPattern.Pattern(1200, 2f, 2f, 5, 1, 1, 3));
        TYPE_B.addPattern(1300, new MealPattern.Pattern(1300, 2f, 2f, 6, 1, 1, 4));
        TYPE_B.addPattern(1400, new MealPattern.Pattern(1400, 2.5f, 2f, 6, 1, 1, 4));
        TYPE_B.addPattern(1500, new MealPattern.Pattern(1500, 2.5f, 2.5f, 6, 1, 1, 4));
        TYPE_B.addPattern(1600, new MealPattern.Pattern(1600, 3f, 2.5f, 6, 1, 1, 4));
        TYPE_B.addPattern(1700, new MealPattern.Pattern(1700, 3f, 3.5f, 6, 1, 1, 4));
        TYPE_B.addPattern(1800, new MealPattern.Pattern(1800, 3f, 3.5f, 7, 2, 1, 4));
        TYPE_B.addPattern(1900, new MealPattern.Pattern(1900, 3f, 4f, 8, 2, 1, 4));
        TYPE_B.addPattern(2000, new MealPattern.Pattern(2000, 3.5f, 4f, 8, 2, 1, 4));
        TYPE_B.addPattern(2100, new MealPattern.Pattern(2100, 3.5f, 4.5f, 8, 2, 1, 5));
        TYPE_B.addPattern(2200, new MealPattern.Pattern(2200, 3.5f, 5f, 8, 2, 1, 6));
        TYPE_B.addPattern(2300, new MealPattern.Pattern(2300, 4f, 5f, 8, 2, 1, 6));
        TYPE_B.addPattern(2400, new MealPattern.Pattern(2400, 4f, 5f, 8, 3, 1, 6));
        TYPE_B.addPattern(2500, new MealPattern.Pattern(2500, 4f, 5f, 8, 4, 1, 7));
        TYPE_B.addPattern(2600, new MealPattern.Pattern(2600, 4f, 6f, 9, 4, 1, 7));
        TYPE_B.addPattern(2700, new MealPattern.Pattern(2700, 4f, 6.5f, 9, 4, 1, 8));
    }

    public static MealPattern getTypeA() {
        return TYPE_A;
    }

    public static MealPattern getTypeB() {
        return TYPE_B;
    }
}
