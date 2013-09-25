package edu.sjsu.cmpe.library.api.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.RequestWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.dto.BookDto;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.dto.LinksDto;
@Path("/v1/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)


public class BookResource {

	public static long isbnId=1;
	public static long authorId=1;
	public static HashMap<Long,Book> bookMap = new HashMap<Long,Book>();
	public static HashMap<Long,Author> authorMap = new HashMap<Long,Author>();
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	
    public Response createBook(Book book)
	{
		LinksDto linkResponse = new LinksDto();
		bookMap.put(isbnId,book);
		
		ArrayList<Author> a = new ArrayList<Author>();
		a = book.getAuthors();
		
		for(int i = 0;i<a.size();i++)
		{
			Author auth = (Author)a.get(i);

			auth.setAuthorId(authorId);
			auth.setIsbn(isbnId);
			
			authorMap.put(authorId, auth);
			authorId++;
		}
		
	/*	Author author = new Author();
		
		author.setIsbn(isbnId);
		author.setAuthorId(authorId);
		
		
		author.setAuthors(bookMap.get(isbnId).getAuthors());
		
		authorMap.put(authorId, author);*/
		
		linkResponse.addLink(new LinkDto("view-book", "/books/"+isbnId, "GET"));
		linkResponse.addLink(new LinkDto("delete-book", "/books/"+isbnId, "DELETE"));
		linkResponse.addLink(new LinkDto("update-book", "/books/"+isbnId, "PUT"));
		linkResponse.addLink(new LinkDto("create-review", "/books/"+isbnId+"/reviews", "POST"));
		isbnId++;
		return Response.status(201).entity(linkResponse).build();
			
		
	}
	
	    
    @GET
    @Path("/{isbn}")
    @Timed(name = "view-book")
    public Response getBookByIsbn(@PathParam("isbn") LongParam isbn) {
    
    Book book = new Book();
    book = bookMap.get(isbn.get());
    book.setIsbn(isbn.get());
    /*System.out.println();
    book.setIsbn(isbn.get());
    book.setTitle(bookMap.get(isbn.get()).getTitle());
    book.setLanguage(bookMap.get(isbn.get()).getLanguage());
    book.setPages(bookMap.get(isbn.get()).getPages());
    book.setPublicationDate(bookMap.get(isbn.get()).getPublicationDate());
    book.setStatus(bookMap.get(isbn.get()).getStatus());
    //book.setAuthors(bookMap.get(isbn.get()).getAuthors());*/
	BookDto bookResponse = new BookDto(book);
	
	
	
	bookResponse.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(),"GET"));
	bookResponse.addLink(new LinkDto("update-book","/books/" + book.getIsbn(), "PUT"));
	bookResponse.addLink(new LinkDto("delete-book","/books/" + book.getIsbn(), "DELETE"));
	bookResponse.addLink(new LinkDto("create-review","/books/" + book.getIsbn()+"/reviews", "POST"));
	bookResponse.addLink(new LinkDto("view-all-reviews","/books/" + book.getIsbn()+"/reviews", "GET"));
	return Response.status(200).entity(bookResponse).build();
	
    }
    
    @DELETE
    @Path("/{isbn}")
    @Timed(name = "delete-book")
    public Response deleteBookByIsbn(@PathParam("isbn") LongParam isbn)
    {
    	Book book = new Book();
    	LinksDto linkResponse = new LinksDto();
    	bookMap.remove(isbn.get());
    	linkResponse.addLink(new LinkDto("create-book","/books/", "POST"));
    	isbnId--;
    	return Response.status(200).entity(linkResponse).build();
    }
    
    @PUT
    @Path("/{isbn}")
    @Timed(name = "update-book")
    public Response updateBookByIsbn(@PathParam("isbn") LongParam isbn,@QueryParam("status") String status)
    {
    	Book book = new Book();
    	book = bookMap.get(isbn.get());
    	book.setStatus(status);
    	bookMap.put(isbn.get(), book);
    	//bookMap.put(isbn.get(), book);
    	LinksDto bookResponse = new LinksDto();
    	bookResponse.addLink(new LinkDto("view-book","/books/"+isbn.get(), "GET"));
    	bookResponse.addLink(new LinkDto("update-book","/books/"+isbn.get(), "PUT"));
    	bookResponse.addLink(new LinkDto("delete-book","/books/"+isbn.get(), "DELETE"));
    	bookResponse.addLink(new LinkDto("create-book","/books/"+isbn.get(), "POST"));
    	bookResponse.addLink(new LinkDto("view-all-reviews","/books/"+isbn.get(), "GET"));
    	return Response.status(200).entity(bookResponse).build();
    }
    
}

