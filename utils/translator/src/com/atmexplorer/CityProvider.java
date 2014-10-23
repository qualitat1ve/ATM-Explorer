package com.atmexplorer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gtranslate.Language;
import com.gtranslate.Translator;

public class CityProvider {
    private Map<Integer, String> mCitiesRu = new HashMap<Integer, String>();
    private Map<Integer, String> mCitiesUa = new HashMap<Integer, String>();

    public void readCities() throws IOException {
        FileInputStream file = new FileInputStream(new File(Constants.OUT_ATMS_FILE_NAME_CITY));
        XSSFWorkbook workbook = new XSSFWorkbook(file);

        XSSFSheet sheet = workbook.getSheetAt(0);
        // Iterate through each rows one by one
        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Integer idValue = Double.valueOf((row.getCell(0).getNumericCellValue())).intValue();

            Cell cellRu = row.getCell(1);
            String value = cellRu.getStringCellValue();
            mCitiesRu.put(idValue, value);

            Cell cellUa = row.getCell(2);
            if (cellUa != null) {
                String valueUa = cellUa.getStringCellValue();
                mCitiesUa.put(idValue, valueUa);
            }

        }
        file.close();
        System.out.println("citiesRu " + mCitiesRu.size() + " citiesUa " + mCitiesUa.size());
    }

    public void translate() throws IOException {
        FileInputStream file = new FileInputStream(new File(Constants.IN_ATMS_FILE_NAME_CITY));
        XSSFWorkbook workbook = new XSSFWorkbook(file);

        Translator translator = Translator.getInstance();

        XSSFSheet sheet = workbook.getSheetAt(0);
        // Iterate through each rows one by one
        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Cell cellRu = row.getCell(1);
            String value = cellRu.getStringCellValue();

            Cell cellUa = row.createCell(2);
            String valueUa = translator.translate(value, Language.RUSSIAN, Language.UKRAINIAN);
            if (cellUa != null) {
                System.out.println("ru " + value + " ua " + valueUa);
                cellUa.setCellValue(valueUa);
            }
            
            Cell cellEn = row.createCell(3);
            String valueEn = translator.translate(value, Language.RUSSIAN, Language.ENGLISH);
            if (valueEn != null) {
                System.out.println("en " + valueEn);
                cellEn.setCellValue(valueEn);
            }

        }
        file.close();

        try {
            FileOutputStream out = new FileOutputStream(new File(Constants.OUT_ATMS_FILE_NAME_CITY));
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, String> getCitiesRu() {
        return mCitiesRu;
    }

    public Map<Integer, String> getCitiesUa() {
        return mCitiesUa;
    }
}
