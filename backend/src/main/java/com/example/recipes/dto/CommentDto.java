package com.example.recipes.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CommentDto {

    private Integer id;

    @NotEmpty
    private String message;

    private String username;
}
