package com.apimsa.labs.qk;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apimsa.labs.qk.user.controller.UserResource;
import com.apimsa.labs.qk.user.controller.UserResourceAPIPaths;
import com.apimsa.labs.qk.user.domain.model.User;

import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import static org.hamcrest.Matchers.equalTo;


import org.apache.http.HttpStatus;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@QuarkusTest
public class UserResourceTest {

	private Logger log = LoggerFactory.getLogger(UserResourceTest.class);
	
	@ConfigProperty(name = "quarkus.oidc.token-path") // added to application.yaml
	String keycloakTokenURL;
	
	
    @Test
    public void testGetUserEndpoint() {
    	
    	RestAssured.defaultParser = Parser.JSON;
   
    	String secret = "d6286cb9-863d-4492-abed-ed4f4e1fbf67";
    	
    	Response response;
    	JsonReader jsonReader;
    	
    	log.info("Get user token");
    	
    	// Get token for identity with "user" level access
    	response = given().urlEncodingEnabled(true)
			.auth().preemptive().basic("userapi", secret)
			.param("grant_type", "password")
			.param("client_id", "userapi")
			.param("username", "joe")
			.param("password", "joe123?")
			.header("Accept", ContentType.JSON.getAcceptHeader())
			.post(keycloakTokenURL)
					.then().statusCode(200).extract()
					.response();
    	
    	jsonReader = Json.createReader(new
    			StringReader(response.getBody().asString()));
    			String userToken = jsonReader.readObject().getString("access_token");
    	
    	log.info(String.format("User token : %s", userToken));
    	
    	
    	log.info("Get admin token");
    	
		// Get token for identity with "admin" level access
    	response = given().urlEncodingEnabled(true)
			.auth().preemptive().basic("userapi", secret)
			.param("grant_type", "password")
			.param("client_id", "userapi")
			.param("username", "amol")
			.param("password", "amol123?")
			.header("Accept", ContentType.JSON.getAcceptHeader())
			.post(keycloakTokenURL)
					.then().statusCode(200).extract()
					.response();
    	
    	jsonReader = Json.createReader(new
    			StringReader(response.getBody().asString()));
    			String adminToken = jsonReader.readObject().getString("access_token");
    	
    			
    	log.info(String.format("Admin token : %s", adminToken));		
    			
    			
    	/**
    	User result = 
          given()
          .when().get("/api/v1/user/111")
          .then()
          .assertThat()
          .statusCode(200)
          .contentType(ContentType.JSON)
          .extract()
          .as(User.class);
    	 */
		/*
		 * Response result = given() .when().get("/api/v1/user/111") .then()
		 * .assertThat() .statusCode(200) .contentType(ContentType.JSON) .extract()
		 * .response();
		 * 
		 * System.out.println(result.asString());
		 * 
		 * String nameOfUser = result.jsonPath().getString("name");
		 * 
		 * assertThat(nameOfUser, is(equalTo("John")));
		 */
    			
		//RestAssured.baseURI = "http://localhost:8083" ;

		// Test GET 
		Response result = 
				given().auth().oauth2(userToken)
				.when().get("/api/v1/user/111").then()
				.assertThat() .statusCode(200) .contentType(ContentType.JSON) .extract()
				.response();
		  
		System.out.println(result.asString());
		String nameOfUser = result.jsonPath().getString("name");
		assertThat(nameOfUser, is(equalTo("John")));
		 		
    	
		  
		JsonObject objUser; 
		  
		// Test POST Order 
		  
		objUser = Json.createObjectBuilder()
				  // .add("id", new Long(200)) // commenting as @Id is Generated.AUTO
				  .add("name", "Mike" )
				  .add("email", "mike@demo.com")
				  .build();
		  
		  given().auth()
		  .oauth2(adminToken)
		  .contentType("application/json")
		  .body(objUser.toString())
		  .when()
		  .post("/api/v1/user/")
		  .then()
		  .statusCode(201);
		  
			/*
			 * // Create new JSON for User to update objUser = Json.createObjectBuilder()
			 * //.add("id", new Long(200)) .add("name", "Mikey" ) .add("email",
			 * "mikey@demo.com") .build();
			 * 
			 * 
			 * // Test UPDATE User given().auth() .oauth2(adminToken)
			 * .contentType("application/json") .body(objUser.toString()) .when()
			 * .put("/api/v1/user/") .then() .statusCode(204);
			 * 
			 * 
			 * // Test DELETE given().auth() .oauth2(adminToken) .when()
			 * .delete("/api/v1/user/200") .then() .statusCode(204);
			 */
		  
    }

}