package org.book.acme.expose;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.book.acme.business.BookService;
import org.book.acme.expose.dto.BookDto;

import java.util.UUID;

@Path("/books")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {

    @Inject
    BookService bookService;

    @GET
    public Response getBooksAll() {
        return Response.ok(bookService.getBooks()).build();
    }

    @GET
    @Path("/{id}")
    public Response getBookById(@PathParam("id") UUID id) {
        return Response.ok(bookService.getBook(id)).build();
    }

    @POST
    public Response create(BookDto.Request request) {
        BookDto.Response response = bookService.createBook(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") UUID id, BookDto.Request request) {
        return Response.ok(bookService.updateBook(id, request)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id) {
        bookService.deleteBook(id);
        return Response.ok().build();
    }

}
