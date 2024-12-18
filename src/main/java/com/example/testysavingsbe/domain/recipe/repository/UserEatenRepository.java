package com.example.testysavingsbe.domain.recipe.repository;

import com.example.testysavingsbe.domain.recipe.entity.UserEaten;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEatenRepository extends MongoRepository<UserEaten, Long> {

    Optional<UserEaten> findByUserId(Long userId);
}
