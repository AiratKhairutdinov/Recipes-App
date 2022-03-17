package com.example.recipes.entity;

import com.example.recipes.entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, updatable = false)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    private String bio;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Recipe> recipes;

//    @ElementCollection(targetClass = Role.class)
//    @CollectionTable(name = "user_role",
//            joinColumns = @JoinColumn(name = "user_id"))
//    @Enumerated(EnumType.STRING)
//    private Set<Role> roles = new HashSet<>();

    @Enumerated(EnumType.STRING)
    Role role;

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public User(Integer id,
                String username,
                String email,
                String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
