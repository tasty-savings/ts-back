package com.example.testysavingsbe.domain.recipe.repository;


import com.example.testysavingsbe.domain.recipe.entity.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecipeRepository extends MongoRepository<Recipe, String> {
    @Override
    Page<Recipe> findAll(Pageable pageable);
}
