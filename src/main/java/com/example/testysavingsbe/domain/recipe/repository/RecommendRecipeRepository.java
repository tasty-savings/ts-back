package com.example.testysavingsbe.domain.recipe.repository;


import com.example.testysavingsbe.domain.recipe.entity.RecommendedRecipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecommendRecipeRepository extends MongoRepository<RecommendedRecipe, String> {
    @Override
    Page<RecommendedRecipe> findAll(Pageable pageable);
}
