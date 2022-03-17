package com.example.recipes.controller;

import com.example.recipes.dto.CommentDto;
import com.example.recipes.entity.Comment;
import com.example.recipes.facade.CommentFacade;
import com.example.recipes.payload.response.MessageResponse;
import com.example.recipes.service.CommentService;
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
@RequestMapping("/api/comments/")
@CrossOrigin("http://localhost:4200")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentFacade commentFacade;

    @Autowired
    private ResponseErrorValidation errorValidation;


    @PostMapping("{recipeId}")
    public ResponseEntity<Object> createComment
            (@Valid @RequestBody CommentDto commentDto,
             @PathVariable("recipeId") String recipeId,
             BindingResult bindingResult,
             Principal principal) {

        ResponseEntity<Object> errors = errorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        } else {
            Comment comment = commentService.saveComment(Integer.valueOf(recipeId), commentDto, principal);
            CommentDto createdCommentDto = commentFacade.commentToCommentDto(comment);

            return ResponseEntity.ok(createdCommentDto);
        }
    }


    @GetMapping("{recipeId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsForRecipe
            (@PathVariable("recipeId") String recipeId) {
        List<CommentDto> comments = commentService.getAllCommentsForRecipe(Integer.valueOf(recipeId))
                .stream()
                .map(commentFacade::commentToCommentDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<MessageResponse> deleteComment
            (@PathVariable("commentId") String commentId) {

        commentService.deleteComment(Integer.valueOf(commentId));

        return ResponseEntity.ok(new MessageResponse("Comment was deleted"));
    }

}
