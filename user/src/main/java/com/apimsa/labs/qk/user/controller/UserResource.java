package com.apimsa.labs.qk.user.controller;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.faulttolerance.Asynchronous;
import org.eclipse.microprofile.faulttolerance.Bulkhead;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.cache.NoCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apimsa.labs.qk.user.config.UserConfiguration;
import com.apimsa.labs.qk.user.domain.model.User;
import com.apimsa.labs.qk.user.domain.service.UserService;

import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.TimeoutException;

@ApplicationScoped
@Path(UserResourceAPIPaths.BASE_PATH)
@Produces("application/json")
@Consumes("application/json")
@Tag(name = "Users API", description = "Users API")
public class UserResource {

    private Logger log = LoggerFactory.getLogger(UserResource.class);

	@Inject 
	UserService userService;
	
	@Inject
	UserConfiguration userapiConfig;
	
	@Inject
	Validator validator; // useful to call methods when not using @Valid annotation, see user update method below
	
	//Injection of the SecurityIdentity is supported in both @RequestScoped and @ApplicationScoped contexts.
	@Inject
    SecurityIdentity securityIdentity;
	
	//To access JsonWebToken claims, you may simply inject the token itself
	
	
	@GET
	@Path(UserResourceAPIPaths.GET_USERS)
	@Counted(name = "countGetUsers", description = "Counts how many times the getUsers method has been invoked")
    @Timed(name = "timeGetUsers", description = "Times how long it takes to invoke the getUsers method", unit = MetricUnits.MILLISECONDS)
	@CircuitBreaker(successThreshold = 5, requestVolumeThreshold = 4, failureRatio=0.75, delay = 1000)
	public Response findAllUsers() {
	
		log.info(String.format("Get users"));
		return Response.ok((List<User>)userService.findAllUsers()).build();
	}
	
	@GET
	@Path(UserResourceAPIPaths.GET_USER)
	@Timeout(200)
	@Fallback(fallbackMethod = "findStaticUser")
	@Retry(retryOn = {RuntimeException.class, TimeoutException.class}, maxRetries = 2)
	public Response findUserById(@PathParam("userid") Long id) {
	
		log.info(String.format("Get user : %d", id));
		return Response.ok((User)userService.findUserById(id)).build();
	}
	
	//Fallback method
	public Response findStaticUser(@PathParam("userid") Long id) {
		
		log.info(String.format("Get user : %d", id));
		return Response.ok(new User(id, "User", "Email")).build();
	}
	
	@POST
	@Path(UserResourceAPIPaths.CREATE_USER)
	// Using hibernate validator on the user object, used to return class, property, value violations if any
	@RolesAllowed("api-admin")
	@NoCache
	public Response create(@Valid User user) {
		
		log.info("Logged in user is: " + securityIdentity.getPrincipal().getName());
		log.info(String.format("Create user using version" + userapiConfig.getApiversion() + "developed by" + userapiConfig.getAuthor().orElse("APIDEV") ));
		userService.createUser(user);
		logAsync(user.getEmail());
		//use status to return http status code from api
		return Response.status(201).build();
	}
	
	@PUT
	@Path(UserResourceAPIPaths.UPDATE_USER)
	public Response update(User user) {
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		if (violations.size() > 2) {
			return Response.status(400).build();
		}
		log.info(String.format("Update users"));
		userService.updateUser(user);
		// use entity to return a body
		return Response.status(204).entity("Update complete").build();
	}
	
	@DELETE
	@Path(UserResourceAPIPaths.DELETE_USER)
	public Response delete(@PathParam("userid") Long id) {
	
		log.info(String.format("Delete users"));
		userService.deleteUser(id);
		return Response.status(204).build();
	}
	
	// execute in asynchronous thread pool, also using bulkhead to allow maximum 5 concurrent requests, maximum 10 requests allowed in the waiting queue
	@Asynchronous
	@Bulkhead(value = 5, waitingTaskQueue = 10)
	private Future logAsync(String str) {
		log.info("New user added at: "+new java.util.Date());
		log.info("User: {}", str);
	return CompletableFuture.completedFuture("ok");
	}
}