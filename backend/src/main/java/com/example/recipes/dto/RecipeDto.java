package com.example.recipes.dto;

import com.example.recipes.entity.Comment;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Data
public class RecipeDto {

    private Integer id;

    private String username;

    private String category;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    private Integer prepTime;

    private Integer cookTime;

    private Integer servings;

    @NotEmpty
    private List<String> ingredients;

    @NotEmpty
    private List<String> directions;

    private Integer likes;

    private Set<String> usersLiked;

    private List<Comment> comments;
}
