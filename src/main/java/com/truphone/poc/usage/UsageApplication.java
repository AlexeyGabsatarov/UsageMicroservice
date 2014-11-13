package com.truphone.poc.usage;

import io.dropwizard.Application;
import io.dropwizard.auth.basic.BasicAuthProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.truphone.poc.usage.core.TemplateHealthCheck;
import com.truphone.poc.usage.core.User;
import com.truphone.poc.usage.resources.AuthResource;
import com.truphone.poc.usage.resources.UsageResource;
import com.truphone.poc.usage.security.SimpleAuthenticator;

public class UsageApplication extends Application<UsageConfiguration>{
	
	List<User> users;
	
    public static void main(String[] args) throws Exception {
        new UsageApplication().run(args);
    }
    
    @Override
    public String getName() {
        return "usage";
    }

	@Override
	public void initialize(Bootstrap<UsageConfiguration> bootstrap) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run(UsageConfiguration configuration, Environment environment) throws Exception {	
		this.users = loadUsers(configuration);
		
		final UsageResource usage = new UsageResource(
		        configuration.getTemplate(),
		        configuration.getDefaultName()
		    );
		final AuthResource auth = new AuthResource(users);
		
	    final TemplateHealthCheck healthCheck =
	            new TemplateHealthCheck(configuration.getTemplate());

	    environment.jersey().register(new BasicAuthProvider<User>(new SimpleAuthenticator(users),
                "usage realm"));

	    environment.healthChecks().register("template", healthCheck);
		environment.jersey().register(usage);
		environment.jersey().register(auth);
	}

	private List<User> loadUsers(UsageConfiguration configuration) {
		ObjectMapper objectMapper = new ObjectMapper();
		
		List<User> users = new ArrayList<User>();
		
		try {
			users = Arrays.asList(objectMapper.readValue(new File(configuration.getUsersFile()), 
					User[].class));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return users;
	}
}
