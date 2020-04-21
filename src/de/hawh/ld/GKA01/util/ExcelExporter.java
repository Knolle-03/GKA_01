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

    public void exportData(String filename, List<String[]> data) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet spreadsheet = workbook.createSheet( "Time data");

        XSSFRow row;


        //Iterate over data and write to sheet

        int rowid = 0;

        for (Object[] objArr : data) {
            row = spreadsheet.createRow(rowid++);

            int cellid = 0;

            for (Object obj : objArr){
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String)obj);
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
