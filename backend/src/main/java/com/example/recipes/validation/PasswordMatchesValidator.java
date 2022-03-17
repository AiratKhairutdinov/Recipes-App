package com.example.recipes.validation;

import com.example.recipes.annotation.PasswordMatches;
import com.example.recipes.payload.request.SignupRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        SignupRequest userSignupRequest = (SignupRequest) o;
        return userSignupRequest.getPassword().equals(userSignupRequest.getConfirmPassword());
    }

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
}
