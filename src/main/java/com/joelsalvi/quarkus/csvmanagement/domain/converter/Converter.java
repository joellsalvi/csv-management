package com.joelsalvi.quarkus.csvmanagement.domain.converter;

import com.joelsalvi.quarkus.csvmanagement.domain.model.FileContentRow;

import java.util.ArrayList;
import java.util.List;

import static com.joelsalvi.quarkus.csvmanagement.infrastructure.impl.CsvFileManipulation.CSV_DELIMITER;

public final class Converter {

    private Converter() {}

    public static String toCsvFormat(FileContentRow fileContentRow) {
        return fileContentRow.getId() + CSV_DELIMITER + fileContentRow.getValue();
    }

    public static FileContentRow toFileContentRowFormat(String[] row) {
        final Long id = Long.valueOf(row[0]);
        final String value = row[1];
        return new FileContentRow(id, value);
    }

    public static List<FileContentRow> toFileContentRowFormat(List<String[]> rows) {
        var fileContentRows = new ArrayList<FileContentRow>();

        rows.forEach(row -> fileContentRows.add(toFileContentRowFormat(row)));

        return fileContentRows;
    }
}
