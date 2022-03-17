package com.example.recipes.controller;

import com.example.recipes.dto.RecipeDto;
import com.example.recipes.entity.Recipe;
import com.example.recipes.facade.RecipeFacade;
import com.example.recipes.payload.response.MessageResponse;
import com.example.recipes.service.RecipeService;
import com.example.recipes.validation.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recipes/")
@CrossOrigin("http://localhost:4200")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeFacade recipeFacade;

    @Autowired
    private ResponseErrorValidation errorValidation;

    @PostMapping()
    public ResponseEntity<Object> createRecipe(@Valid @RequestBody RecipeDto recipeDto,
                                               BindingResult bindingResult,
                                               Principal principal) {
        ResponseEntity<Object> errors = errorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        } else {
            Recipe recipe = recipeService.createRecipe(recipeDto, principal);
            RecipeDto createdRecipeDto = recipeFacade.recipeToRecipeDto(recipe);

            return ResponseEntity.ok(createdRecipeDto);
        }
    }

    @PostMapping("like/{recipeId}/{username}")
    public ResponseEntity<RecipeDto> likeRecipe
            (@PathVariable("recipeId") String recipeId,
             @PathVariable("username") String username) {
        Recipe recipe = recipeService.likeRecipe(Integer.valueOf(recipeId), username);

        RecipeDto recipeDto = recipeFacade.recipeToRecipeDto(recipe);

        return ResponseEntity.ok(recipeDto);
    }

    @GetMapping()
    public ResponseEntity<List<RecipeDto>> getAllRecipes() {
        List<RecipeDto> recipes = recipeService.getAllRecipes()
                .stream()
                .map(recipeFacade::recipeToRecipeDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(recipes);
    }

    @GetMapping("user/recipes")
    public ResponseEntity<List<RecipeDto>> getAllRecipesForUser(Principal principal) {
        List<RecipeDto> recipes = recipeService.getAllRecipesForUser(principal)
                .stream()
                .map(recipeFacade::recipeToRecipeDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(recipes);
    }

    @DeleteMapping("{recipeId}")
    public ResponseEntity<MessageResponse> deleteRecipe
            (@PathVariable("recipeId") String recipeId,
             Principal principal) {

        recipeService.deleteRecipe(Integer.valueOf(recipeId), principal);

        return ResponseEntity.ok(new MessageResponse("Recipe was deleted"));
    }

    @GetMapping("{recipeId}")
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable("recipeId") String recipeId) {
        Recipe recipe = recipeService.getRecipe(Integer.valueOf(recipeId));
        RecipeDto recipeDto = recipeFacade.recipeToRecipeDto(recipe);

        return ResponseEntity.ok(recipeDto);
    }
}
