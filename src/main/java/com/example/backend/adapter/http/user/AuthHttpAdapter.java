package com.example.backend.model.security;

import com.example.backend.application.dto.*;
import com.example.backend.application.*;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class AuthController {

    @Inject
    private AuthService authService;

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Valid LoginDto dto) {
        try {
            return Response.status(200).entity(this.authService.login(dto)).build();
        } catch (ReqException e) {
            return Response.status(e.getCode()).entity(e.getMessage()).build();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Response.status(500).entity(Constants.INTERNAL_ERROR).build();
        }
    }

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(@Valid RegisterDto dto) {
        try {
            return Response.status(200).entity(this.authService.register(dto)).build();
        } catch (ReqException e) {
            return Response.status(e.getCode()).entity(e.getMessage()).build();
        }catch (Exception e){
            return Response.status(500).entity(Constants.INTERNAL_ERROR).build();
        }
    }

}

