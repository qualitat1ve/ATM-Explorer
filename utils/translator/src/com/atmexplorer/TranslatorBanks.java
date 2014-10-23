package com.atmexplorer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gtranslate.Language;
import com.gtranslate.Translator;

public class TranslatorBanks {
    public void translate() {

        XSSFWorkbook workbook = null;
        try {
            FileInputStream file = new FileInputStream(new File(Constants.IN_ATMS_FILE_NAME_BANK));

            workbook = new XSSFWorkbook(file);

            // Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            // Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();
            Translator translate = Translator.getInstance();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                String originValue = row.getCell(3).getStringCellValue();

                String text = translate.translate(originValue, Language.RUSSIAN, Language.UKRAINIAN);
                String textEn = translate.translate(originValue, Language.RUSSIAN, Language.ENGLISH);
                System.out.println(originValue + "\t" + text + "\t" + textEn);
                Cell cell = row.createCell(4);
                cell.setCellValue(text);

                Cell cellEn = row.createCell(5);
                cellEn.setCellValue(textEn);

            }
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(Constants.OUT_ATMS_FILE_NAME_BANK));
            workbook.write(out);
            out.close();

            System.out.println(" written successfully on disk.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
