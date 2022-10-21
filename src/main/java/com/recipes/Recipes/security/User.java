package com.recipes.Recipes.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "users")
public class User {

    @NotBlank
    @Pattern(regexp = ".+@.+\\..+")
    private String email;
    @NotBlank
    @Size(min = 8)
    private String password;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    @NonNull
    @Column(name = "user_id")
    private Long id;


    public User() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}