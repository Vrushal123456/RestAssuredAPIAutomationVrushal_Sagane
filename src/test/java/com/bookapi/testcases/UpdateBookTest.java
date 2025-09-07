package com.bookapi.testcases;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.bookapi.assertions.WrappedAssert;
import com.bookapi.endPoints.EndPoints;
import com.bookapi.logs.WrappedReportLogger;
import com.bookapi.pojo.request.UpdateBook;
import com.bookapi.pojo.response.GetBook;
import com.bookapi.reportBuilder.ApiClient;
import com.bookapi.reportBuilder.RequestBuilder;

import io.restassured.response.Response;

public class UpdateBookTest {

	
	@Test(priority = 12, description = "Updating newly added book")
	public void validateUserAbleToUpdateNewlyAddedBook() {
		WrappedReportLogger.trace("Validating if user is able to update the book with ID "+CreateBookTest.newBookId+"...");
		
		UpdateBook updateBook= new UpdateBook(); 
		updateBook.setAuthor("updated Author Name");
		updateBook.setBook_summary("updated book summary");
		updateBook.setId(CreateBookTest.newBookId);
		updateBook.setName("updated  Name");
		updateBook.setPublished_year(1700);
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("bookId", CreateBookTest.newBookId);
		
		Response response=ApiClient.put(RequestBuilder.withBodyAndAuthToken(updateBook,LoginForAccessTokenTest.access_token, null, pathParams), EndPoints.UPDATE_BOOK, 200, "GetBook.json");
		
		
		GetBook getBook=response.as(GetBook.class);
		WrappedAssert.assertEquals("updated Author Name", getBook.getAuthor(), "validating if book with ID"+CreateBookTest.newBookId+" name has beeen updated");
		WrappedAssert.assertEquals(response.getHeader("server"), "uvicorn", "Validating Server header");
	    WrappedAssert.assertEquals(response.getHeader("content-type"), "application/json", "Validating Content-Type header");

	    
		WrappedReportLogger.trace("Validated user is able to update the book with ID "+CreateBookTest.newBookId+"!!!!");
		
	}
	
	
	@Test(priority = 13, description = "Validating newly created book can be searched using ID", dependsOnMethods = "validateUserAbleToUpdateNewlyAddedBook" )
	public void validatingIfUserAbleToSearchUpdatedBookByID() {
		WrappedReportLogger.trace("Validating if user is able to search UPDATED book with ID "+CreateBookTest.newBookId+"...");
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("bookId", CreateBookTest.newBookId);
		
		GetBook getBook=ApiClient.get(RequestBuilder.withAuthToken(LoginForAccessTokenTest.access_token, null, pathParams), EndPoints.GET_BOOK, 200, "GetBook.json").as(GetBook.class);
		WrappedAssert.assertEquals("updated Author Name", getBook.getAuthor(), "validating if updates on book with ID "+CreateBookTest.newBookId+" is present on the page");
		
		WrappedReportLogger.trace("Validated user is able to search UPDATED book with ID "+CreateBookTest.newBookId+"!!!");
	}
	
}
