package edu.sjsu.cmpe.library.api.resources;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.yammer.dropwizard.jersey.params.LongParam;

import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.dto.AuthorDto;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.dto.LinksDto;
import edu.sjsu.cmpe.library.dto.ReviewDto;

@Path("/v1/books/{isbn}/reviews")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReviewResource {
	public static HashMap<Long,Review> reviewMap = new HashMap<Long,Review>();
	public static long reviewId=1;
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createReview(Review review,@PathParam("isbn") LongParam isbn)
	{
		//review.isbn = isbn.get();
		review.setIsbn(isbn.get());
		review.setReviewId(reviewId);
		reviewMap.put(reviewId, review);
		LinksDto reviewResponse = new LinksDto();
		
		
		reviewResponse.addLink(new LinkDto("view-review", "/books/"+isbn.get()+"/review/"+reviewId,
			"GET"));
		reviewId++;
		return Response.status(201).entity(review).build();
		
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Response getReviewById(@PathParam("isbn") LongParam isbn, @PathParam("id") LongParam reviewId)
	
	{
		Review review = new Review();
		review.setIsbn(isbn.get());
		review.setReviewId(reviewId.get());
		review = reviewMap.get(reviewId.get());
		//review.setReviewId(reviewId.get());
		//review.setIsbn(isbn.get());
		//review.setRating(reviewMap.get(reviewId.get()).getRating());
		//review.setComment(reviewMap.get(reviewId.get()).getComment());
		ReviewDto reviewResponse = new ReviewDto(review);
		
		reviewResponse.addLink(new LinkDto("view-review", "/books/"+isbn.get()+"/review/"+review.getReviewId(),
			"GET"));
	
		return Response.status(200).entity(review).build();
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Response getAllReviews(@PathParam("isbn") LongParam isbn)
	{
		
		ArrayList<Review> rvw = new ArrayList<Review>();
		for(long i=1;i<=reviewMap.size();i++)
		{
			if(reviewMap.get(i).getIsbn() == isbn.get())
			{
				rvw.add(reviewMap.get(i));
			}
		}
		
		
		
		//Author auth = new Author();
		/*Review rw = new Review();
		ArrayList<Review> rws = new ArrayList<Review>();
		
		for(long i = 1 ; i<=reviewMap.size();i++)
		{
			if(reviewMap.get(i).isbn == isbn.get())
			rws.add(reviewMap.get(i));
		}*/
		
		//ArrayList reviewResponse = new ArrayList();	
		return Response.status(200).entity(rvw).build();
	}
	

}
