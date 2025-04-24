package com.project;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Function;

/**
 * Utility class for reading Excel files and converting rows into domain-specific Java objects
 * such as {@link Project}, {@link Applicant}, {@link Manager}, {@link Officer}, and {@link Inquiry}.
 * <p>
 * Uses Apache POI for parsing `.xlsx` files.
 */
public class ExcelReader {
    /**
     * Reads project data from an Excel file and constructs a list of {@link Project} objects.
     *
     * @param filePath the path to the Excel file
     * @return a list of Project instances
     * @throws IOException if there is an error reading the file
     */

    public List<Project> readProjects(String filePath) throws IOException {
        List<Project> projects = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(filePath))) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0 || row == null) continue;

                boolean isRowEmpty = true;
                for (Cell cell : row) {
                    if (cell != null && cell.getCellType() != CellType.BLANK) {
                        isRowEmpty = false;
                        break;
                    }
                }
                if (isRowEmpty) continue;
                String name = row.getCell(0).getStringCellValue();
                String neighborhood = row.getCell(1).getStringCellValue();
                String type1 = row.getCell(2).getStringCellValue();
                int noType1 = (int) row.getCell(3).getNumericCellValue();
                double priceType1 = row.getCell(4).getNumericCellValue();
                String type2 = row.getCell(5).getStringCellValue();
                int noType2 = (int) row.getCell(6).getNumericCellValue();
                double priceType2 = row.getCell(7).getNumericCellValue();
                LocalDate openDate = toLocalDate(row.getCell(8).getDateCellValue());
                LocalDate closeDate = toLocalDate(row.getCell(9).getDateCellValue());
                String manager = row.getCell(10).getStringCellValue();
                int officerSlot = (int) row.getCell(11).getNumericCellValue();
                Cell officerCell = row.getCell(12);
                List<String> officers = new ArrayList<>();

                if (officerCell != null && officerCell.getCellType() == CellType.STRING && !officerCell.getStringCellValue().trim().isEmpty()) {
                    officers = Arrays.stream(officerCell.getStringCellValue().split(","))
                                    .map(String::trim)
                                    .collect(Collectors.toList());
                }

                boolean visibility = row.getCell(13).getBooleanCellValue();

                Cell applicantCell = row.getCell(14);
                List<String> applicants = new ArrayList<>();

                if (applicantCell != null && applicantCell.getCellType() == CellType.STRING && !applicantCell.getStringCellValue().trim().isEmpty()) {
                    applicants = Arrays.stream(applicantCell.getStringCellValue().split(","))
                                    .map(String::trim)
                                    .collect(Collectors.toList());
                }


                projects.add(new Project(name, neighborhood, type1, noType1, priceType1, type2, noType2, priceType2,
                        openDate, closeDate, manager, officerSlot, officers, visibility, applicants));
            }
        }
        return projects;
    }

    
        /**
     * Reads applicant data from an Excel file and constructs a list of {@link Applicant} objects.
     *
     * @param filePath the path to the Excel file
     * @return a list of Applicant instances
     * @throws IOException if there is an error reading the file
     */

    public List<Applicant> readApplicants(String filePath) throws IOException {
        return readUsers(filePath, Applicant::new);
    }

        /**
     * Reads manager data from an Excel file and constructs a list of {@link Manager} objects.
     *
     * @param filePath the path to the Excel file
     * @return a list of Manager instances
     * @throws IOException if there is an error reading the file
     */

    public List<Manager> readManagers(String filePath) throws IOException {
        return readUsers(filePath, Manager::new);
    }

        /**
     * Reads officer data from an Excel file and constructs a list of {@link Officer} objects.
     *
     * @param filePath the path to the Excel file
     * @return a list of Officer instances
     * @throws IOException if there is an error reading the file
     */

    public List<Officer> readOfficers(String filePath) throws IOException {
        return readUsers(filePath, Officer::new);
    }



    private <T extends User> List<T> readUsers(String filePath, UserFactory<T> factory) throws IOException {
        List<T> users = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(filePath))) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0 || row == null) continue;

                boolean isRowEmpty = true;
                for (Cell cell : row) {
                    if (cell != null && cell.getCellType() != CellType.BLANK) {
                        isRowEmpty = false;
                        break;
                    }
                }
                if (isRowEmpty) continue;
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

        /**
     * Functional interface for creating user objects (such as {@link Applicant}, {@link Manager}, or {@link Officer}).
     *
     * @param <T> a type that extends {@link User}
     */

    @FunctionalInterface
    private interface UserFactory<T extends User> {
        T create(String name, String nric, int age, String maritalStatus, String password);
    }
     
        /**
     * Reads inquiries from an Excel file and maps them to existing {@link Applicant} objects by NRIC.
     *
     * @param filePath   the path to the Excel file
     * @param applicants list of Applicants for NRIC matching
     * @return a list of Inquiry objects associated with applicants
     * @throws IOException if there is an error reading the file
     */

    public List<Inquiry> readInquiries(String filePath, List<Applicant> applicants) throws IOException {
        List<Inquiry> inquiries = new ArrayList<>();
        Map<String, Applicant> nricMap = applicants.stream().collect(Collectors.toMap(Applicant::getNric, a -> a));
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(filePath))) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
            
                if (row.getRowNum() == 0 || row == null) continue;

                boolean isRowEmpty = true;
                for (Cell cell : row) {
                    if (cell != null && cell.getCellType() != CellType.BLANK) {
                        isRowEmpty = false;
                        break;
                    }
                }
                if (isRowEmpty) continue;

                String nric = row.getCell(0).getStringCellValue();
                
                String message = row.getCell(1).getStringCellValue();
                String projectName = row.getCell(2).getStringCellValue();
                Cell replyCell = row.getCell(3);
                String reply = null;
                if (replyCell != null && replyCell.getCellType() == CellType.STRING && !replyCell.getStringCellValue().trim().isEmpty()) {
                    reply = replyCell.getStringCellValue();
                }
                LocalDate timestamp = toLocalDate(row.getCell(4).getDateCellValue());

                Applicant applicant = nricMap.get(nric);
                if (applicant != null) {
                    inquiries.add(new Inquiry(applicant, message, projectName, reply, timestamp));
                }
            }
        }
        return inquiries;
    }

        /**
     * Updates a list of {@link Applicant} objects with their application status details from an Excel file.
     *
     * @param filename       the path to the Excel file
     * @param applicantList  the list of applicants to update
     * @param projectList    the list of available projects for matching applied project names
     * @throws IOException if the file cannot be read
     */

    public void readApplicantStatusList(String filename, List<Applicant> applicantList, List<Project> projectList) throws IOException {
        FileInputStream fis = new FileInputStream(filename);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);
    
        Map<String, Applicant> applicantMap = applicantList.stream()
            .collect(Collectors.toMap(Applicant::getNric, Function.identity()));
        Map<String, Project> projectMap = projectList.stream()
            .collect(Collectors.toMap(Project::getName, Function.identity()));
    
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null || row.getCell(0) == null) continue;
    
            String nric = getCellString(row, 0, "");
            Applicant applicant = applicantMap.get(nric);
            if (applicant == null) continue;
    
            String appStatus = getCellString(row, 1, "Unsuccessful");
            boolean withdrawStatus = getCellBoolean(row, 2, false);
            String appliedType = getCellString(row, 3, "");
            String appliedFlatType = getCellString(row, 4, "");
            String flatTypeBooked = getCellString(row, 5, "");
            String appliedProjectStr = getCellString(row, 6, "");
    
            Project appliedProject = projectMap.getOrDefault(appliedProjectStr, null);
    
            applicant.setApplicationStatus(appStatus);
            applicant.setWithdrawStatus(withdrawStatus);
            applicant.setAppliedType(appliedType);
            applicant.setAppliedFlatType(appliedFlatType);
            applicant.setFlatTypeBooked(flatTypeBooked);
            applicant.setAppliedProject(appliedProject);
        }
    
        workbook.close();
        fis.close();
    }
        /**
     * Updates a list of {@link Officer} objects with their application and registration status details from an Excel file.
     *
     * @param filename      the path to the Excel file
     * @param officerList   the list of officers to update
     * @param projectList   the list of projects used for matching officer applications and registrations
     * @throws IOException if the file cannot be read
     */

    public void readOfficerStatusList(String filename, List<Officer> officerList, List<Project> projectList) throws IOException {
        FileInputStream fis = new FileInputStream(filename);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);
    
        Map<String, Officer> officerMap = officerList.stream()
            .collect(Collectors.toMap(Officer::getNric, Function.identity()));
        Map<String, Project> projectMap = projectList.stream()
            .collect(Collectors.toMap(Project::getName, Function.identity()));
    
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null || row.getCell(0) == null) continue;
    
            String nric = getCellString(row, 0, "");
            Officer officer = officerMap.get(nric);
            if (officer == null) continue;
    
            String appStatus = getCellString(row, 1, "Unsuccessful");
            boolean withdrawStatus = getCellBoolean(row, 2, false);
            String appliedType = getCellString(row, 3, "");
            String appliedFlatType = getCellString(row, 4, "");
            String flatTypeBooked = getCellString(row, 5, "");
            String appliedProjectStr = getCellString(row, 6, "");
            String registrationStatus = getCellString(row, 7, "Not Registered");
            String registeredProjectStr = getCellString(row, 8, "");
    
            Project appliedProject = projectMap.getOrDefault(appliedProjectStr, null);
            Project registeredProject = projectMap.getOrDefault(registeredProjectStr, null);
            if (registrationStatus.equalsIgnoreCase("Pending")) {Officer.getPendingOfficers().add(officer);}
            officer.setApplicationStatus(appStatus);
            officer.setWithdrawStatus(withdrawStatus);
            officer.setAppliedType(appliedType);
            officer.setAppliedFlatType(appliedFlatType);
            officer.setFlatTypeBooked(flatTypeBooked);
            officer.setAppliedProject(appliedProject);
            officer.setRegistrationStatus(registrationStatus);
            officer.setRegisteredProject(registeredProject);
        }
    
        workbook.close();
        fis.close();
    }
    
        /**
     * Gets the string content of a cell or returns a default value if the cell is null or blank.
     *
     * @param row          the row containing the cell
     * @param index        the index of the cell in the row
     * @param defaultValue the default value to return if the cell is blank
     * @return the string content of the cell or the default value
     */

    private static String getCellString(Row row, int index, String defaultValue) {
        Cell cell = row.getCell(index);
        return (cell == null || cell.getCellType() == CellType.BLANK) ? defaultValue : cell.getStringCellValue();
    }
    
        /**
     * Parses a boolean value from a cell, or returns a default value if the cell is null or unrecognized.
     *
     * @param row          the row containing the cell
     * @param index        the index of the cell in the row
     * @param defaultValue the default boolean value to return if the cell is blank
     * @return the boolean value of the cell or the default value
     */

    private static boolean getCellBoolean(Row row, int index, boolean defaultValue) {
        Cell cell = row.getCell(index);
        if (cell == null || cell.getCellType() == CellType.BLANK) return defaultValue;
        if (cell.getCellType() == CellType.BOOLEAN) return cell.getBooleanCellValue();
        if (cell.getCellType() == CellType.STRING) return Boolean.parseBoolean(cell.getStringCellValue());
        if (cell.getCellType() == CellType.NUMERIC) return cell.getNumericCellValue() != 0;
        return defaultValue;
    }
    
        /**
     * Parses a boolean value from a cell, or returns a default value if the cell is null or unrecognized.
     *
     * @param date          the row containing the cell
     * @return the local date 
     */
    private LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    
           /**
     * Parses a boolean value from a cell, or returns a default value if the cell is null or unrecognized.
     *
     * @param projects
     * @param managers        
     * @param officers 
     * @param applicants
     * @param inquiries
     */
    
    public void resolveProjectReferences(List<Project> projects,
                                     List<Manager> managers,
                                     List<Officer> officers,
                                     List<Applicant> applicants,
                                     List<Inquiry> inquiries) {
        // Create NRIC -> Object maps
        Map<String, Manager> managerMap = managers.stream()
                .collect(Collectors.toMap(Manager::getNric, m -> m));
        Map<String, Officer> officerMap = officers.stream()
                .collect(Collectors.toMap(Officer::getNric, o -> o));
        Map<String, Applicant> applicantMap = applicants.stream()
                .collect(Collectors.toMap(Applicant::getNric, a -> a));
        Map<String, Project> projectMap = projects.stream()
                .collect(Collectors.toMap(Project::getName, p -> p));  // name is unique

        // Set Manager, Officers, Applicants for each project
        for (Project project : projects) {
            project.setManager(managerMap.get(project.getManagerStr()));

            List<Officer> resolvedOfficers = project.getOfficersStr().stream()
                    .map(officerMap::get)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            project.setOfficers(resolvedOfficers);

            List<Applicant> resolvedApplicants = project.getApplicantsStr().stream()
                    .map(applicantMap::get)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            project.setApplicants(resolvedApplicants);

            for (Applicant applicant : project.getApplicants()) {
                if (applicant.getApplicationStatus().equals("Successful") || applicant.getApplicationStatus().equals("Booked")) {
                    project.addApprovedApplicant(applicant);
                }
            }
        }

        // Link Inquiries to corresponding Projects
        for (Inquiry inquiry : inquiries) {
            Project project = projectMap.get(inquiry.getProjectName());
            if (project != null) {
                project.addInquiry(inquiry);
            }
        }
    }

}

