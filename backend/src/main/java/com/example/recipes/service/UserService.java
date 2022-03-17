package com.example.recipes.service;

import com.example.recipes.dto.UserDto;
import com.example.recipes.entity.User;
import com.example.recipes.entity.enums.Role;
import com.example.recipes.exception.UserExistException;
import com.example.recipes.payload.request.SignupRequest;
import com.example.recipes.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(SignupRequest userIn) {
        User user = new User();
        user.setEmail(userIn.getEmail());
        user.setUsername(userIn.getUsername());
        user.setFirstname(userIn.getFirstname());
        user.setLastname(userIn.getLastname());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        user.setRole(Role.USER);

        try {
            log.info("Saving User {}", userIn.getEmail());
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("Error during registration. {}", e.getMessage());
            throw new UserExistException("The user " + user.getUsername() + " already exist. Please check credentials");
        }
    }

    public User updateUser(UserDto userDto, Principal principal) {
        User user = getUserByPrincipal(principal);
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setBio(userDto.getBio());

        return userRepository.save(user);
    }

    public User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Username not found: " + username));
    }

    public User getUserById(Integer userId) {
        return userRepository.findUserById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }



}
