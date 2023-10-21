package com.joelsalvi.quarkus.csvmanagement.domain.usecase;

import com.joelsalvi.quarkus.csvmanagement.domain.model.FileContentRow;
import com.joelsalvi.quarkus.csvmanagement.domain.model.FileContentRowRequest;
import com.joelsalvi.quarkus.csvmanagement.domain.service.FileGateway;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class FileUseCase {

    @Inject
    private FileGateway fileGateway;

    public Long addRow(FileContentRowRequest fileContentRowRequest) {
        return fileGateway.addRow(fileContentRowRequest);
    }

    public void deleteRow(Long rowId) {
        fileGateway.deleteRow(rowId);
    }

    public List<FileContentRow> getAllFileContent() {
        return fileGateway.getAllFileContent();
    }

    public FileContentRow getFileContentById(Long rowId) {
        return fileGateway.getFileContentById(rowId);
    }

    public void updateRow(Long rowId, FileContentRowRequest fileContentRowRequest) {
        fileGateway.updateRow(rowId, fileContentRowRequest);
    }
}
