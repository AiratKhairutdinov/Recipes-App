package com.example.recipes.repository;

import com.example.recipes.entity.Recipe;
import com.example.recipes.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

    List<Recipe> findAllByOrderByCreatedDateDesc();

    List<Recipe> findAllByUserOrderByCreatedDateDesc(User user);

    Optional<Recipe> findRecipeByIdAndUser(Integer recipeId, User user);

}
