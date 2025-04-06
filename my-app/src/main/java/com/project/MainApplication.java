package com.project;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MainApplication 
{
    private static List<Project> projectList;
    private static List<Applicant> applicantList;
    private static List<Manager> managerList;
    private static List<Officer> officerList;
    private static List<User> allUsers;
    private static User currentUser;


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
                String neighborhood = row.getCell(1).getStringCellValue();
                String type1 = row.getCell(2).getStringCellValue();
                int noType1 = (int) row.getCell(3).getNumericCellValue();
                int priceType1 = (int) row.getCell(4).getNumericCellValue();
                String type2 = row.getCell(5).getStringCellValue();
                int noType2 = (int) row.getCell(6).getNumericCellValue();
                int priceType2 = (int) row.getCell(7).getNumericCellValue();

                Date open = row.getCell(8).getDateCellValue(); // java.util.Date
                LocalDate openDate = open.toInstant()
                                           .atZone(ZoneId.systemDefault())
                                           .toLocalDate();
                Date close = row.getCell(9).getDateCellValue(); // java.util.Date
                LocalDate closeDate = close.toInstant()
                                            .atZone(ZoneId.systemDefault())
                                            .toLocalDate();

                String manager = row.getCell(10).getStringCellValue();
                int officerSlot = (int) row.getCell(11).getNumericCellValue();

                List<String> officers = new ArrayList<>();
                String nameStr = row.getCell(12).getStringCellValue();
                officers = Arrays.stream(nameStr.split(","))
                            .map(String::trim)
                            .toList(); 
                dataList.add(new Project(name, neighborhood, type1, noType1, priceType1, type2, noType2, priceType2, openDate, closeDate, manager, officerSlot, officers));

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

        // the officers and managers stored in projects are only strings, they should be stored as class objects
        
        
        
        
        
        allUsers = new ArrayList<>();
        allUsers.addAll(applicantList);
        allUsers.addAll(managerList);
        allUsers.addAll(officerList);
    }
    
    public static boolean login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Login ===");
        System.out.print("Enter user ID: ");
        String inputId = scanner.nextLine();
        System.out.print("Enter password: ");
        String inputPass = scanner.nextLine();

        for (User user : allUsers) {
            if (user.getNric().equals(inputId) && user.getPassword().equals(inputPass)) {
                currentUser = user;
                System.out.println("Logged in as " + currentUser.getRole() + " " + currentUser.getName());
                return true;
            }
        }

        System.out.println("Invalid user ID or password. Try again.");
        return false;
    }
    public static void main( String[] args )
    {
        boolean run = true;
        boolean loggedIn = false;
    
        initializeList();
        Scanner scanner = new Scanner(System.in);

        while (run) {
            // login
            loggedIn = login();

            while (loggedIn) {
                String action = currentUser.showInterface(scanner, projectList);
                switch (action) {
                    case "logout":
                        loggedIn = false;
                        currentUser = null;
                        break;
                    case "quit":
                        run = false;
                        loggedIn = false;
                        break;
                    default:
                        // do nothing, just loop again
                }

            }
        }

        // write to excel & terminate
        System.out.println("Terminating...");
        
    }
}
