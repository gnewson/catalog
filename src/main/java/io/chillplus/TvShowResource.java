package io.chillplus;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/tv")
public class TvShowResource {

	private List<TvShow> shows = new ArrayList<>();
	private Long counter = 0L;

	@Inject
	Validator validator;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<TvShow> getAll() {
		return shows;
	}

	@POST
	public Response create(TvShow show) {
		Set<ConstraintViolation<TvShow>> violations = validator.validate(show);
		if (violations.isEmpty()) {
			show.id = counter++;
			shows.add(show);
			return Response.status(201).entity(show).build();
		} else {
			return Response.status(400).build();
		}
	}
	
//	Add a getOneById method in the TvShowResource class. The method takes 
//	a tvShow’s title as parameter and return the corresponding tvShow if 
//	found or an HTTP not found error.
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOneById(@PathParam("id") Long id) {
		for (TvShow show : shows) {
			if (show.id.equals(id)) {
				return Response.status(200).entity(show).build();
			}
		}
		
		return Response.status(400).build();
	}
	
	// Add a deleteAll method in the TvShowResource class to clear the tvShows list.
	@DELETE
	public Response deleteAll() {
		shows.clear();
		
		return Response.status(200).build();
	}

// Add a deleteOne method in the TvShowResource class to remove one tvShows from 
//	the list based on its id.
	@DELETE
	@Path("/{id}")
	public Response deleteOne(@PathParam("id") Long id) {
		for (TvShow show : shows) {
			if (show.id.equals(id)) {
				shows.remove(show);
				return Response.status(200).build();
			}
		}
		
		return Response.status(400).build();
	}

}
