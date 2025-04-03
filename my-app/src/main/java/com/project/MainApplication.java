package com.project;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainApplication 
{
    private ArrayList<Project> projectList;
    private ArrayList<Applicant> applicantList;
    // 2 more arrays for officers and managers

    public void read_excel(String filePath, ArrayList<?> list) {
        // String filePath = "./data/ApplicantList.xlsx";  // Change this to your actual file path

        // try (FileInputStream file = new FileInputStream(new File(filePath));
        //      Workbook workbook = new XSSFWorkbook(file)) {

        //     Sheet sheet = workbook.getSheetAt(0);  // Read the first sheet

        //     for (Row row : sheet) {  // Iterate through rows
        //         for (Cell cell : row) {  // Iterate through columns
        //             switch (cell.getCellType()) {
        //                 case STRING:
        //                     System.out.print(cell.getStringCellValue() + "\t");
        //                     break;
        //                 case NUMERIC:
        //                     System.out.print(cell.getNumericCellValue() + "\t");
        //                     break;
        //                 case BOOLEAN:
        //                     System.out.print(cell.getBooleanCellValue() + "\t");
        //                     break;
        //                 default:
        //                     System.out.print("UNKNOWN\t");
        //             }
        //         }
        //         System.out.println();
        //     }

        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
    }
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
