package com.example.backend.model.article;

import com.example.backend.application.*;
import com.example.backend.application.dto.ArticleUpdateDto;
import com.example.backend.application.dto.TokenInfo;
import com.example.backend.model.user.security.*;
import com.google.common.net.HttpHeaders;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/articles")
public class ArticleController {

    @Inject
    private ArticleService articleService;
    @Inject
    private SecurityService securityService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        try{
            return Response.status(200).entity(this.articleService.getAll()).build();
        }catch (Exception e){
            return Response.status(500).entity(Constants.INTERNAL_ERROR).build();
        }
    }

    @GET
    @Path("/search/{text}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByTitle(@PathParam("text") String text, @QueryParam("categoryId") Integer categoryId){
        try{
            return Response.status(200).entity(this.articleService.getByName(text, categoryId)).build();
        }catch (Exception e){
            return Response.status(500).entity(Constants.INTERNAL_ERROR).build();
        }
    }

    @GET
    @Path("/most-read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMostRead(){
        try{
            return Response.status(200).entity(this.articleService.mostRead()).build();
        }catch (Exception e){
            return Response.status(500).entity(Constants.INTERNAL_ERROR).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/byTag/{tag}")
    public Response getByTag(@PathParam("tag") String tag){
        try{
            return Response.status(200).entity(this.articleService.getByTag(tag)).build();
        }catch (Exception e){
            return Response.status(500).entity(Constants.INTERNAL_ERROR).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/byTag/{exclude}")
    public Response getByTags(List<String> tags, @PathParam("exclude") Integer articleId){
        try{
            return Response.status(200).entity(this.articleService.getByTags(tags, articleId)).build();
        }catch (Exception e){
            return Response.status(500).entity(Constants.INTERNAL_ERROR).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/byCategory/{categoryId}")
    public Response getByTag(@PathParam("categoryId") Integer categoryId){
        try{
            return Response.status(200).entity(this.articleService.getAll(categoryId)).build();
        }catch (Exception e){
            return Response.status(500).entity(Constants.INTERNAL_ERROR).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/most-reacted")
    public Response getMostReacted() {
        try{
            return Response.status(200).entity(this.articleService.mostReacted()).build();
        }catch (Exception e){
            return Response.status(500).entity(Constants.INTERNAL_ERROR).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addOne(@Valid Article article, @HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
        try{
            this.securityService.verify(token);
            return Response.status(200).entity(this.articleService.addOne(article)).build();
        }catch (ReqException e){
            return Response.status(e.getCode()).entity(e.getMessage()).build();
        }catch (Exception e){
           return Response.status(500).entity(Constants.INTERNAL_ERROR).build();
        }

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getOne(@PathParam("id") Integer id) {
        try{
            return Response.status(200).entity(this.articleService.getOne(id)).build();
        }catch (Exception e){
            return Response.status(500).entity(Constants.INTERNAL_ERROR).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteOne(@PathParam("id") Integer id, @HeaderParam(HttpHeaders.AUTHORIZATION) String token ) {
        try{
            this.securityService.verify(token);
            return Response.status(200).entity(this.articleService.deleteOne(id)).build();
        }catch (ReqException e){
            return Response.status(e.getCode()).entity(e.getMessage()).build();
        }catch (Exception e){
            return Response.status(500).entity(Constants.INTERNAL_ERROR).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/view/{articleId}")
    public Response increaseCount(@PathParam("articleId") Integer articleId) {
        try{
            return Response.status(200).entity(this.articleService.increaseViewCount(articleId)).build();
        }catch (ReqException e){
            return Response.status(e.getCode()).entity(e.getMessage()).build();
        }catch (Exception e){
            return Response.status(500).entity(Constants.INTERNAL_ERROR).build();
        }
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response updateOne(@PathParam("id") Integer articleId, @HeaderParam(HttpHeaders.AUTHORIZATION) String token, @Valid ArticleUpdateDto updateData){
        try{
           this.securityService.verify(token);
           return Response.status(200).entity(this.articleService.updateOne(articleId, updateData)).build();
        }catch (ReqException e){
            return Response.status(e.getCode()).entity(e.getMessage()).build();
        }catch (Exception f){
            return Response.status(500).entity(Constants.INTERNAL_ERROR).build();
        }
    }
}
