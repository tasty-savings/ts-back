package com.example.testysavingsbe.domain.recipe.repository;

import com.example.testysavingsbe.domain.recipe.entity.UserEaten;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEatenRepository extends MongoRepository<UserEaten, Long> {
}
