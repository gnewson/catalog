package io.chillplus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.DisabledOnIntegrationTest;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.http.ContentType;

@QuarkusIntegrationTest
public class TvShowResourceNativeIT extends TvShowResourceTest {

	@Test
	void getAllTvShowsNative() {
		
		TvShow show1 = new TvShow();
		show1.title = "News";
		
		TvShow show2 = new TvShow();
		show2.title = "News2";
		
		
		given()
		.contentType(ContentType.JSON)
		.body(show1)
		.when().post("/api/tv")
		.then()
		.statusCode(201);
		
		given()
		.contentType(ContentType.JSON)
		.body(show2)
		.when().post("/api/tv")
		.then()
		.statusCode(201);
		
		given()
		.when().get("/api/tv")
		.then()
		.statusCode(200)
		.contentType(ContentType.JSON)
		.body("$.size()", is(2));

	}
	
	  @DisabledOnIntegrationTest
	  @Test
	  public void checkTvShowTitleIsNotBlank(){}
	
}
