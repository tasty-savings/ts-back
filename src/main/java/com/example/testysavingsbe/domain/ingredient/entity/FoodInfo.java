package com.example.testysavingsbe.domain.ingredient.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class FoodInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 식품이름
    @Column(name = "name")
    private String name;

    @Column(name = "food_type")
    @Enumerated(EnumType.STRING)
    private FoodType foodType;


    @Builder
    public FoodInfo(String name, FoodType foodType) {
        this.name = name;
        this.foodType = foodType;
    }
}
