package com.bookapi.testcases;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.bookapi.assertions.WrappedAssert;
import com.bookapi.endPoints.EndPoints;
import com.bookapi.logs.WrappedReportLogger;
import com.bookapi.pojo.response.Detail;
import com.bookapi.pojo.response.GetBook;
import com.bookapi.reportBuilder.ApiClient;
import com.bookapi.reportBuilder.RequestBuilder;

public class GetBookByIdTest {
	
	@Test(priority = 11, description = "Validating newly created book can be searched using ID")
	public void validatingIfUserAbleToSearchBookByID() {
		WrappedReportLogger.trace("Validating if user is able to search a book with ID "+CreateBookTest.newBookId+"...");
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("bookId", CreateBookTest.newBookId);
		
		GetBook getBook=ApiClient.get(RequestBuilder.withAuthToken(LoginForAccessTokenTest.access_token, null, pathParams), EndPoints.GET_BOOK, 200, "GetBook.json").as(GetBook.class);
		WrappedAssert.assertEquals(CreateBookTest.newBookId, getBook.getId(), "validating if "+CreateBookTest.newBookId+" is present on the page");
		
		WrappedReportLogger.trace("Validated user is able to search a book with ID "+CreateBookTest.newBookId+"!!!");
	}
	
	@Test(priority = 11, description = "NEGATIVE: Validating user unable to search a book with incorrect ID")
	public void validatingIfUserAbleToSearchIncorrectBookByID() {
		WrappedReportLogger.trace("Validating if user is able to search a book with ID "+CreateBookTest.newBookId+"...");
		
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("bookId", "12345");
		
		Detail detail=ApiClient.get(RequestBuilder.withAuthToken(LoginForAccessTokenTest.access_token, null, pathParams), EndPoints.GET_BOOK, 404, "Detail.json").as(Detail.class);
		WrappedAssert.assertEquals(detail.getDetail(), "Book not found", "validating if book is incorrect ID  is NOT present on the page");
		
		WrappedReportLogger.trace("Validated user is able to search a book with ID "+CreateBookTest.newBookId+"!!!");
	}

}
