package com.example.backend.model.user;

import com.example.backend.application.dto.UserUpdateDto;

import java.util.List;

public interface UserRepository {
    User add(User user);
    List<User> getAll();
    User get(Integer id);
    void delete(Integer id);
    User get(String email, String password);
    User get(String email);
    void update(Integer id, UserUpdateDto user);
}
