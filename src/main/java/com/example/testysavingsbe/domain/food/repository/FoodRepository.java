package com.example.testysavingsbe.domain.food.repository;

import com.example.testysavingsbe.domain.food.entity.Food;
import com.example.testysavingsbe.domain.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {
    @EntityGraph(attributePaths = {"foodInfo"})
    List<Food> findAllByUser(User user);
    @EntityGraph(attributePaths = {"foodInfo"})
    Optional<Food> findByIdAndUser(Long id, User user);
}
