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
    private static List<Project> projectList;
    private static List<Applicant> applicantList;
    private static List<Manager> managerList;
    private static List<Officer> officerList;

    public static List<?> read_excel(String filePath) {
        List<Object> dataList = new ArrayList<>();
        String fileName = new File(filePath).getName().toLowerCase();
        try (FileInputStream file = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(file)) {

        Sheet sheet = workbook.getSheetAt(0); // Assume each file has 1 sheet
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Skip header row

            if (fileName.contains("project")) {
                String name = row.getCell(0).getStringCellValue();

                dataList.add(new Project(name));

            } else if (fileName.contains("applicant")) {
                String name = row.getCell(0).getStringCellValue();
                String nric = row.getCell(1).getStringCellValue();
                int age = (int) row.getCell(2).getNumericCellValue();
                String maritalStatus = row.getCell(3).getStringCellValue();
                String password = row.getCell(4).getStringCellValue();
                dataList.add(new Applicant(name, nric, age, maritalStatus, password));

            } else if (fileName.contains("manager")) {
                String name = row.getCell(0).getStringCellValue();
                String nric = row.getCell(1).getStringCellValue();
                int age = (int) row.getCell(2).getNumericCellValue();
                String maritalStatus = row.getCell(3).getStringCellValue();
                String password = row.getCell(4).getStringCellValue();
                dataList.add(new Manager(name, nric, age, maritalStatus, password));

            } else if (fileName.contains("officer")) {
                String name = row.getCell(0).getStringCellValue();
                String nric = row.getCell(1).getStringCellValue();
                int age = (int) row.getCell(2).getNumericCellValue();
                String maritalStatus = row.getCell(3).getStringCellValue();
                String password = row.getCell(4).getStringCellValue();
                dataList.add(new Officer(name, nric, age, maritalStatus, password));
            }
        }

    } catch (IOException e) {
        e.printStackTrace();
    }

    return dataList;
        
    }

    public static void initializeList() {
        projectList = (List<Project>) read_excel("./data/ProjectList.xlsx");
        applicantList = (List<Applicant>) read_excel("./data/ApplicantList.xlsx");
        managerList = (List<Manager>) read_excel("./data/ManagerList.xlsx");
        officerList = (List<Officer>) read_excel("./data/OfficerList.xlsx");
    }
    
    public static void main( String[] args )
    {

        initializeList();
        System.out.println("Excel data loaded");

    }
}
