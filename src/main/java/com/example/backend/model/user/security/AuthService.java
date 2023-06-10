package com.example.backend.model.user.security;


import com.example.backend.application.dto.*;
import com.example.backend.application.*;
import com.example.backend.model.user.*;
import com.google.common.hash.Hashing;

import javax.inject.Inject;
import java.nio.charset.StandardCharsets;

public class AuthService {

    @Inject
    private UserRepository userRepository;

    public String login(LoginDto dto) throws Exception {
       try {
           User user = this.userRepository.get(dto.getEmail(), this.hashPassword(dto.getPassword()));
           if(user == null) throw new ReqException("Invalid credentials", 400);
           if(!user.isActive()) throw new ReqException("Your account is inactive", 400);
           return JWT.createToken(new TokenInfo(user));
       }catch (ReqException e){
           throw new ReqException(e.getMessage(), e.getCode());
       }catch (Exception e){
           throw new Exception(e);
       }
    }

    public String register(RegisterDto dto) throws Exception {
        try {
            User existingUser = this.userRepository.get(dto.getEmail());
            if (existingUser != null) throw new ReqException("User with the same email already exists", 400);
            User newUser = new User(dto.getEmail(),dto.getName(), dto.getSurname(),hashPassword(dto.getPassword()));
            this.userRepository.add(newUser);
            return JWT.createToken(new TokenInfo(newUser));
        }catch (ReqException e){
            throw new ReqException(e.getMessage(), e.getCode());
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public String hashPassword(String password) {
        return Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    }

}
