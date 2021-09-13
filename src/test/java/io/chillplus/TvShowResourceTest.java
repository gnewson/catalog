package io.chillplus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class TvShowResourceTest {
	
	@BeforeEach
	public void beforeEach() {
		given().delete("/api/tv");
	}

	@Test
	void getAllTvShows() {
		
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
	
	@Test
	void checkTvShowTitleIsNotBlank() {
		
		TvShow show = new TvShow();
		show.category = "News";
		given()
		.contentType(ContentType.JSON)
		.body(show)
		.when().post("/api/tv")
		.then()
		.statusCode(400);
	}
	
	@Test
	void createTvShow() {
		
		TvShow show = new TvShow();
		show.title = "News";

		given()
		.contentType(ContentType.JSON)
		.body(show)
		.when().post("/api/tv")
		.then()
		.statusCode(201)
		.body("title", is(show.title));
				
		given()
		.when()
		.get("/api/tv")
		.then()
		.statusCode(200)
		.contentType(ContentType.JSON)
		.body("$.size()", is(1));
		
		show.id = 10L;
		
		given()
		.contentType(ContentType.JSON)
		.body(show)
		.when().post("/api/tv")
		.then()
		.statusCode(400);

	}

	@Test
	void getOneTvShow() {
		TvShow show1 = new TvShow();
		show1.title = "News";	
		
		given()
		.contentType(ContentType.JSON)
		.body(show1)
		.when().post("/api/tv")
		.then()
		.statusCode(201);
		
		given()
		.when()
		.get("/api/tv")
		.then()
		.statusCode(200)
		.contentType(ContentType.JSON)
		.body("$.size()", is(1));
		
		given()
		.when().get("/api/tv/{id}", "0")
		.then()
		.statusCode(200)
		.body("title", is(show1.title));
	}

	@Test
	void getNonExistingTvShow() {
		TvShow show1 = new TvShow();
		show1.title = "News";	
		
		given()
		.contentType(ContentType.JSON)
		.body(show1)
		.when().post("/api/tv")
		.then()
		.statusCode(201);
		
		given()
		.when()
		.get("/api/tv")
		.then()
		.statusCode(200)
		.contentType(ContentType.JSON)
		.body("$.size()", is(1));
		
		given()
		.when().get("/api/tv/{id}", "1")
		.then()
		.statusCode(404);
	}

	@Test
	void deleteAllTvShows() {
		
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

		given()
		.when()
		.delete("/api/tv")
		.then()
		.statusCode(200);
				
		given()
		.when().get("/api/tv")
		.then()
		.statusCode(200)
		.contentType(ContentType.JSON)
		.body("$.size()", is(0));

	}
	
	@Test
	void deleteOneTvShow() {
		
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

		given()
		.when()
		.delete("/api/tv/{id}",0)
		.then()
		.statusCode(200);
				
		given()
		.when().get("/api/tv")
		.then()
		.statusCode(200)
		.contentType(ContentType.JSON)
		.body("$.size()", is(1));

	}
	
}
