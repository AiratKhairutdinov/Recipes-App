package com.example.recipes.service;

import com.example.recipes.entity.Recipe;
import com.example.recipes.entity.RecipeCategory;
import com.example.recipes.repository.RecipeCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RecipeCategoryService {

    private final RecipeCategoryRepository recipeCategoryRepository;

    @Autowired
    public RecipeCategoryService(RecipeCategoryRepository recipeCategoryRepository) {
        this.recipeCategoryRepository = recipeCategoryRepository;
    }

    public List<RecipeCategory> getRecipeCategories() {
        return recipeCategoryRepository.findAll();
    }

    public Set<Recipe> getRecipesByCategory(Integer categoryId) {
        return recipeCategoryRepository.findById(categoryId).get().getRecipes();
    }

}
