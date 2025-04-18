package com.project;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
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

    public void writeProjects(String filePath, List<Project> projects) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet();
            Row header = sheet.createRow(0);
            String[] headers = {"Name", "Neighborhood", "Type1", "NoType1", "PriceType1", "Type2", "NoType2", "PriceType2", "OpenDate", "CloseDate", "Manager", "OfficerSlot", "Officers", "Visibility", "Applicants"};
            for (int i = 0; i < headers.length; i++) header.createCell(i).setCellValue(headers[i]);

            int rowIndex = 1;
            for (Project p : projects) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(p.getName());
                row.createCell(1).setCellValue(p.getNeighborhood());
                row.createCell(2).setCellValue(p.getType1());
                row.createCell(3).setCellValue(p.getNoType1());
                row.createCell(4).setCellValue(p.getPriceType1());
                row.createCell(5).setCellValue(p.getType2());
                row.createCell(6).setCellValue(p.getNoType2());
                row.createCell(7).setCellValue(p.getPriceType2());
                row.createCell(8).setCellValue(java.sql.Date.valueOf(p.getOpenDate()));
                row.createCell(9).setCellValue(java.sql.Date.valueOf(p.getCloseDate()));
                row.createCell(10).setCellValue(p.getManager().getNric());
                row.createCell(11).setCellValue(p.getOfficerSlot());
                row.createCell(12).setCellValue(String.join(",", p.getOfficersStr()));
                row.createCell(13).setCellValue(p.isVisible());
                row.createCell(14).setCellValue(String.join(",", p.getApplicantsStr()));
            }

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }
        }
    }

    public List<Applicant> readApplicants(String filePath) throws IOException {
        return readUsers(filePath, Applicant::new);
    }

    public void writeApplicants(String filePath, List<Applicant> applicants) throws IOException {
        writeUsers(filePath, applicants);
    }

    public List<Manager> readManagers(String filePath) throws IOException {
        return readUsers(filePath, Manager::new);
    }

    public void writeManagers(String filePath, List<Manager> managers) throws IOException {
        writeUsers(filePath, managers);
    }

    public List<Officer> readOfficers(String filePath) throws IOException {
        return readUsers(filePath, Officer::new);
    }

    public void writeOfficers(String filePath, List<Officer> officers) throws IOException {
        writeUsers(filePath, officers);
    }

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

    public void writeInquiries(String filePath, List<Inquiry> inquiries) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet();
            Row header = sheet.createRow(0);
            String[] headers = {"ApplicantNRIC", "Message", "ProjectName", "Reply", "Timestamp"};
            for (int i = 0; i < headers.length; i++) header.createCell(i).setCellValue(headers[i]);

            int rowIndex = 1;
            for (Inquiry inq : inquiries) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(inq.getApplicant().getNric());
                row.createCell(1).setCellValue(inq.getMessage());
                row.createCell(2).setCellValue(inq.getProjectName());
                row.createCell(3).setCellValue(inq.getReply());
                row.createCell(4).setCellValue(java.sql.Date.valueOf(inq.getTimestamp()));
            }

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }
        }
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

    private <T extends User> void writeUsers(String filePath, List<T> users) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet();
            Row header = sheet.createRow(0);
            String[] headers = {"Name", "NRIC", "Age", "MaritalStatus", "Password"};
            for (int i = 0; i < headers.length; i++) header.createCell(i).setCellValue(headers[i]);

            int rowIndex = 1;
            for (T user : users) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(user.getName());
                row.createCell(1).setCellValue(user.getNric());
                row.createCell(2).setCellValue(user.getAge());
                row.createCell(3).setCellValue(user.getMaritalStatus());
                row.createCell(4).setCellValue(user.getPassword());
            }

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }
        }
    }

    private LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @FunctionalInterface
    private interface UserFactory<T extends User> {
        T create(String name, String nric, int age, String maritalStatus, String password);
    }

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

