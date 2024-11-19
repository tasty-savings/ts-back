package com.example.testysavingsbe.domain.user.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


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

    @Builder
    public User(String username, Long socialId) {
        this.username = username;
        this.socialId = socialId;
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
