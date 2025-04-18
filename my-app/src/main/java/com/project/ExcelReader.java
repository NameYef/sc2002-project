package com.project;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class ExcelReader {

    public List<Project> readProjects(String filePath) throws IOException {
        List<Project> projects = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(filePath))) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // skip header

                String name = row.getCell(0).getStringCellValue();
                String neighborhood = row.getCell(1).getStringCellValue();
                String type1 = row.getCell(2).getStringCellValue();
                int noType1 = (int) row.getCell(3).getNumericCellValue();
                int priceType1 = (int) row.getCell(4).getNumericCellValue();
                String type2 = row.getCell(5).getStringCellValue();
                int noType2 = (int) row.getCell(6).getNumericCellValue();
                int priceType2 = (int) row.getCell(7).getNumericCellValue();

                LocalDate openDate = toLocalDate(row.getCell(8).getDateCellValue());
                LocalDate closeDate = toLocalDate(row.getCell(9).getDateCellValue());

                String manager = row.getCell(10).getStringCellValue();
                int officerSlot = (int) row.getCell(11).getNumericCellValue();

                List<String> officers = Arrays.stream(row.getCell(12).getStringCellValue().split(","))
                        .map(String::trim)
                        .collect(Collectors.toList());

                

                projects.add(new Project(name, neighborhood, type1, noType1, priceType1, type2, noType2, priceType2,
                        openDate, closeDate, manager, officerSlot, officers));
            }
        }
        return projects;
    }

    public List<Applicant> readApplicants(String filePath) throws IOException {
        return readUsers(filePath, Applicant::new);
    }

    public List<Manager> readManagers(String filePath) throws IOException {
        return readUsers(filePath, Manager::new);
    }

    public List<Officer> readOfficers(String filePath) throws IOException {
        return readUsers(filePath, Officer::new);
    }

    private <T extends User> List<T> readUsers(String filePath, UserFactory<T> factory) throws IOException {
        List<T> users = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(filePath))) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                String name = row.getCell(0).getStringCellValue();
                String nric = row.getCell(1).getStringCellValue();
                int age = (int) row.getCell(2).getNumericCellValue();
                String maritalStatus = row.getCell(3).getStringCellValue();
                String password = row.getCell(4).getStringCellValue();

                users.add(factory.create(name, nric, age, maritalStatus, password));
            }
        }
        return users;
    }

    private LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @FunctionalInterface
    private interface UserFactory<T extends User> {
        T create(String name, String nric, int age, String maritalStatus, String password);
    }
}

