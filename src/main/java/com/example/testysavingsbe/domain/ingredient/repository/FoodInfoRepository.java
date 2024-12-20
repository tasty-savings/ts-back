package com.example.testysavingsbe.domain.ingredient.repository;

import com.example.testysavingsbe.domain.ingredient.entity.FoodInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface FoodInfoRepository extends JpaRepository<FoodInfo, Long> {
    Optional<FoodInfo> findByName(String name);
    List<FoodInfo> findByNameContaining(String name);
}
