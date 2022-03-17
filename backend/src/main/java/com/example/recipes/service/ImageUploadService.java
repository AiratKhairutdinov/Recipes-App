package com.example.recipes.service;

import com.example.recipes.entity.ImageModel;
import com.example.recipes.entity.Recipe;
import com.example.recipes.entity.User;
import com.example.recipes.repository.ImageRepository;
import com.example.recipes.repository.RecipeRepository;
import com.example.recipes.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
@Slf4j
public class ImageUploadService {

    private final ImageRepository imageRepository;

    private final UserRepository userRepository;

    private final RecipeRepository recipeRepository;

    @Autowired
    public ImageUploadService(ImageRepository imageRepository,
                              UserRepository userRepository,
                              RecipeRepository recipeRepository) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
    }

    public ImageModel uploadImageToRecipe(MultipartFile file, Principal principal, Integer recipeId)
            throws IOException {
        User user = getUserByPrincipal(principal);
        Recipe recipe = user.getRecipes()
                .stream()
                .filter(r->r.getId().equals(recipeId))
                .collect(toSingleRecipeCollector());

        ImageModel imageModel = new ImageModel();
        imageModel.setRecipeId(recipe.getId());
        imageModel.setImageBytes(compressBytes(file.getBytes()));
        imageModel.setName(file.getOriginalFilename());

        log.info("Uploading image to Recipe {}", recipe.getId());

        return imageRepository.save(imageModel);
    }

    public ImageModel uploadImageToUser(MultipartFile file, Principal principal)
            throws IOException {
        User user = getUserByPrincipal(principal);

        log.info("Uploading image profile to User {}", user.getUsername());

        ImageModel userProfileImage = imageRepository.findByUserId(user.getId()).orElse(null);
        if (!ObjectUtils.isEmpty(userProfileImage)) {
            imageRepository.delete(userProfileImage);
        }

        ImageModel imageModel = new ImageModel();
        imageModel.setUserId(user.getId());
        imageModel.setImageBytes(compressBytes(file.getBytes()));
        imageModel.setName(file.getOriginalFilename());
        return imageRepository.save(imageModel);
    }

    public ImageModel getImageForUser(Principal principal) {
        User user = getUserByPrincipal(principal);

        ImageModel imageModel = imageRepository.findByUserId(user.getId()).orElse(null);

        if (!ObjectUtils.isEmpty(imageModel)) {
            imageModel.setImageBytes(decompressBytes(imageModel.getImageBytes()));
        }
        return imageModel;
    }

    public ImageModel getImageForRecipe(Integer recipeId) {

        ImageModel imageModel = imageRepository.findByRecipeId(recipeId).orElse(null);

        if (!ObjectUtils.isEmpty(imageModel)) {
            imageModel.setImageBytes(decompressBytes(imageModel.getImageBytes()));
        }
        return imageModel;
    }


    private byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            log.error("Cannot compress Bytes");
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    private static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException e) {
            log.error("Cannot decompress Bytes");
        }
        return outputStream.toByteArray();
    }


    private <T> Collector<T, ?, T> toSingleRecipeCollector() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException();
                    }
                    return list.get(0);
                }
        );
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Username not found: " + username));
    }


}
