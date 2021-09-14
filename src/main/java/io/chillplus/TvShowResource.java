package io.chillplus;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/tv")
public class TvShowResource {

	@Inject
	Validator validator;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<TvShow> getAll() {
		return TvShow.listAll();
	}

	@POST
	@Transactional
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(TvShow show) {
		Set<ConstraintViolation<TvShow>> violations = validator.validate(show);
		if (show.id != null) {
			return Response.status(400).build();
		}
		else if (violations.isEmpty()) {
			show.persist();
			return Response.status(201).entity(show).build();
		} else {
			return Response.status(400).build();
		}
	}
	
    @PUT
    @Transactional
	@Consumes(MediaType.APPLICATION_JSON)
    public Response update(TvShow show) {
    	if (show.id == null) {
    		return Response.status(400).build();
    	}
    	
    	TvShow entity = TvShow.findById(show.id);
        if(entity == null) {
        	throw new WebApplicationException("Show does not exist. ", Response.Status.NOT_FOUND);
        }

        entity.title = show.title;
        entity.category = show.category;
        
        return Response.status(201).entity(entity).build();
    }
	
//	Add a getOneById method in the TvShowResource class. The method takes 
//	a tvShow’s title as parameter and return the corresponding tvShow if 
//	found or an HTTP not found error.
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public TvShow getOneById(@PathParam("id") Long id) {
		TvShow show = TvShow.findById(id);
		
		if (show == null) {
			throw new WebApplicationException("Show does not exist. ", Response.Status.NOT_FOUND);
		}
		
		return show;
	}
	
	// Add a deleteAll method in the TvShowResource class to clear the tvShows list.
	@DELETE
	@Transactional
	public Response deleteAll() {
		TvShow.deleteAll();
		
		return Response.status(200).build();
	}

// Add a deleteOne method in the TvShowResource class to remove one tvShows from 
//	the list based on its id.
	@DELETE
	@Path("/{id}")
	@Transactional
	public Response deleteOne(@PathParam("id") Long id) {
		TvShow show = TvShow.findById(id);
		if (show == null) {
			return Response.status(400).build();
		}
		show.delete();
		return Response.status(200).build();
	}

}
