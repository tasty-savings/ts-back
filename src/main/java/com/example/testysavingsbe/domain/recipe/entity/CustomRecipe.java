package com.example.testysavingsbe.domain.recipe.entity;

import com.example.testysavingsbe.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "custom_recipe")
public class CustomRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String content;
    @Column
    private Boolean isEaten;
    @Column
    private Boolean isBookMarked;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public CustomRecipe(String content, User user) {
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
