package com.example.backend.model.category;

import com.google.common.net.HttpHeaders;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/categories")
public class CategoryController {

    @Inject
    private CategoryService categoryService;
    @Inject
    private SecurityService securityService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        try{
            return Response.status(200).entity(this.categoryService.getAll()).build();
        }catch (Exception e){
            return Response.status(500).entity(Constants.INTERNAL_ERROR).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addOne(@Valid Category category, @HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
        try{
            this.securityService.verify(token);
            Category existingCategory = this.categoryService.getOne(category.getName());
            if(existingCategory != null)
                throw new ReqException("Category with the same name already exists", 400);
            this.categoryService.addOne(category);
            return Response.status(200).entity(this.categoryService.getAll()).build();
        }catch (ReqException e){
            return Response.status(e.getCode()).entity(e.getMessage()).build();
        }catch (Exception a){
            return Response.status(500).entity(Constants.INTERNAL_ERROR).build();
        }
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response updateOne(@Valid Category category, @HeaderParam(HttpHeaders.AUTHORIZATION) String token, @PathParam("id") Integer categoryId){
        try{
            this.securityService.verify(token);
            return Response.status(200).entity(this.categoryService.updateOne(categoryId, category)).build();
        }catch (ReqException e){
            return Response.status(e.getCode()).entity(e.getMessage()).build();
        }catch (Exception p){
            return Response.status(500).entity(Constants.INTERNAL_ERROR).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getOne(@PathParam("id") Integer id){
        try{
            return Response.status(200).entity(this.categoryService.getOne(id)).build();
        }catch (Exception e){
            return Response.status(500).entity(Constants.INTERNAL_ERROR).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteOne(@PathParam("id") Integer id, @HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
        try{
            this.securityService.verify(token);
            this.categoryService.deleteOne(id);
            return Response.status(200).entity(this.categoryService.getAll()).build();
        }catch (ReqException e){
            return Response.status(e.getCode()).entity(e.getMessage()).build();
        }catch (Exception a){
            return Response.status(500).entity(Constants.INTERNAL_ERROR).build();
        }
    }
}
