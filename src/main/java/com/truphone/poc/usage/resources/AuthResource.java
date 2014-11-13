package com.truphone.poc.usage.resources;

import io.dropwizard.auth.Auth;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.truphone.poc.usage.core.AuthSaying;
import com.truphone.poc.usage.core.User;
import com.codahale.metrics.annotation.Timed;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {
    private final List<User> users;
    

    public AuthResource(List<User> users) {
        this.users = users;
    }
    
    @POST
    @Timed
    @Path("/msisdn/{msisdn}")
    public AuthSaying authoriseMSISDN(@PathParam("msisdn") String msisdn) {
    	for (User user:users) {
			if (msisdn.equals(user.getMsisdn())) {
				return new AuthSaying("user and msisdn authorised");
			}
		}
    	
    	throw new WebApplicationException(Response.Status.UNAUTHORIZED);
    }
    
    @POST
    @Timed
    @Path("/user/{msisdn}")
    public AuthSaying authenticateUser(@Auth(required=true) User user,
    		@PathParam("msisdn") String msisdn) {
		if (user.getMsisdn().equals(msisdn)) {
			return new AuthSaying("user and msisdn authorised");
		} else {
			throw new WebApplicationException(Response.Status.UNAUTHORIZED);
		}
    }
}