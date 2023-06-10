package rs.raf.demo.models;

import rs.raf.demo.enums.Constants;

import javax.swing.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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

    public User(){}

    public User(Integer id, String email, String name, String surname, String type, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.type = type;
        this.password = password;
        this.active = true;
    }

    public User(Integer id, String email, String name, String surname, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.type = Constants.UserType.CREATOR;
        this.active = true;
    }

    public User(Integer id, String email, String name, String surname, String type, String password, boolean active) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.type = type;
        this.password = password;
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
