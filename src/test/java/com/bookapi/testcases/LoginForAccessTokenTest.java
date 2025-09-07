package com.bookapi.testcases;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.bookapi.assertions.WrappedAssert;
import com.bookapi.endPoints.EndPoints;
import com.bookapi.logs.WrappedReportLogger;
import com.bookapi.pojo.request.LoginForAccessTokenRequest;
import com.bookapi.pojo.response.Detail;
import com.bookapi.pojo.response.LoginForAccessTokenResponse;
import com.bookapi.reportBuilder.ApiClient;
import com.bookapi.reportBuilder.RequestBuilder;

public class LoginForAccessTokenTest {
	public static String access_token;

	@Test(priority=4, description = "Login using newly created user", dependsOnGroups = "userCreation")
	 public void loginUsingnewlyCreatedUser() {
		LoginForAccessTokenRequest loginForAccessTokenRequest = new LoginForAccessTokenRequest();
		loginForAccessTokenRequest.setId(CreateUserSignupTest.newId);
		loginForAccessTokenRequest.setEmail(CreateUserSignupTest.newEmail);
		loginForAccessTokenRequest.setPassword(CreateUserSignupTest.newPwd);
		
		WrappedReportLogger.trace("Login using newly created user....");
		LoginForAccessTokenResponse loginForAccessTokenResponse=ApiClient.post(RequestBuilder.withBodyAndNoAuthToken(loginForAccessTokenRequest,null, null), EndPoints.LOGIN, 200, "LoginForAccessToken.json").as(LoginForAccessTokenResponse.class);
		
		WrappedAssert.assertEquals( loginForAccessTokenResponse.getToken_type(), "bearer", "Validating token type value");
		WrappedAssert.assertNotNull( loginForAccessTokenResponse.getAccess_token(), "Validating if access token is not null string");

		WrappedReportLogger.trace("Logged in using newly created user!!!!");
		
		access_token=loginForAccessTokenResponse.getAccess_token();
		
		}
	
	@Test(priority=5, description = "NEGATIVE: Login using incorrect credentails", dataProvider = "setOfInvalidCreds")
	 public void loginUsingIncorrectCred(String email, String password) {
		
		LoginForAccessTokenRequest loginForAccessTokenRequest = new LoginForAccessTokenRequest();
		loginForAccessTokenRequest.setId(1911);
		loginForAccessTokenRequest.setEmail(email);
		loginForAccessTokenRequest.setPassword(password);
		
		WrappedReportLogger.trace("Login using incorrect credentails....");
		Detail detailResponse=ApiClient.post(RequestBuilder.withBodyAndNoAuthToken(loginForAccessTokenRequest,null, null), EndPoints.LOGIN, 400, "Detail.json").as(Detail.class);
		WrappedAssert.assertEquals( detailResponse.getDetail(), "Incorrect email or password", "Validating detail value");
		WrappedReportLogger.trace("User unable to login using incorrect credentails!!!!");
	}
	
	@DataProvider(name="setOfInvalidCreds") 
	public static Object[][] setOfInvalidCreds() {
		Object[][] obj= new Object[4][2];
		obj[0][0]= CreateUserSignupTest.newEmail;
		obj[0][1]="Incorrectpwd";
		
		obj[1][0]= "IncorrectUser";
		obj[1][1]= CreateUserSignupTest.newPwd;
		
		obj[2][0]= CreateUserSignupTest.newEmail;
		obj[2][1]= "";
		
		obj[3][0]= "";
		obj[3][1]= CreateUserSignupTest.newPwd;

		return obj;
		
}

}
