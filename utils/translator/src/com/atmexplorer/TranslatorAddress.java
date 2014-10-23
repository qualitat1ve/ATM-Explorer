package com.atmexplorer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gtranslate.Language;
import com.gtranslate.Translator;

public class TranslatorAddress {

    private static final String NOT_FOUND_RU = "нет данных";
    private static final String NOT_FOUND_UA = "немає даних";
    private static final String NOT_FOUND_EN = "no information";
    
    private Translator mTranslate = Translator.getInstance();

    public void translate() throws IOException {
        XSSFWorkbook workbook = null;
        FileInputStream file = new FileInputStream(new File(Constants.IN_ATMS_FILE_NAME));

        // Create Workbook instance holding reference to .xlsx file
        workbook = new XSSFWorkbook(file);

        // Get first/desired sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(0);

        // Iterate through each rows one by one
        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next();

        int counter = 0;

        while (rowIterator.hasNext()) {
            counter++;
            System.out.println(counter);
            Row row = rowIterator.next();

            String originValue = row.getCell(3).getStringCellValue();

            if (counter <= 2871) { // 2871
                // address
                String resultUa = translateAddress(originValue, Language.RUSSIAN, Language.UKRAINIAN);
                String resultEng = translateAddress(originValue, Language.RUSSIAN, Language.ENGLISH);

                // System.out.println(counter + " " + originValue + "\t" +
                // result + "\t" + resultEng);
                Cell cell = row.createCell(4);
                cell.setCellValue(resultUa);
                Cell cellEn = row.createCell(5);
                cellEn.setCellValue(resultEng);

                // Translate time
                String originValueTime = row.getCell(6).getStringCellValue();
                String resultValueTimeUa = translateSimple(originValueTime, Language.RUSSIAN, Language.UKRAINIAN);
                String resultValueTimeEn = translateSimple(originValueTime, Language.RUSSIAN, Language.ENGLISH);
                Cell cellTimeUa = row.createCell(7);
                cellTimeUa.setCellValue(resultValueTimeUa);
                Cell cellTimeEn = row.createCell(8);
                cellTimeEn.setCellValue(resultValueTimeEn);

                // Translate position
                String originValuePosition = row.getCell(9).getStringCellValue();
                String resultValuePosUa = translateSimple(originValuePosition, Language.RUSSIAN, Language.UKRAINIAN);
                String resultValuePosEn = translateSimple(originValuePosition, Language.RUSSIAN, Language.ENGLISH);

                Cell cellPosUa = row.createCell(10);
                cellPosUa.setCellValue(resultValuePosUa);

                Cell cellPosEn = row.createCell(11);
                cellPosEn.setCellValue(resultValuePosEn);

                // Translate type
                String originValueType = row.getCell(12).getStringCellValue();
                String resultValueTypeUa = translateSimple(originValueType, Language.RUSSIAN, Language.UKRAINIAN);
                String resultValueTypeEn = translateSimple(originValueType, Language.RUSSIAN, Language.ENGLISH);

                Cell cellTypeUa = row.createCell(13);
                cellTypeUa.setCellValue(resultValueTypeUa);

                Cell cellTypeEn = row.createCell(14);
                cellTypeEn.setCellValue(resultValueTypeEn);

            } else {
                // break;
                // if(counter >= 2871 && counter <= 2875) {
                Cell cell = row.createCell(4);
                cell.setCellValue(originValue);

                String result = translateAddress(originValue, Language.UKRAINIAN, Language.RUSSIAN);
                Cell cellRu = row.getCell(3);
                cellRu.setCellValue(result);

                String resultEng = translateAddress(result, Language.RUSSIAN, Language.ENGLISH);
                Cell cellEn = row.createCell(5);
                cellEn.setCellValue(resultEng);

                // Translate time
                Cell cellTimeRu = row.getCell(6);

                Cell cellTimeUa = row.createCell(7);
                String originValueTime = cellTimeRu.getStringCellValue();

                Cell cellTimeEn = row.createCell(8);

                if (NOT_FOUND_RU.equals(originValueTime)) {
                    originValueTime = NOT_FOUND_UA;
                    cellTimeRu.setCellValue(NOT_FOUND_RU);
                    cellTimeEn.setCellValue(NOT_FOUND_EN);
                } else {
                    String resultValueTimeRu = translateSimple(originValueTime, Language.UKRAINIAN, Language.RUSSIAN);
                    String resultValueTimeEn = translateSimple(resultValueTimeRu, Language.RUSSIAN, Language.ENGLISH);

                    cellTimeRu.setCellValue(resultValueTimeRu);
                    cellTimeEn.setCellValue(resultValueTimeEn);
                }
                cellTimeUa.setCellValue(originValueTime);

                // Translate position
                Cell cellPosRu = row.getCell(9);
                Cell cellPosUa = row.createCell(10);
                String originValuePos = cellPosRu.getStringCellValue();
                cellPosUa.setCellValue(originValuePos);

                String resultValuePosRu = translateSimple(originValuePos, Language.UKRAINIAN, Language.RUSSIAN);
                String resultValuePosEn = translateSimple(resultValuePosRu, Language.RUSSIAN, Language.ENGLISH);

                cellPosRu.setCellValue(resultValuePosRu);

                Cell cellPosEn = row.createCell(11);
                cellPosEn.setCellValue(resultValuePosEn);

                // Translate type
                Cell cellTypeRu = row.getCell(12);
                Cell cellTypeUa = row.createCell(13);

                String originValueType = cellTypeRu.getStringCellValue();

                String resultValueTypeRu = translateSimple(originValueType, Language.UKRAINIAN, Language.RUSSIAN);
                String resultValueTypeEn = translateSimple(resultValueTypeRu, Language.RUSSIAN, Language.ENGLISH);

                cellTypeUa.setCellValue(originValueType);
                cellTypeRu.setCellValue(resultValueTypeRu);

                Cell cellTypeEn = row.createCell(14);
                cellTypeEn.setCellValue(resultValueTypeEn);
                // }
            }

        }
        file.close();
        // Write the workbook in file system
        FileOutputStream out = new FileOutputStream(new File(Constants.OUT_ATMS_FILE_NAME));
        workbook.write(out);
        out.close();

        System.out.println(" written successfully on disk.");
    }
    
    private String translateAddress(String originValue, String from, String to) {
        String values[] = originValue.split("[\\.,]");
        String result = "";

        int length = values.length;

        if (length > 1) {
            for (int i = 0; i < length; i++) {
                String val = values[i];
                if (i == length - 1) {
                    if (result.length() > 2) {
                        String tmp = result.substring(0, result.length() - 2);
                        tmp += ", ";
                        tmp += mTranslate.translate(val, from, to);
                        result = tmp;
                    }
                } else {
                    result += mTranslate.translate(val, from, to) + ". ";
                }
            }
        } else {
            String tmp = mTranslate.translate(originValue, from, to);
            result = tmp;
        }
        return result;
    }

    private String translateSimple(String originValue, String from, String to) {
        String result = mTranslate.translate(originValue, from, to);
        return result;
    }
    
}
