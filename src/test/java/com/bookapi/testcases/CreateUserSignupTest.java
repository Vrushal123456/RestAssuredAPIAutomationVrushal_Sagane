package com.bookapi.testcases;

import org.testng.annotations.Test;

import com.bookapi.assertions.WrappedAssert;
import com.bookapi.endPoints.EndPoints;
import com.bookapi.logs.WrappedReportLogger;
import com.bookapi.pojo.request.CreateUserSignup;
import com.bookapi.pojo.response.Detail;
import com.bookapi.pojo.response.Message;
import com.bookapi.reportBuilder.ApiClient;
import com.bookapi.reportBuilder.RequestBuilder;
import com.bookapi.utils.DataGenerator;

import io.restassured.response.Response;

public class CreateUserSignupTest {
	public static String newEmail;
	public static int newId;
	public static String newPwd;
	
	
	@Test(priority=2, description = "Validating creation of new user", groups = "userCreation")
	 public void createNewUser() {
		WrappedReportLogger.trace("Creating Sign Up Request with Random Email ID and Random ID....");
		newEmail= DataGenerator.randomEmail();
		newId= DataGenerator.randomID();
		newPwd= DataGenerator.randomPwd();
		
		CreateUserSignup createUserSignupRequest = new CreateUserSignup();
		createUserSignupRequest.setId(newId);
		createUserSignupRequest.setEmail(newEmail);
		createUserSignupRequest.setPassword(newPwd);
		WrappedReportLogger.trace("Created Sign Up Request with random email as "+newEmail+" and random ID as "+newId+"!!!!");
		
		WrappedReportLogger.trace("Creating a new user....");
		
		Response response=ApiClient.post(RequestBuilder.withBodyAndNoAuthToken(createUserSignupRequest,null, null), EndPoints.SIGNUP, 200, "Message.json");

		Message messageResponse=response.as(Message.class);
		WrappedAssert.assertEquals( messageResponse.getMessage(), "User created successfully", "Validating message value");
		
		WrappedAssert.assertEquals(response.getHeader("server"), "uvicorn", "Validating Server header");
	    WrappedAssert.assertEquals(response.getHeader("content-type"), "application/json", "Validating Content-Type header");

		WrappedReportLogger.trace("Created a new user!!!!");
		
		
		}
	
	
	@Test(priority=3, description = "NEGATIVE: Validating creation of new user with existing email and id", dependsOnMethods = "createNewUser")
	 public void createExistingUser() {
		
		CreateUserSignup createUserSignupRequest = new CreateUserSignup();
		createUserSignupRequest.setId(newId);
		createUserSignupRequest.setEmail(newEmail);
		createUserSignupRequest.setPassword(newPwd);
		
		WrappedReportLogger.trace("Adding an exsting user....");
		Detail detailResponse=ApiClient.post(RequestBuilder.withBodyAndNoAuthToken(createUserSignupRequest,null, null), EndPoints.SIGNUP, 400, "Detail.json").as(Detail.class);
		WrappedAssert.assertEquals( detailResponse.getDetail(), "Email already registered", "Validating detail value");
		WrappedReportLogger.trace("Unable to add an existing user!!!!");
		
		
	}
	
	
	
	
	
	

}
