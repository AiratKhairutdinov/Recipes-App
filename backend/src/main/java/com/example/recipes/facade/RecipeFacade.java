package com.example.recipes.facade;

import com.example.recipes.dto.RecipeDto;
import com.example.recipes.entity.Recipe;
import org.springframework.stereotype.Component;

@Component
public class RecipeFacade {

    public RecipeDto recipeToRecipeDto(Recipe recipe) {
        RecipeDto recipeDto = new RecipeDto();

        recipeDto.setId(recipe.getId());
        recipeDto.setName(recipe.getName());
        recipeDto.setCategory(recipe.getCategory().getCategoryName());
        recipeDto.setUsername(recipe.getUser().getUsername());
        recipeDto.setDescription(recipe.getDescription());
        recipeDto.setPrepTime(recipe.getPrepTime());
        recipeDto.setCookTime(recipe.getCookTime());
        recipeDto.setServings(recipe.getServings());
        recipeDto.setIngredients(recipe.getIngredients());
        recipeDto.setDirections(recipe.getDirections());
        recipeDto.setLikes(recipe.getLikes());
        recipeDto.setUsersLiked(recipe.getUsersLiked());
        recipeDto.setComments(recipe.getComments());

        return recipeDto;
    }


}
