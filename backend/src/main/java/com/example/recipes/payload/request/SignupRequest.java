package com.example.recipes.payload.request;

import com.example.recipes.annotation.PasswordMatches;
import com.example.recipes.annotation.ValidEmail;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class SignupRequest {

    @Email(message = "It should have email format")
    @NotBlank
    @ValidEmail
    private String email;

    @NotBlank(message = "Please enter your username")
    private String username;

    @NotBlank(message = "Please enter your firstname")
    private String firstname;

    @NotEmpty(message = "Please enter your lastname")
    private String lastname;

    @NotEmpty(message = "Password is required")
    @Size(min = 6)
    private String password;

    private String confirmPassword;
}
