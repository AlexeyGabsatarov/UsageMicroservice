package com.truphone.poc.usage.security;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.List;

//import org.eclipse.jetty.server.Authentication.User;
import com.google.common.base.Optional;
import com.truphone.poc.usage.core.User;

public class SimpleAuthenticator implements Authenticator<BasicCredentials, User> {
	
	List<User> users;
	
	@Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
		
		for (User user:users) {
			
			if (credentials.getUsername().equals(user.getUsername()) && 
					credentials.getPassword().equals(user.getPassword())) {
				
				return Optional.of(new User(user.getUsername(), user.getMsisdn()));
			}
		}
		
		return Optional.absent();
    }
	
	public SimpleAuthenticator(List<User> users) {
		this.users = users;
	}
}
