package com.example.contact_integration;

import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Test;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;

public class AddContactsTest {
	

	@Test
	public void testAddContactWith_ValidInput() {
		long number = (long) Math.floor(Math.random() * 9000000000L) + 1000000000L;
		String myJson = "{\"firstName\":\"micheal\", \"lastName\":\"jackson\", \"emailId\":\"jack@gmail.com\", \"phoneNumber\":\""+number+"\"}";
		given()
			.contentType(ContentType.JSON)
			.body(myJson)
		.when()
			.post("http://localhost:8080/contacts")
		.then()
			.log()
			.body()
			.statusCode(200)
			.body("firstName", equalTo("micheal"));
	}
	
	@Test
	public void testAddContactWhen_FisrtNameIsNotGiven() {
		long number = (long) Math.floor(Math.random() * 9000000000L) + 1000000000L;
		String myJson = "{\"firstName\":\"\", \"lastName\":\"jackson\", \"emailId\":\"jack@gmail.com\", \"phoneNumber\":\""+number+"\"}";
		given()
			.contentType(ContentType.JSON)
			.body(myJson)
		.when()
			.post("http://localhost:8080/contacts")
		.then()
			.statusCode(400)
			.body("message", equalTo("Contact will not be saved with out firstname and phonenumber"));
	}
	
	@Test
	public void testAddContactWhen_PhoneNumberIsNotGiven() {
		String myJson = "{\"firstName\":\"micheal\", \"lastName\":\"jackson\", \"emailId\":\"jack@gmail.com\", \"phoneNumber\":\"\"}";
		given()
			.contentType(ContentType.JSON)
			.body(myJson)
		.when()
			.post("http://localhost:8080/contacts")
		.then()
			.statusCode(400)
			.body("message", equalTo("Contact will not be saved with out firstname and phonenumber"));
	}
	
	//failed
	@Test
	public void testAddContactWhen_LastNameIsNotGiven() {
		long number = (long) Math.floor(Math.random() * 9000000000L) + 1000000000L;
		String myJson = "{\"firstName\":\"micheal\", \"lastName\":\"\", \"emailId\":\"jack@gmail.com\", \"phoneNumber\":\""+number+"\"}";
		given()
			.contentType(ContentType.JSON)
			.body(myJson)
		.when()
			.post("http://localhost:8080/contacts")
		.then()
			.statusCode(200)
			.body("lastName", equalTo(""))
			.body("phoneNumber", equalTo(""+number));
	}
	
	@Test
	public void testAddContactWith_PhoneNumberNotEqualTo10Digits() {
		String myJson = "{\"firstName\":\"micheal\", \"lastName\":\"jackson\", \"emailId\":\"jack@gmail.com\", \"phoneNumber\":\"111111111\"}";
		given()
			.contentType(ContentType.JSON)
			.body(myJson)
		.when()
			.post("http://localhost:8080/contacts")
		.then()
			.statusCode(400)
			.body("message", equalTo("Please enter a 10 digit phonenumber"));
	}
	
	@Test
	public void testAddContactWith_PhoneNumberContainsAlphabeticsAndNumerics() {
		String myJson = "{\"firstName\":\"micheal\", \"lastName\":\"jackson\", \"emailId\":\"jack@gmail.com\", \"phoneNumber\":\"111111111a\"}";
		given()
			.contentType(ContentType.JSON)
			.body(myJson)
		.when()
			.post("http://localhost:8080/contacts")
		.then()
			.statusCode(400)
			.body("message", equalTo("Please enter a 10 digit phonenumber"));
	}
}
