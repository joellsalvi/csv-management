package com.joelsalvi.quarkus.csvmanagement.domain.service;

import com.joelsalvi.quarkus.csvmanagement.domain.model.FileContentRow;
import com.joelsalvi.quarkus.csvmanagement.domain.model.FileContentRowRequest;

import java.util.List;

public interface FileGateway {

    Long addRow(FileContentRowRequest fileContentRowRequest);

    void deleteRow(Long rowId);

    List<FileContentRow> getAllFileContent();

    FileContentRow getFileContentById(Long rowId);

    void updateRow(Long rowId, FileContentRowRequest fileContentRowRequest);

}
