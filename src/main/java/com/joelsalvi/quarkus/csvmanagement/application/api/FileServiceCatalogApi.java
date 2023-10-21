package com.joelsalvi.quarkus.csvmanagement.application.api;

import com.joelsalvi.quarkus.csvmanagement.domain.model.FileContentRow;
import com.joelsalvi.quarkus.csvmanagement.domain.model.FileContentRowRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/catalog")
@Tag(name = "FileServiceCatalog")
public interface FileServiceCatalogApi {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Create a new row in the File",
            description = "Add new row in the File",
            operationId = "addRow"
    )
    Long addRow(@RequestBody(required = true) FileContentRowRequest fileContentRowRequest);

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Delete an existent row in the file",
            description = "Delete a row from file content",
            operationId = "deleteRow"
    )
    void deleteRow(@PathParam("id") Long rowId);

    @GET
    @Operation(
            summary = "Get all file content",
            description = "Returns all file content",
            operationId = "getAllFileContent"
    )
    List<FileContentRow> getAllFileContent();

    @GET
    @Path("/{id}")
    @Operation(
            summary = "Get a row from file content",
            description = "Returns a row from file content",
            operationId = "getFileContentById"
    )
    FileContentRow getFileContentById(@PathParam("id") Long rowId);

    @PUT
    @Path("/{id}")
    @Operation(
            summary = "Update an existent row in the file",
            description = "Returns a row from file content",
            operationId = "updateRow"
    )
    void updateRow(@PathParam("id") Long rowId, @RequestBody(required = true) FileContentRowRequest fileContentRowRequest);
}
