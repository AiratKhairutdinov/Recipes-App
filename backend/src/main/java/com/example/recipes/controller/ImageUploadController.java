package com.example.recipes.controller;

import com.example.recipes.entity.ImageModel;
import com.example.recipes.payload.response.MessageResponse;
import com.example.recipes.service.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/images/")
@CrossOrigin("http://localhost:4200")
public class ImageUploadController {

    @Autowired
    private ImageUploadService imageUploadService;

    @GetMapping()
    public ResponseEntity<ImageModel> getImageForUser(Principal principal) {
        ImageModel userImage = imageUploadService.getImageForUser(principal);
        return ResponseEntity.ok(userImage);
    }

    @GetMapping("{recipeId}")
    public ResponseEntity<ImageModel> getImageForRecipe(@PathVariable("recipeId") String recipeId) {
        ImageModel postImage = imageUploadService.getImageForRecipe(Integer.valueOf(recipeId));
        return new ResponseEntity<>(postImage, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<MessageResponse> uploadImageToUser(@RequestParam("file") MultipartFile file,
                                                             Principal principal) throws IOException {

        imageUploadService.uploadImageToUser(file, principal);
        return ResponseEntity.ok(new MessageResponse("Image Uploaded Successfully"));
    }

    @PostMapping("{recipeId}")
    public ResponseEntity<MessageResponse> uploadImageToRecipe(@PathVariable("recipeId") String recipeId,
                                                             @RequestParam("file") MultipartFile file,
                                                             Principal principal) throws IOException {
        imageUploadService.uploadImageToRecipe(file, principal, Integer.valueOf(recipeId));
        return ResponseEntity.ok(new MessageResponse("Image Uploaded Successfully"));
    }
}
