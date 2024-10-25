package com.example.testysavingsbe.domain.recipe.entity;

import com.example.testysavingsbe.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Boolean isEaten;
    private Boolean isBookMarked;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public Recipe(String content, User user) {
        this.content = content;
        this.user = user;
        this.isEaten = false;
        this.isBookMarked = false;
    }

    public void updateEaten(){
        this.isEaten = !this.isEaten;
    }

    public void updateBookMarked(){
        this.isBookMarked = !this.isBookMarked;
    }
}
