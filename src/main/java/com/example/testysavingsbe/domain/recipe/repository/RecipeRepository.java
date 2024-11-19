package com.example.testysavingsbe.domain.recipe.repository;

import com.example.testysavingsbe.domain.recipe.entity.CustomRecipe;
import com.example.testysavingsbe.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<CustomRecipe, Long> {
    @Query("select r from CustomRecipe r where r.user = :user and  r.isEaten = true ")
    List<CustomRecipe> findAllEatenRecipeByUser(@Param("user")User user);

    @Query("select r from CustomRecipe r where r.user = :user and r.isBookMarked = true ")
    List<CustomRecipe> findAllBookMarkedRecipeByUser(@Param("user") User user);

    Page<CustomRecipe> findAllByUser(User user, Pageable pageable);
}
