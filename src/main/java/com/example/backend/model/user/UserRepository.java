package com.example.backend.model.user.repository;

import rs.raf.demo.dto.update.UserUpdateDto;
import rs.raf.demo.models.User;

import java.util.List;

public interface UserRepository {
    User addOne(User user);
    List<User> getAll();
    User getOne(Integer id);
    void deleteOne(Integer id);
    User getOne(String email, String password);
    User getOne(String email);
    void updateOne(Integer id, UserUpdateDto user);
}
