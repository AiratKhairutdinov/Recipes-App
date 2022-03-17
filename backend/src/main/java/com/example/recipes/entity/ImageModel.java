package com.example.recipes.entity;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Data
@Entity(name = "image_model")
public class ImageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "BYTEA")
    private byte[] imageBytes;

    @JsonIgnore
    private Integer userId;

    @JsonIgnore
    private Integer recipeId;
}
