package com.joelsalvi.quarkus.csvmanagement.application.api;

import com.joelsalvi.quarkus.csvmanagement.domain.model.FileContentRow;
import com.joelsalvi.quarkus.csvmanagement.domain.model.FileContentRowRequest;
import com.joelsalvi.quarkus.csvmanagement.domain.usecase.FileUseCase;
import jakarta.inject.Inject;

import java.util.Comparator;
import java.util.List;

public class FileServiceCatalogApiImpl implements FileServiceCatalogApi {

    @Inject
    private FileUseCase fileUseCase;

    public Long addRow(FileContentRowRequest fileContentRowRequest) {
        return fileUseCase.addRow(fileContentRowRequest);
    }

    public void deleteRow(Long rowId) {
        fileUseCase.deleteRow(rowId);
    }

    public List<FileContentRow> getAllFileContent() {
        List<FileContentRow> allFileContent = fileUseCase.getAllFileContent();
        allFileContent.sort(Comparator.comparing(FileContentRow::getId));
        return allFileContent;
    }

    public FileContentRow getFileContentById(Long rowId) {
        return fileUseCase.getFileContentById(rowId);
    }

    public void updateRow(Long rowId, FileContentRowRequest fileContentRowRequest) {
        fileUseCase.updateRow(rowId, fileContentRowRequest);
    }

}
