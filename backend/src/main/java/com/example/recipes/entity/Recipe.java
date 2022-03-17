package com.example.recipes.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "recipe")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private RecipeCategory category;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER,
            mappedBy = "recipe", orphanRemoval = true)
    private List<Comment> comments;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    private Integer prepTime;

    private Integer cookTime;

    private Integer servings;

    @Column
    @ElementCollection(targetClass = String.class)
    private List<String> ingredients;

    @Column
    @ElementCollection(targetClass = String.class)
    private List<String> directions;

    @Column
    @ElementCollection(targetClass = String.class)
    private Set<String> usersLiked = new HashSet<>();

    private Integer likes;

    @Column(updatable = false)
    private LocalDateTime createdDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }

}
