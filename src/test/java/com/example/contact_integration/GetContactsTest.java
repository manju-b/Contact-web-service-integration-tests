package com.example.contact_integration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;

import org.junit.Test;

import static io.restassured.RestAssured.*;

public class GetContactsTest {

	@Test
	public void testGetContactsByNameWith_ValidInput() {
		when()
			.get("http://localhost:8080/contacts?searchPart=smith")
		.then()
			.statusCode(200)
			.body("firstName", hasItems("William"));
	}
	
	@Test
	public void testGetContactsByNameWith_inValidInput() {
		when()
			.get("http://localhost:8080/contacts?searchPart=ravi")
		.then()
			.statusCode(400)
			.body("message", equalTo("No matched Contacts"));
	}
	
	@Test
	public void testGetContactsByPhoneNumberWith_ValidInput() {
		when()
			.get("http://localhost:8080/contacts?searchPart=273")
		.then()
			.statusCode(200)
			.body("phoneNumber", hasItems("2738098078"));
	}
	
	@Test
	public void testGetContactsByPhoneNumberWith_inValidInput() {
		when()
			.get("http://localhost:8080/contacts?searchPart=781")
		.then()
			.statusCode(400)
			.body("message", equalTo("No matched Contacts"));
	}
	
	@Test
	public void testGetAllContacts() {
		when()
			.get("http://localhost:8080/contacts")
		.then()
			.statusCode(200)
			.body("$.size()", equalTo(5));
		
	}
	

}
