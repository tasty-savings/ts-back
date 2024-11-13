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
    private String typeName;

    @ManyToOne
    private User user;

    @Builder
    public UserPreferType(String typeName, User user) {
        this.typeName = typeName;
        this.user = user;
    }
}
