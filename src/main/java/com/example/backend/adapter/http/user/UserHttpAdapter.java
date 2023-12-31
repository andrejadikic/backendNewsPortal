package com.example.backend.adapter.http.user;

import com.example.backend.application.*;
import com.example.backend.application.dto.UserUpdateDto;
import com.example.backend.model.user.*;
import com.example.backend.model.user.security.SecurityService;
import com.google.common.net.HttpHeaders;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserHttpAdapter {

    @Inject
    private UserService userService;
    @Inject
    private SecurityService securityService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
        try {
            this.securityService.verifyAdmin(token);
            return Response.status(200).entity(this.userService.getAll()).build();
        }catch (ReqException e){
            return Response.status(e.getCode()).entity(e.getMessage()).build();
        }catch (Exception e){
            return Response.status(500).entity(Constants.INTERNAL_ERROR).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addOne(@Valid User user, @HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
        try{
            this.securityService.verifyAdmin(token);
            this.userService.addOne(user);
            return Response.status(200).entity(this.userService.getAll()).build();
        }catch (ReqException e){
           return Response.status(e.getCode()).entity(e.getMessage()).build();
        }catch (Exception e){
            return Response.status(500).entity(Constants.INTERNAL_ERROR).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getOne(@PathParam("id") Integer id, @HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
        try{
            this.securityService.verifyAdmin(token);
            return Response.status(200).entity(this.userService.getOne(id)).build();
        }catch (ReqException e){
            return Response.status(e.getCode()).entity(e.getMessage()).build();
        }catch (Exception e){
            return Response.status(500).entity(Constants.INTERNAL_ERROR).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteOne(@PathParam("id") Integer id, @HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
        try{
            this.securityService.verifyAdmin(token);
            this.userService.deleteOne(id);
            return Response.status(200).entity(this.userService.getAll()).build();
        }catch (ReqException e){
            return Response.status(e.getCode()).entity(e.getMessage()).build();
        }catch (Exception e){
            return Response.status(500).entity(Constants.INTERNAL_ERROR).build();
        }
    }

    @PATCH
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOne(@PathParam("id") Integer id, @HeaderParam(HttpHeaders.AUTHORIZATION) String token, @Valid UserUpdateDto user){
        try{
            this.securityService.verifyAdmin(token);
            this.userService.updateOne(id, user);
            return Response.status(200).entity(this.userService.getAll()).build();
        }catch (ReqException e){
            return Response.status(e.getCode()).entity(e.getMessage()).build();
        }catch (Exception e){
            return Response.status(500).entity(Constants.INTERNAL_ERROR).build();
        }
    }

}
