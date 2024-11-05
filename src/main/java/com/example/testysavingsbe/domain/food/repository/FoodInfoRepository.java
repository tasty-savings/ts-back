package com.example.testysavingsbe.domain.food.repository;

import com.example.testysavingsbe.domain.food.entity.FoodInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface FoodInfoRepository extends JpaRepository<FoodInfo, Long> {
    Optional<FoodInfo> findByName(String name);
}
