package com.example.contact_integration;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Test;

public class DeleteContactTest {

	@Test
	public void testDeleteContactWith_AnExistingRecord() {
		when()
			.delete("http://localhost:8080/contacts/2")
		.then()
			.statusCode(200);
			
	}
	
	@Test
	public void testDeleteContactWith_NonExistingRecord() {
		when()
			.delete("http://localhost:8080/contacts/8")
		.then()
			.statusCode(400)
			.body("message", equalTo("Record you are trying to delete does not exist in database"));
			
	}
}
