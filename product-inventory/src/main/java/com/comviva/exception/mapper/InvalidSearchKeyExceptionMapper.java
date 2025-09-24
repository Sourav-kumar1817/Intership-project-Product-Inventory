package com.comviva.exception.mapper;

import com.comviva.exception.InvalidSearchKeyException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

@Provider
public class InvalidSearchKeyExceptionMapper implements ExceptionMapper<InvalidSearchKeyException> {

    @Override
    public Response toResponse(InvalidSearchKeyException exception) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", 400);
        error.put("error", "Bad Request");
        error.put("message", exception.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
    }
}
