package edu.sjsu.cmpe.library.api.resources;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
import edu.sjsu.cmpe.library.dto.ReviewDto;

@Path("/v1/books/{isbn}/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {
	//public static long authorId=1;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Response getAllAuthors(@PathParam("isbn") LongParam isbn)
	
	{
		ArrayList<Author> auth = new ArrayList<Author>();
		System.out.println(BookResource.authorMap.size());
		for(long i=1;i<=BookResource.authorMap.size();i++)
		{
			if(BookResource.authorMap.get(i).getIsbn() == isbn.get())
			{
				auth.add(BookResource.authorMap.get(i));
			}
		}
		
		//author.setAuthorId(authorId);
		//author.setAuthors(BookResource.bookMap.get(authorId).getAuthors());
		//AuthorDto authorResponse = new AuthorDto(auth);
		//authorResponse.addLink(new LinkDto("view-author", "/books/"+isbn.get()+"/authors/"+isbn.get(),
			//"GET"));
	
		return Response.status(200).entity(auth).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Response getAuthorsById(@PathParam("isbn") LongParam isbn, @PathParam("id") LongParam authorId)
	
	{
		Author author = new Author();
		author.setIsbn(isbn.get());
		author = BookResource.authorMap.get(authorId.get());
		
		//author.setName(BookResource.authorMap.get(authorId.get()).getName());
		//ArrayList<Author> aws = new ArrayList<Author>();
		
		/*for(long i = 1 ; i<=BookResource.authorMap.size();i++)
		{
			if(BookResource.authorMap.get(i).isbn == isbn.get())
			aws.add(BookResource.authorMap.get(i));
		}*/
	
		AuthorDto authorResponse = new AuthorDto(author);
		
		authorResponse.addLink(new LinkDto("view-review", "/books/"+isbn.get()+"/review/"+authorId.get(),
			"GET"));
	
		return Response.status(200).entity(author).build();
	}
	
	
	

}
