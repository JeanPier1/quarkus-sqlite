package org.book.acme.utils;

import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class EntityNotFoundExceptionMapper implements ExceptionMapper<EntityNotFoundException> {

    @Override
    public Response toResponse(EntityNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorResponse(404, exception.getMessage()))
                .build();
    }

    public static class ErrorResponse {
        public int status;
        public String message;
        public ErrorResponse(int status, String message) {
            this.status = status;
            this.message = message;
        }
    }
}
