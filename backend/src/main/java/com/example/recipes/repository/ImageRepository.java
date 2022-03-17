package com.example.recipes.repository;

import com.example.recipes.entity.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageModel, Integer> {

    Optional<ImageModel> findByUserId(Integer userId);

    Optional<ImageModel> findByRecipeId(Integer recipeId);
}
