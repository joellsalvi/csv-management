package com.joelsalvi.quarkus.csvmanagement.infrastructure.impl;

import com.joelsalvi.quarkus.csvmanagement.domain.model.FileContentRow;
import com.joelsalvi.quarkus.csvmanagement.domain.model.FileContentRowRequest;
import com.joelsalvi.quarkus.csvmanagement.infrastructure.exception.CsvFileReadException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.BadRequestException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class CsvFileManipulation {

    private static final String FILE_PATH = "src/main/resources/DatabaseFile.csv";

    public static final String CSV_DELIMITER = CSVFormat.DEFAULT.getDelimiterString();

    public synchronized String getLatestId() {
        List<CSVRecord> records = getCsvFile().getRecords();
        if (records.isEmpty()) {
            return null;
        }
        Collections.reverse(records);
        return extractId(records.get(0));
    }

    public List<String[]> readAllFileRows() {
        List<String[]> rows = new ArrayList<>();
        for (CSVRecord csvRecord : getCsvFile()) {
            rows.add(csvRecord.values());
        }
        return rows;
    }

    public String[] readFileRowById(Long rowId) {
        try (CSVParser parser = getCsvFile()) {

            return getRowByIdOrThrow(rowId, parser.getRecords())
                    .values();

        } catch (IOException e) {
            throw new CsvFileReadException(e.getLocalizedMessage());
        }
    }

    public void writeNewFileRow(FileContentRow fileContentRow) {
        try (CSVPrinter printer = new CSVPrinter(new FileWriter(FILE_PATH, true), CSVFormat.DEFAULT)) {
            printer.printRecord(fileContentRow.getId(), fileContentRow.getValue());
        } catch (IOException e) {
            throw new CsvFileReadException(e.getLocalizedMessage());
        }
    }

    public void updateFileRow(Long rowId, FileContentRowRequest fileContentRowRequest) {
        List<String[]> newRecords = new ArrayList<>();
        try (CSVParser parser = getCsvFile()) {
            List<CSVRecord> csvRecords = parser.getRecords();

            CSVRecord csvRecordToBeUpdated = getRowByIdOrThrow(rowId, csvRecords);

            for (CSVRecord csvRecord : csvRecords) {
                if (csvRecord.getRecordNumber() == csvRecordToBeUpdated.getRecordNumber()) {
                    newRecords.add(new String[]{extractId(csvRecordToBeUpdated), fileContentRowRequest.getValue()});
                } else {
                    newRecords.add(csvRecord.values());
                }
            }

        } catch (IOException e) {
            throw new CsvFileReadException(e.getLocalizedMessage());
        }

        reWriteAllFileElements(newRecords);
    }

    public void deleteFileRow(Long rowId) {
        List<CSVRecord> newRecords;
        try (CSVParser parser = getCsvFile()) {

            List<CSVRecord> csvRecords = parser.getRecords();

            CSVRecord csvRecordToBeRemoved = getRowByIdOrThrow(rowId, csvRecords);

            csvRecords.remove(csvRecordToBeRemoved);

            newRecords = csvRecords;

        } catch (IOException e) {
            throw new CsvFileReadException(e.getLocalizedMessage());
        }

        reWriteAllCsvRecords(newRecords);
    }

    private CSVParser getCsvFile() {
        try {
            return new CSVParser(new FileReader(FILE_PATH), CSVFormat.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CsvFileReadException(e.getLocalizedMessage());
        }
    }

    private CSVRecord getRowByIdOrThrow(Long rowId, List<CSVRecord> csvRecords) {
        return csvRecords.stream()
                .filter(r -> extractId(r).equals(rowId.toString()))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("The rowId doesn't exists: " + rowId));
    }

    private void reWriteAllCsvRecords(List<CSVRecord> csvRecords) {
        List<String[]> newRecords = new ArrayList<>();
        for (CSVRecord csvRecord : csvRecords) {
            newRecords.add(new String[]{extractId(csvRecord), extractValue(csvRecord)});
        }
        reWriteAllFileElements(newRecords);
    }

    private String extractValue(CSVRecord csvRecord) {
        return csvRecord.values()[1];
    }

    private void reWriteAllFileElements(List<String[]> newRecords) {
        try (CSVPrinter printer = new CSVPrinter(new FileWriter(FILE_PATH), CSVFormat.DEFAULT)) {

            for (String[] newRecord : newRecords) {
                printer.printRecord(newRecord[0], newRecord[1]);
            }

        } catch (IOException e) {
            throw new CsvFileReadException(e.getLocalizedMessage());
        }
    }

    private String extractId(CSVRecord csvRecord) {
        return csvRecord.values()[0];
    }

}
