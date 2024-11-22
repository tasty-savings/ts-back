package com.example.testysavingsbe.domain.recipe.repository;

import com.example.testysavingsbe.domain.recipe.entity.CustomRecipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface CustomRecipeRepository extends MongoRepository<CustomRecipe, String> {
    Page<CustomRecipe> findMongoRecipesByUserId(Long userId, Pageable pageable);
    Optional<CustomRecipe> findByIdAndUserId(String recipeId, Long userId);
}
