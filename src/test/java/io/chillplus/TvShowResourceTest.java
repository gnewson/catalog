package io.chillplus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;

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
	void getAllTvShowsOrderByTitle() {
		
		TvShow show1 = new TvShow();
		show1.title = "BB";
		
		TvShow show2 = new TvShow();
		show2.title = "AA";
		
		
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
		
		List<TvShow> shows =
		given()
		.when().get("/api/tv")
		.then()
		.statusCode(200)
		.contentType(ContentType.JSON)
		.body("$.size()", is(2))
		.extract()
		.jsonPath()
		.getList("", TvShow.class);

		System.out.print(shows);
		
		assertThat("List is in order", shows.get(0).title, equalTo("AA"));
		assertThat("List is in order", shows.get(1).title, equalTo("BB"));

	}
	
	@Test
	void getAllTvShowsByCategory() {
		
		List<TvShow> shows = new ArrayList<TvShow>();
		
		TvShow show1 = new TvShow();
		show1.title = "BB";
		show1.category = "news";
		shows.add(show1);
		
		TvShow show2 = new TvShow();
		show2.title = "AA";
		show2.category = "NEWS";
		shows.add(show2);
		
		TvShow show3 = new TvShow();
		show3.title = "CC";
		show3.category = "nEws";
		shows.add(show3);
		
		TvShow show4 = new TvShow();
		show4.title = "DD";
		show4.category = "NeWS";
		shows.add(show4);

		TvShow show5 = new TvShow();
		show5.title = "ee";
		show5.category = "newS";
		shows.add(show5);

		TvShow show6 = new TvShow();
		show6.title = "ff";
		show6.category = "NEwS";
		shows.add(show6);

		TvShow show7 = new TvShow();
		show7.title = "gg";
		show7.category = "nEWs";
		shows.add(show7);

		TvShow show8 = new TvShow();
		show8.title = "hh";
		show8.category = "NeWs";
		shows.add(show8);
	
		for(TvShow show : shows) {
			
			given()
			.contentType(ContentType.JSON)
			.body(show)
			.when().post("/api/tv")
			.then()
			.statusCode(201);
		}

		
		given()
		.when().get("/api/tv/categories/news")
		.then()
		.statusCode(200)
		.contentType(ContentType.JSON)
		.body("$.size()", is(8));
		
		given()
		.when().get("/api/tv/categories/news?index=0&size=4")
		.then()
		.statusCode(200)
		.contentType(ContentType.JSON)
		.body("$.size()", is(4));
		
		given()
		.when().get("/api/tv/categories/news?index=1&size=4")
		.then()
		.statusCode(200)
		.contentType(ContentType.JSON)
		.body("$.size()", is(4));

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
	void updateTvShow() {
		
		TvShow show = new TvShow();
		show.title = "News";
		
		TvShow returnedShow =
		given()
		.contentType(ContentType.JSON)
		.body(show)
		.when().post("/api/tv")
		.then()
		.statusCode(201)
		.body("title", is(show.title))
		.extract()
		.as(TvShow.class);
			
		returnedShow.title = "Daily News";
		
		given()
		.when()
		.contentType(ContentType.JSON)
		.body(returnedShow)
		.put("/api/tv")
		.then()
		.statusCode(201)
		.contentType(ContentType.JSON)
		.body("title", is(returnedShow.title));
		
		TvShow newShow = new TvShow();
		newShow.title = "Nightly News";
		newShow.category = "News";
		
		given()
		.contentType(ContentType.JSON)
		.body(newShow)
		.when().put("/api/tv")
		.then()
		.statusCode(400);

	}
	
	@Test
	void getOneTvShow() {
		TvShow show1 = new TvShow();
		show1.title = "News";	
		
		TvShow returnedShow =
		given()
		.contentType(ContentType.JSON)
		.body(show1)
		.when().post("/api/tv")
		.then()
		.statusCode(201)
		.extract()
		.as(TvShow.class);
			
		given()
		.when()
		.get("/api/tv")
		.then()
		.statusCode(200)
		.contentType(ContentType.JSON)
		.body("$.size()", is(1));
		
		given()
		.when().get("/api/tv/{id}", returnedShow.id)
		.then()
		.statusCode(200)
		.body("title", is(show1.title));
	}
	
	@Test
	void getTvShowByTitle() {
		TvShow show1 = new TvShow();
		show1.title = "news";	
		
		given()
		.contentType(ContentType.JSON)
		.body(show1)
		.when().post("/api/tv")
		.then()
		.statusCode(201)
		.body("title", is(show1.title));
			
		given()
		.when()
		.get("/api/tv/search/{title}", show1.title)
		.then()
		.statusCode(200)
		.contentType(ContentType.JSON)
		.body("title", is(show1.title));
		
		given()
		.when().get("/api/tv/search/{title}", "eastenders")
		.then()
		.statusCode(404);
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
		.when().get("/api/tv/{id}", "100")
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
		.delete("/api/tv/{id}",1)
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
