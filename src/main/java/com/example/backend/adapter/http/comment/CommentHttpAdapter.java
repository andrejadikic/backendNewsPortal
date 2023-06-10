package com.example.backend.adapter.http.comment;

import com.example.backend.application.Constants;
import com.example.backend.application.ReqException;
import com.example.backend.application.dto.TokenInfo;
import com.example.backend.model.comment.Comment;
import com.example.backend.model.comment.CommentService;
import com.example.backend.model.user.User;
import com.example.backend.model.user.UserService;
import com.example.backend.model.user.security.SecurityService;
import com.google.common.net.HttpHeaders;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/comments")
public class CommentHttpAdapter {

    @Inject
    private CommentService commentService;
    @Inject
    private SecurityService securityService;
    @Inject
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        try{
            return Response.status(200).entity(this.commentService.getAll()).build();
        }catch (Exception e){
            return Response.status(500).entity(Constants.INTERNAL_ERROR).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addOne(@Valid Comment comment, @HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
        try {
            TokenInfo info = this.securityService.verify(token);
            User user = this.userService.getOne(info.getId());
            comment.setAuthor(user.getName() + " " + user.getSurname());
            return Response.status(200).entity(this.commentService.addOne(comment)).build();
        }catch (ReqException reqException){
            return Response.status(reqException.getCode()).entity(reqException.getMessage()).build();
        }catch (Exception e){
            return Response.status(500).entity(Constants.INTERNAL_ERROR).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/byArticle/{articleId}")
    public Response getByArticle(@PathParam("articleId") Integer articleId){
        try{
            return Response.status(200).entity(this.commentService.getAll(articleId)).build();
        }catch (Exception e){
            return Response.status(500).entity(Constants.INTERNAL_ERROR).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getOne(@PathParam("id") Integer id) {
        try{
            return Response.status(200).entity(this.commentService.getOne(id)).build();
        }catch (Exception e){
            return Response.status(500).entity(Constants.INTERNAL_ERROR).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteOne(@PathParam("id") Integer id, @HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
        try{
            this.securityService.verifyAdmin(token);
            this.commentService.deleteOne(id);
            return Response.status(200).entity("Success").build();
        }catch (ReqException reqException){
            return Response.status(reqException.getCode()).entity(reqException.getMessage()).build();
        }catch (Exception e){
            return Response.status(500).entity(Constants.INTERNAL_ERROR).build();
        }
    }

}
