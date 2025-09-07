package com.bookapi.testcases;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.bookapi.assertions.WrappedAssert;
import com.bookapi.endPoints.EndPoints;
import com.bookapi.logs.WrappedReportLogger;
import com.bookapi.pojo.response.Detail;
import com.bookapi.pojo.response.Message;
import com.bookapi.reportBuilder.ApiClient;
import com.bookapi.reportBuilder.RequestBuilder;

public class DeleteBookTest {
	
	@Test(priority = 14, description = "Delating newly created book can be searched using ID")
	public void validatingIfUserAbleToDeleteBookByID() {
		WrappedReportLogger.trace("Validating if user is able to delete a book with ID "+CreateBookTest.newBookId+"...");
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("bookId", CreateBookTest.newBookId);
		
		Message message=ApiClient.delete(RequestBuilder.withAuthToken(LoginForAccessTokenTest.access_token, null, pathParams), EndPoints.DELETE_BOOK, 200, "Message.json").as(Message.class);
		WrappedAssert.assertEquals("Book deleted successfully", message.getMessage(), "validating if "+CreateBookTest.newBookId+" is delete on the page");
		
		WrappedReportLogger.trace("Validated user is able to delete a book with ID "+CreateBookTest.newBookId+"!!!");
	}
	
	
	@Test(priority = 14, description = "NEGATIVE: againDelating newly created book can be searched using ID", dependsOnMethods = "validatingIfUserAbleToDeleteBookByID")
	public void validatingIfUserAbleToDeleteBookByIDAgain() {
		
		WrappedReportLogger.trace("Validating if user is able to delete a book with ID "+CreateBookTest.newBookId+"...");
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("bookId", CreateBookTest.newBookId);
		
		Detail detail=ApiClient.delete(RequestBuilder.withAuthToken(LoginForAccessTokenTest.access_token, null, pathParams), EndPoints.DELETE_BOOK, 404, "Detail.json").as(Detail.class);
		WrappedAssert.assertEquals("Book not found", detail.getDetail(), "validating if user getting error on deleting book with ID "+CreateBookTest.newBookId+" again");
		
		WrappedReportLogger.trace("Validated user is able to delete a book with ID "+CreateBookTest.newBookId+"!!!");
	
	}

}
