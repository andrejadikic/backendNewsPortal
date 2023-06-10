package com.example.backend.model.user;

import com.example.backend.application.*;
import com.example.backend.application.dto.UserUpdateDto;
import com.example.backend.model.user.security.AuthService;
import com.google.common.hash.Hashing;

import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class UserService {

    @Inject
    private UserRepository userRepository;

    public User addOne(User user) throws ReqException {
        User existingUser = this.userRepository.get(user.getEmail());
        if(existingUser != null){
            throw new ReqException("User with same email already exists", 400);
        }
        user.setPassword(hashPassword(user.getPassword()));
        return this.userRepository.add(user);
    }

    public String hashPassword(String password) {
        return Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    }

    public List<User> getAll() { return this.userRepository.getAll(); }

    public User getOne(Integer id) { return this.userRepository.get(id); }

    public void deleteOne(Integer id) { this.userRepository.delete(id);}

    public User getOne(String email, String password) { return this.userRepository.get(email, password); }

    public void updateOne(Integer id, UserUpdateDto user){ this.userRepository.update(id, user); }
}
