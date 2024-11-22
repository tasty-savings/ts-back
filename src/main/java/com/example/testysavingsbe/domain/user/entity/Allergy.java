package com.example.testysavingsbe.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Allergy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "allergy")
    private String allergy;



    @Builder
    public Allergy(String allergy) {
        this.allergy = allergy;
    }
}
