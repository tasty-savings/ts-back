package com.example.testysavingsbe.domain.food.repository;

import com.example.testysavingsbe.domain.food.entity.Food;
import com.example.testysavingsbe.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findAllByUser(User user);
    Optional<Food> findByIdAndUser(Long id, User user);
}
