package com.example.recipes.annotation;

import com.example.recipes.validation.PasswordMatchesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordMatches {

    String message() default "Password does not match";

    Class<?>[] groups() default{};

    Class<? extends Payload>[] payload() default{};
}
