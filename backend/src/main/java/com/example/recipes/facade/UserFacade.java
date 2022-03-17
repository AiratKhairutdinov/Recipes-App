package com.example.recipes.facade;

import com.example.recipes.dto.UserDto;
import com.example.recipes.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {

    public UserDto userToUserDto(User user) {
        UserDto userDTO = new UserDto();
        userDTO.setId(user.getId());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setLastname(user.getLastname());
        userDTO.setUsername(user.getUsername());
        userDTO.setBio(user.getBio());
        userDTO.setRole(user.getRole());
        return userDTO;
    }
}
