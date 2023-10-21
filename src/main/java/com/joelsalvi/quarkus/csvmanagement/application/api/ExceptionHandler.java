package com.joelsalvi.quarkus.csvmanagement.application.api;

import com.joelsalvi.quarkus.csvmanagement.domain.model.Error;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@ApplicationScoped
public class ExceptionHandler {

    @ServerExceptionMapper
    public RestResponse<Error> mapException(BadRequestException ex) {
        Response.Status responseStatus = Response.Status.BAD_REQUEST;
        return RestResponse.status(
                responseStatus,
                new Error(responseStatus.getStatusCode(), "Bad Request: " + ex.getLocalizedMessage()));
    }

    @ServerExceptionMapper
    public RestResponse<Error> mapException(Exception ex) {
        Response.Status responseStatus = Response.Status.INTERNAL_SERVER_ERROR;
        return RestResponse.status(
                responseStatus,
                new Error(responseStatus.getStatusCode(), "Unexpected exception: " + ex.getLocalizedMessage()));
    }

}
