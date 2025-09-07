package com.bookapi.testcases;

import org.testng.annotations.Test;

import com.bookapi.assertions.WrappedAssert;
import com.bookapi.endPoints.EndPoints;
import com.bookapi.logs.WrappedReportLogger;
import com.bookapi.pojo.response.Detail;
import com.bookapi.reportBuilder.ApiClient;
import com.bookapi.reportBuilder.RequestBuilder;

public class GetAllBooksTest {

	@Test(priority = 6, description = "Validating if books are displayed")
	public void validatingBooksAreDisplayed() {
		WrappedReportLogger.trace("Validating if books are displaued...");
		ApiClient.get(RequestBuilder.withAuthToken(LoginForAccessTokenTest.access_token,null, null), EndPoints.GET_ALL_BOOKS, 200,
				"GetAllBooks.json");
		WrappedReportLogger.trace("Validated books are displayed!!!");

	}
	
	
	@Test(priority = 7, description = "NEGATIVE: Validating if books are not displayed when invalid token used")
	public void NoBooksDisplayedForInvalidToken() {
		WrappedReportLogger.trace("Validating no books are displaued when invalid token used...");

		Detail detail= ApiClient.get(RequestBuilder.withAuthToken("incorrectToken",null, null), EndPoints.GET_ALL_BOOKS, 403,
				"Detail.json").as(Detail.class);
		WrappedAssert.assertEquals(detail.getDetail(), "Invalid token or expired token", "iNVALID Authentication token given");

		WrappedReportLogger.trace("Validated no books are displayed when invalid token is used!!!");

	}
	
	
	@Test(priority = 8, description = "NEGATIVE: Validating if books are displayed when no token used")
	public void NoBooksDisplayedForNoToken() {
		WrappedReportLogger.trace("Validating no books are displaued when no token used...");

		Detail detail= ApiClient.get(RequestBuilder.defaultSpec(), EndPoints.GET_ALL_BOOKS, 403,
				"Detail.json").as(Detail.class);
		WrappedAssert.assertEquals(detail.getDetail(), "Not authenticated", "No Authentication token given");

		WrappedReportLogger.trace("Validated no books are displayed when no token is used!!!");

	}

}
