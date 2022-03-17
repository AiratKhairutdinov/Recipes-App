package com.example.recipes.dto;

import com.example.recipes.entity.enums.Role;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserDto {

    private Integer id;

    @NotEmpty
    private String firstname;

    @NotEmpty
    private String lastname;

    private String username;

    private String bio;

    private Role role;
}
