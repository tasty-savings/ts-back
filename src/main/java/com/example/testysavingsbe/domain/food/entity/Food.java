package com.example.testysavingsbe.domain.food.entity;

import com.example.testysavingsbe.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;


@Entity
@Getter
@NoArgsConstructor
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "food_name")
    private String foodName;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDate createDate;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "saving_type")
    private SavingType savingType;

    @ManyToOne(fetch = FetchType.LAZY)
    private FoodInfo foodInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    public FoodInfo getFoodInfo() {
        return foodInfo != null ? foodInfo : null;
    }

    public void updateSavingType(final String savingType) {
        this.savingType = SavingType.valueOf(savingType);
    }

    public void updateExpirationDate(final LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Builder
    public Food(String foodName, LocalDate expirationDate, SavingType savingType, FoodInfo foodInfo, User user) {
        this.foodName = foodName;
        this.expirationDate = expirationDate;
        this.savingType = savingType;
        this.foodInfo = foodInfo;
        this.user = user;
    }
}
