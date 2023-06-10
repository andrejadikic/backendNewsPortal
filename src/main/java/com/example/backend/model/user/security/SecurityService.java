package com.example.backend.model.security;


import com.example.backend.application.*;
import com.example.backend.application.dto.*;
import com.example.backend.model.user.User;

public class SecurityService {

    public TokenInfo verifyAdmin(String token) throws ReqException {
        try{
            TokenInfo info = JWT.verifyToken(token.split(" ")[1]);
            if(!info.getType().equals(User.UserType.ADMIN))
                throw new ReqException("Forbidden", 403);
            if(!info.isActive())
                throw new ReqException("Inactive user", 403);
            return info;
        }catch (ReqException e){
            throw new ReqException(e.getMessage(), e.getCode());
        }catch (Exception e){
            throw new ReqException("Jwt malformed or invalid", 403);
        }
    }

    public TokenInfo verify(String token) throws ReqException {
        try{
            TokenInfo info = JWT.verifyToken(token.split(" ")[1]);
            if(!info.isActive())
                throw new ReqException("Inactive user", 403);
            return info;
        }catch (ReqException e){
            throw new ReqException(e.getMessage(), e.getCode());
        }catch (Exception e){
            e.printStackTrace();
            throw new ReqException("Jwt malformed or invalid", 403);
        }
    }

}
