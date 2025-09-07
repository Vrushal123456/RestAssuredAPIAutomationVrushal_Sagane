package com.bookapi.testcases;

import org.testng.annotations.Test;

import com.bookapi.assertions.WrappedAssert;
import com.bookapi.endPoints.EndPoints;
import com.bookapi.logs.WrappedReportLogger;
import com.bookapi.pojo.response.Detail;
import com.bookapi.reportBuilder.ApiClient;
import com.bookapi.reportBuilder.RequestBuilder;

public class TokenTest {
	
	@Test(priority = 16, description = "NEGATIVE: Validating if books are not displayed when EXPIRED token used")
	public void NoBooksDisplayedForExpiredToken() {
		WrappedReportLogger.trace("Validating no books are displaued when EXPIRED token used...");

		Detail detail= ApiClient.get(RequestBuilder.withAuthToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaXZhQGdtYWlsIiwiZXhwIjoxNzU1NDk5ODIyfQ.R9Kuds3CU1smTccbs-PUwBfu7b-OFyYl6F7fGUKIquY",null, null), EndPoints.GET_ALL_BOOKS, 403,
				"Detail.json").as(Detail.class);
		WrappedAssert.assertEquals(detail.getDetail(), "Invalid token or expired token", "EXPIRED Authentication token given");

		WrappedReportLogger.trace("Validated no books are displayed when EXPIRED token is used!!!");

	}
	
	@Test(priority = 17, description = "NEGATIVE: Validating if books are not displayed when INVALID token used")
	public void NoBooksDisplayedForInvalidToken() {
		WrappedReportLogger.trace("Validating no books are displaued when INVALID token used...");

		Detail detail= ApiClient.get(RequestBuilder.withAuthToken("invalidTokeneyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaXZhQGdtYWlsIiwiZXhwIjoxNzU1NDk5ODIyfQ.R9Kuds3CU1smTccbs-PUwBfu7b-OFyYl6F7fGUKIquY",null, null), EndPoints.GET_ALL_BOOKS, 403,
				"Detail.json").as(Detail.class);
		WrappedAssert.assertEquals(detail.getDetail(), "Invalid token or expired token", "INVALID Authentication token given");

		WrappedReportLogger.trace("Validated no books are displayed when INVALID token is used!!!");

	}
	

}
