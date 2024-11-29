package com.example.testysavingsbe.domain.recipe.repository;


import com.example.testysavingsbe.domain.recipe.entity.Recipe;
import java.util.List;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecipeRepository extends MongoRepository<Recipe, String> {

    @Aggregation(pipeline = {"{ $sample:  {size:  ?0}}"})
    List<Recipe> findRandomRecipes(int size);
}

