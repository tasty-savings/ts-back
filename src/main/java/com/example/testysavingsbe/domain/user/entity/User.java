package com.example.testysavingsbe.domain.user.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private String username;
    private Long socialId;

    @Builder
    public User(String username, Long socialId) {
        this.username = username;
        this.socialId = socialId;
    }
}
