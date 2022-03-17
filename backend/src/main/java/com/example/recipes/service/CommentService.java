package com.example.recipes.service;

import com.example.recipes.dto.CommentDto;
import com.example.recipes.entity.Comment;
import com.example.recipes.entity.Recipe;
import com.example.recipes.entity.User;
import com.example.recipes.exception.RecipeNotFoundException;
import com.example.recipes.repository.CommentRepository;
import com.example.recipes.repository.RecipeRepository;
import com.example.recipes.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;

    private final RecipeRepository recipeRepository;

    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository,
                          RecipeRepository recipeRepository,
                          UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }


    public List<Comment> getAllCommentsForRecipe(Integer recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe cannot be found"));
        return commentRepository.findAllByRecipe(recipe);
    }

    public Comment saveComment(Integer recipeId, CommentDto commentDto, Principal principal) {
        User user = getUserByPrincipal(principal);
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new UsernameNotFoundException("Recipe cannot be found for username "
                        + user.getUsername()));
        Comment comment = new Comment();
        comment.setUserId(user.getId());
        comment.setRecipe(recipe);
        comment.setUsername(user.getUsername());
        comment.setMessage(commentDto.getMessage());

        log.info("Saving comment for Recipe: {}", recipe.getName());

        return commentRepository.save(comment);
    }

    public void deleteComment(Integer commentId) {
        commentRepository.findById(commentId)
                .ifPresent(commentRepository::delete);
    }


    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Username not found: " + username));
    }
}
