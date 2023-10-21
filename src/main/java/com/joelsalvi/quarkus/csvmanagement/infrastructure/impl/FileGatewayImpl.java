package com.joelsalvi.quarkus.csvmanagement.infrastructure.impl;

import com.joelsalvi.quarkus.csvmanagement.domain.converter.Converter;
import com.joelsalvi.quarkus.csvmanagement.domain.model.FileContentRow;
import com.joelsalvi.quarkus.csvmanagement.domain.model.FileContentRowRequest;
import com.joelsalvi.quarkus.csvmanagement.domain.service.FileGateway;
import io.quarkus.runtime.util.StringUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class FileGatewayImpl implements FileGateway {

    @Inject
    private CsvFileManipulation csvFileManipulation;

    private static long idCounter = 0;

    public synchronized long createID() {
        if (idCounter == 0) {
            String latestId = csvFileManipulation.getLatestId();
            idCounter = StringUtil.isNullOrEmpty(latestId) ? 1 : Long.parseLong(latestId) + 1;
        }
        return idCounter++;
    }

    @Override
    public Long addRow(FileContentRowRequest fileContentRowRequest) {
        long id = createID();
        csvFileManipulation.writeNewFileRow(new FileContentRow(id, fileContentRowRequest.getValue()));
        return id;
    }

    @Override
    public void deleteRow(Long rowId) {
        csvFileManipulation.deleteFileRow(rowId);
    }

    @Override
    public List<FileContentRow> getAllFileContent() {
        return Converter.toFileContentRowFormat(csvFileManipulation.readAllFileRows());
    }

    @Override
    public FileContentRow getFileContentById(Long rowId) {
        return Converter.toFileContentRowFormat(csvFileManipulation.readFileRowById(rowId));
    }

    @Override
    public void updateRow(Long rowId, FileContentRowRequest fileContentRowRequest) {
        csvFileManipulation.updateFileRow(rowId, fileContentRowRequest);
    }

}
