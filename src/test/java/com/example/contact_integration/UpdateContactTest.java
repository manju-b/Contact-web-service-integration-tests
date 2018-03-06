package com.example.contact_integration;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import io.restassured.http.ContentType;


public class UpdateContactTest {
	
	@Before
	public void setUp() {
		when()
		.get("http://localhost:8080/contacts?searchPart=smith")
	.then()
		.statusCode(200)
		.body("lastName", hasItems("Smith"));
	}

	@Ignore
	public void testUpdatingAContactWith_ValidInput() {
		String myJson = "{\"firstName\":\"william\", \"lastName\":\"Smith\", \"emailId\":\"jack@gmail.com\", \"phoneNumber\":\"0000000000\"}";
		given()
			.contentType(ContentType.JSON)
			.body(myJson)
		.when()
			.put("http://localhost:8080/contacts/1")
		.then()
			.statusCode(200)
			.body("phoneNumber", equalTo("0000000000"));
	}
	
	@Ignore
	public void testUpdatingAContactWhen_PhoneNumberIsNotGiven() {
		String myJson = "{\"firstName\":\"william\", \"lastName\":\"Smith\", \"emailId\":\"jack@gmail.com\", \"phoneNumber\":\"\"}";
		given()
			.contentType(ContentType.JSON)
			.body(myJson)
		.when()
			.put("http://localhost:8080/contacts/1")
		.then()
			.statusCode(400)
			.body("message", equalTo("Contact will not be saved with out firstname and phonenumber"));
	}
	
	@Ignore
	public void testUpdatingAContactWith_InvalidPhoneNumberWhichDoesNotContain10Digits() {
		String myJson = "{\"firstName\":\"william\", \"lastName\":\"Smith\", \"emailId\":\"jack@gmail.com\", \"phoneNumber\":\"999999\"}";
		given()
			.contentType(ContentType.JSON)
			.body(myJson)
		.when()
			.put("http://localhost:8080/contacts/1")
		.then()
			.statusCode(400)
			.body("message", equalTo("Please enter a 10 digit phonenumber"));
	}
	
	@Test
	public void testUpdatingAContactWith_InvalidPhoneNumberContainingAlphabetsAndNumerics() {
		String myJson = "{\"firstName\":\"william\", \"lastName\":\"Smith\", \"emailId\":\"jack@gmail.com\", \"phoneNumber\":\"999ytr999\"}";
		given()
			.contentType(ContentType.JSON)
			.body(myJson)
		.when()
			.put("http://localhost:8080/contacts/1")
		.then()
			.statusCode(400)
			.body("message", equalTo("Please enter a 10 digit phonenumber"));
	}
}
