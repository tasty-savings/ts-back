package com.example.testysavingsbe.domain.recipe.repository;

import com.example.testysavingsbe.domain.recipe.entity.BookmarkedRecipe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkedRepository extends MongoRepository<BookmarkedRecipe, Long> {
    Optional<BookmarkedRecipe> findByUserIdAndRecipeId(Long userId, String recipeId);

    List<BookmarkedRecipe> findAllByUserId(Long userId, Pageable pageable);
}
