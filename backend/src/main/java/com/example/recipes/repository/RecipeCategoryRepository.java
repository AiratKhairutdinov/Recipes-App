package com.example.recipes.repository;

import com.example.recipes.entity.RecipeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeCategoryRepository extends JpaRepository<RecipeCategory, Integer> {

    RecipeCategory findByCategoryName(String name);

}
