package com.example.testysavingsbe.domain.user.entity;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
public class PhysicalAttributes {
    float height;
    float weight;
    int activityLevel;

    @Builder
    public PhysicalAttributes(float height, float weight, int activityLevel) {
        this.height = height;
        this.weight = weight;
        this.activityLevel = activityLevel;
    }
}
