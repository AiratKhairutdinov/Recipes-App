package com.example.recipes.controller;

import com.example.recipes.dto.RecipeDto;
import com.example.recipes.entity.RecipeCategory;
import com.example.recipes.facade.RecipeFacade;
import com.example.recipes.service.RecipeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recipeCategory/")
@CrossOrigin("http://localhost:4200")
public class RecipeCategoryController {

    @Autowired
    private RecipeCategoryService recipeCategoryService;

    @Autowired
    private RecipeFacade recipeFacade;


    @GetMapping()
    public ResponseEntity<List<RecipeCategory>> getRecipeCategories() {
        List<RecipeCategory> recipeCategories = recipeCategoryService.getRecipeCategories();
        return ResponseEntity.ok(recipeCategories);
    }

    @GetMapping("{categoryId}/recipes")
    public ResponseEntity<List<RecipeDto>> getRecipesByCategory(@PathVariable("categoryId") String categoryId) {
        List<RecipeDto> recipes = recipeCategoryService.getRecipesByCategory(Integer.valueOf(categoryId))
                .stream()
                .map(recipeFacade::recipeToRecipeDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(recipes);
    }
}
