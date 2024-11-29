package com.example.testysavingsbe.domain.recipe.repository;

import com.example.testysavingsbe.domain.recipe.entity.SharedRecipe;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SharedRecipeRepository extends MongoRepository<SharedRecipe, String> {
    Optional<SharedRecipe> findByUuid(String uuid);
    void deleteByCustomRecipeId(String customRecipeId);
    Optional<SharedRecipe> findByCustomRecipeId(String customRecipeId);
}
