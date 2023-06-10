package com.example.backend.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User {

    private Integer id;
    @NotNull(message = "Email is required")
    @NotEmpty(message = "Email is required")
    private String email;
    @NotNull(message = "Name is required")
    @NotEmpty(message = "Name is required")
    private String name;
    @NotNull(message = "Surname is required")
    @NotEmpty(message = "Surname is required")
    private String surname;
    @NotNull(message = "Type is required")
    @NotEmpty(message = "Type is required")
    @Pattern(regexp = "^(content_creator|admin)$", message = "Invalid type")
    private String type;
    @NotNull(message = "Password is required")
    @NotEmpty(message = "Password is required")
    private String password;
    @NotNull(message = "User activity is missing")
    private boolean active;

    public User(Integer id, String email, String name, String surname, String type, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.type = type;
        this.password = password;
        this.active = true;
    }

    public User(String email, String name, String surname, String password) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.type = UserType.CREATOR;
        this.active = true;
    }

    public static class UserType {
        public static final String CREATOR = "content_creator";
        public static final String ADMIN = "admin";
    }
}
