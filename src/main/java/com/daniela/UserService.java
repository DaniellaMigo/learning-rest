package com.daniela;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Daniela on 7/8/2016.
 */

@Path("UserService")
public class UserService {

    UserDao userDao = new UserDao();
    private static final String SUCCESS ="<result>success</result>";
    private static final String FAILURE ="<result>failure</result>";

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_XML)
    public List<User> getUsers() {
        return userDao.getAllUsers();
    }

    @GET
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public User getUser(@PathParam("id") int id) {
        return userDao.getUserById(id);
    }

    @PUT
    @Path("/add")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String addUser(@FormParam("id") int id, @FormParam("name") String name, @FormParam("profession") String profession, @Context HttpServletResponse response) {
        if (userDao.addUser(name, profession)) {
            return SUCCESS;
        }
        return FAILURE;
    }

    @POST
    @Path("/modify")
    @Produces(MediaType.APPLICATION_XML)
    public String modifyUser(@FormParam("id") int id, @FormParam("profession") String profession){
        if (userDao.modifyUser(id, profession)){
            return SUCCESS;
        }
        return FAILURE;
    }

    @DELETE
    @Path("/remove/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public String removeUser(@PathParam("id") int id){
        if (userDao.removeUser(id)){
            return SUCCESS;
        }
        return FAILURE;
    }
}
