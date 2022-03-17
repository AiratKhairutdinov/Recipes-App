package com.example.recipes.controller;

import com.example.recipes.dto.UserDto;
import com.example.recipes.entity.User;
import com.example.recipes.facade.UserFacade;
import com.example.recipes.service.UserService;
import com.example.recipes.validation.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/users/")
@CrossOrigin("http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private ResponseErrorValidation errorValidation;

    @GetMapping()
    public ResponseEntity<UserDto> getCurrentUser(Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        UserDto userDto = userFacade.userToUserDto(user);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserDto> getUserProfile(@PathVariable("userId") String userId) {
        User user = userService.getUserById(Integer.valueOf(userId));
        UserDto userDto = userFacade.userToUserDto(user);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping()
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDto userDto,
                                             BindingResult bindingResult,
                                             Principal principal) {
        ResponseEntity<Object> errors = errorValidation.mapValidationService(bindingResult);

        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }
        else {
            User user = userService.updateUser(userDto, principal);
            UserDto updatedUser = userFacade.userToUserDto(user);
            return ResponseEntity.ok(updatedUser);
        }
    }
}
