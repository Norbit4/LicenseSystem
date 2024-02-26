package pl.norbit.backend.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import pl.norbit.backend.exception.model.ExcelDataException;
import pl.norbit.backend.model.license.License;
import pl.norbit.backend.model.token.Token;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ExcelService {

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

        createRow(sheet, 0, hStyle, "ID", "OWNER", "KEY");

        for (int i = 0; i < licenses.size(); i++) {
            License license = licenses.get(i);

            String[] data = {
                    license.getId().toString(),
                    license.getOwner(),
                    license.getLicenseKey()
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
        try {
            var workbook = new XSSFWorkbook();

            CellStyle headerStyle = getHeatherStyle(workbook);
            CellStyle valuesStyle = getValuesStyle(workbook);

            createLicenseSheet(workbook, headerStyle, valuesStyle, licenses);
            createTokenSheet(workbook, headerStyle, valuesStyle, tokens);

            var bos = new ByteArrayOutputStream();

            workbook.write(bos);
            byte[] byteArray = bos.toByteArray();

//            try (bos) {
//                workbook.write(bos);
//            }
            bos.close();
            return byteArray;
        } catch (Exception e) {
            throw new ExcelDataException("Error while creating excel file");
        }
    }
}
