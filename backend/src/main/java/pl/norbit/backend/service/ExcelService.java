package pl.norbit.backend.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import pl.norbit.backend.exception.model.ExcelDataException;
import pl.norbit.backend.model.license.License;
import pl.norbit.backend.model.token.Token;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ExcelService {

    private String convertTime(long time){
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm - dd.MM.yyyy");
        return sdf.format(new Date(time));
    }

    private CellStyle getHeatherStyle(Workbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIME.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);
        return headerStyle;
    }
    private CellStyle getValuesStyle(Workbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        return headerStyle;
    }

    private void createRow(Sheet sheet, int rowIndex, CellStyle cellStyle, String... data){
        Row row = sheet.createRow(rowIndex);

        for (int i = 0; i < data.length; i++) {
            Cell headerCell = row.createCell(i);
            headerCell.setCellValue(data[i]);
            if(cellStyle != null) headerCell.setCellStyle(cellStyle);
        }
    }

    private void createLicenseSheet(Workbook workbook, CellStyle hStyle, CellStyle vStyle, List<License> licenses){
        Sheet sheet = workbook.createSheet("LICENSES");

        String[] tittles = {"ID", "OWNER", "KEY", "CREATION DATE", "EXPIRATION DATE", "TYPE", "DESCRIPTION"};

        createRow(sheet, 0, hStyle, tittles);

        for (int i = 0; i < licenses.size(); i++) {
            License license = licenses.get(i);
            String expirationDate = license.getExpirationDate() == 0 ? "NEVER" : convertTime(license.getExpirationDate());

            String[] data = {
                    license.getId().toString(),
                    license.getOwner(),
                    license.getLicenseKey(),
                    convertTime(license.getCreationDate()),
                    expirationDate,
                    license.getLicenseType().toString(),
                    license.getDescription()
            };
            createRow(sheet, i + 1, vStyle, data);
        }
    }

    private void createTokenSheet(Workbook workbook, CellStyle hStyle, CellStyle vStyle, List<Token> tokens){
        Sheet sheet = workbook.createSheet("TOKENS");

        createRow(sheet, 0, hStyle, "ID", "TOKEN TYPE", "TOKEN");

        for (int i = 0; i < tokens.size(); i++) {
            Token license = tokens.get(i);

            String[] data = {
                    license.getId().toString(),
                    license.getTokenType().toString(),
                    license.getAccessToken()
            };
            createRow(sheet, i + 1, vStyle, data);
        }
    }

    public byte[] getExcelFile(List<License> licenses, List<Token> tokens) {
        var workbook = new XSSFWorkbook();

        CellStyle headerStyle = getHeatherStyle(workbook);
        CellStyle valuesStyle = getValuesStyle(workbook);

        createLicenseSheet(workbook, headerStyle, valuesStyle, licenses);
        createTokenSheet(workbook, headerStyle, valuesStyle, tokens);

        try (var bos = new ByteArrayOutputStream()) {
            workbook.write(bos);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new ExcelDataException("Error while creating excel file");
        }
    }
}
