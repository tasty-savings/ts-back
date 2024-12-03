package com.example.testysavingsbe.domain.user.entity;


import com.example.testysavingsbe.global.config.PrincipalDetails;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "social_id")
    private Long socialId;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "age")
    private Integer age;

    @Enumerated(EnumType.STRING)
    private CookingLevel cookingLevel;

    @Embedded
    private PhysicalAttributes physicalAttributes = new PhysicalAttributes();

    @Enumerated(EnumType.STRING)
    private SpicyLevel spicyLevel = SpicyLevel.LEVEL_2;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Allergy> allergy;

    @Builder
    public User(String username, Long socialId, CookingLevel cookingLevel, Gender gender) {
        this.username = username;
        this.socialId = socialId;
        this.cookingLevel = cookingLevel;
        this.gender = gender;
    }

    public void updatePhysicalAttributes(int age, float height, float weight, int activityLevel) {
        this.age = age;
        this.physicalAttributes = PhysicalAttributes.builder()
            .height(height)
            .weight(weight)
            .activityLevel(activityLevel)
            .build();
    }

    public Set<Allergy> getAllergy() {
        if (allergy == null) {
            allergy = new HashSet<>();
        }
        return allergy;
    }

    public void registerAllergy(Allergy allergy) {
        if (this.allergy == null) {
            this.allergy = new HashSet<>();
        }
        this.allergy.add(allergy);
    }

    public void updateSpicyLevel(SpicyLevel spicyLevel) {
        this.spicyLevel = spicyLevel;
    }

    public void updateCookingLevel(CookingLevel cookingLevel) {
        this.cookingLevel = cookingLevel;
    }
}
