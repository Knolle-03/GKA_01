package de.hawh.ld.GKA01.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExporter {

    public void exportData(String filename, String[] header , List<long[]> data) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet spreadsheet = workbook.createSheet( "runtime data");

        XSSFRow row;

//
//        for (int i = 0; i < data.size(); i++) {
//            row = sheet.createRow(i);
//            for (int j = 0; j < data.get(i).length; j++) {
//                Cell cell = row.createCell(j);
//                cell.setCellValue(data.get(i)[j]);
//            }
//        }




        // fill header
        int rowId = 0;
        row = spreadsheet.createRow(rowId++);
        int cellId = 0;

        for (String str : header) {
            Cell cell = row.createCell(cellId++);
            cell.setCellValue(str);

        }



        for (long[] longArr : data) {
            row = spreadsheet.createRow(rowId++);

            cellId = 0;

            for (long runtime : longArr){
                Cell cell = row.createCell(cellId++);
                cell.setCellValue(runtime);
            }
        }

        //Write the workbook in file system
        FileOutputStream out = new FileOutputStream(
                new File("resources/sheets/" + filename + ".xlsx"));

        workbook.write(out);
        out.close();
        System.out.println(filename + ".xlsx written successfully");
    }
}
