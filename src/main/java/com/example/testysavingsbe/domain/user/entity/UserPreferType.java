package com.example.testysavingsbe.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
public class UserPreferType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_name")
    @Enumerated(EnumType.STRING)
    private PreferType type;

    @ManyToOne
    private User user;

    @Builder
    public UserPreferType(PreferType type, User user) {
        this.type = type;
        this.user = user;
    }
}
