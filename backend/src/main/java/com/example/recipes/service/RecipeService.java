package com.example.recipes.service;

import com.example.recipes.dto.RecipeDto;
import com.example.recipes.entity.Recipe;
import com.example.recipes.entity.User;
import com.example.recipes.exception.RecipeNotFoundException;
import com.example.recipes.repository.ImageRepository;
import com.example.recipes.repository.RecipeCategoryRepository;
import com.example.recipes.repository.RecipeRepository;
import com.example.recipes.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RecipeService {

    private final RecipeRepository recipeRepository;

    private final UserRepository userRepository;

    private final ImageRepository imageRepository;

    private final RecipeCategoryRepository recipeCategoryRepository;

    public RecipeService(RecipeRepository recipeRepository,
                         UserRepository userRepository,
                         ImageRepository imageRepository,
                         RecipeCategoryRepository recipeCategoryRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.recipeCategoryRepository = recipeCategoryRepository;
    }

    @Autowired

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAllByOrderByCreatedDateDesc();
    }

    public List<Recipe> getAllRecipesForUser(Principal principal) {
        User user = getUserByPrincipal(principal);
        return recipeRepository.findAllByUserOrderByCreatedDateDesc(user);
    }

    public Recipe getRecipeById(Integer recipeId, Principal principal) {
        User user = getUserByPrincipal(principal);
        return recipeRepository.findRecipeByIdAndUser(recipeId, user)
                .orElseThrow(() -> new
                        RecipeNotFoundException("Recipe cannot be found for username "
                        + user.getUsername()));
    }

    public Recipe getRecipe(Integer recipeId) {
        return recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe cannot be found"));
    }

    public void deleteRecipe(Integer recipeId, Principal principal) {
        Recipe recipe = getRecipeById(recipeId, principal);
        recipeRepository.delete(recipe);
        imageRepository.findByRecipeId(recipe.getId())
                .ifPresent(imageRepository::delete);
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Username not found: " + username));
    }

    public Recipe createRecipe(RecipeDto recipeDto, Principal principal) {
        User user = getUserByPrincipal(principal);
        Recipe recipe = new Recipe();
        recipe.setUser(user);
        recipe.setCategory(recipeCategoryRepository.findByCategoryName(recipeDto.getCategory()));
        recipe.setName(recipeDto.getName());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setPrepTime(recipeDto.getPrepTime());
        recipe.setCookTime(recipeDto.getCookTime());
        recipe.setServings(recipeDto.getServings());
        recipe.setIngredients(recipeDto.getIngredients());
        recipe.setDirections(recipeDto.getDirections());
        recipe.setLikes(0);

        log.info("Saving Recipe for User: {}", user.getUsername());

        return recipeRepository.save(recipe);
    }

    public Recipe likeRecipe(Integer recipeId, String username) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe cannot be found"));

        Optional<String> userLiked = recipe.getUsersLiked()
                .stream().filter(u -> u.equals(username)).findAny();

        if (userLiked.isPresent()) {
            recipe.setLikes(recipe.getLikes() - 1);
            recipe.getUsersLiked().remove(username);
        } else {
            recipe.setLikes(recipe.getLikes() + 1);
            recipe.getUsersLiked().add(username);
        }
        return recipeRepository.save(recipe);
    }
}
