package com.project;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

import java.util.*;

public class ExcelWriter {

    public void writeProjects(String filePath, List<Project> projects) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            CellStyle dateCellStyle = createDateCellStyle(workbook);
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
                Cell cell = row.createCell(8);
                cell.setCellValue(java.sql.Date.valueOf(p.getOpenDate()));
                cell.setCellStyle(dateCellStyle);
                cell = row.createCell(9);
                cell.setCellValue(java.sql.Date.valueOf(p.getCloseDate()));
                cell.setCellStyle(dateCellStyle); 

                row.createCell(10).setCellValue(p.getManager().getNric());
                row.createCell(11).setCellValue(p.getOfficerSlot());
                row.createCell(12).setCellValue(String.join(",", p.getOfficersStr()));
                row.createCell(13).setCellValue(p.isVisible());
                row.createCell(14).setCellValue(String.join(",", p.getApplicantsStr()));
            }

            writeToFile(workbook, filePath);
        }
    }

    public void writeApplicants(String filePath, List<Applicant> applicants) throws IOException {
        writeUsers(filePath, applicants);
    }

    public void writeManagers(String filePath, List<Manager> managers) throws IOException {
        writeUsers(filePath, managers);
    }

    public void writeOfficers(String filePath, List<Officer> officers) throws IOException {
        writeUsers(filePath, officers);
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

            writeToFile(workbook, filePath);
        }
    }

    public void writeInquiries(String filePath, List<Inquiry> inquiries) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            CellStyle dateCellStyle = createDateCellStyle(workbook);
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
                Cell cell = row.createCell(4);
                cell.setCellValue(java.sql.Date.valueOf(inq.getTimestamp()));
                cell.setCellStyle(dateCellStyle);
            }

            writeToFile(workbook, filePath);
        }
    }

    public void writeApplicantStatusList(String filename, List<Applicant> applicantList) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Applicants");
    
        String[] headers = {
            "NRIC", "ApplicationStatus", "WithdrawStatus", "AppliedType",
            "AppliedFlatType", "FlatTypeBooked", "AppliedProject"
        };
    
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }
    
        int rowNum = 1;
        for (Applicant a : applicantList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(a.getNric());
            row.createCell(1).setCellValue(a.getApplicationStatus());
            row.createCell(2).setCellValue(a.getWithdrawStatus());
            row.createCell(3).setCellValue(a.getAppliedType());
            row.createCell(4).setCellValue(a.getAppliedFlatType());
            row.createCell(5).setCellValue(a.getFlatTypeBooked());
            row.createCell(6).setCellValue(a.getAppliedProject() != null ? a.getAppliedProject().getName() : "");
        }
    
        writeToFile(workbook, filename);
    }
    
    public void writeOfficerStatusList(String filename, List<Officer> officerList) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Officers");
    
        String[] headers = {
            "NRIC", "ApplicationStatus", "WithdrawStatus", "AppliedType",
            "AppliedFlatType", "FlatTypeBooked", "AppliedProject",
            "RegistrationStatus", "RegisteredProject"
        };
    
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }
    
        int rowNum = 1;
        for (Officer o : officerList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(o.getNric());
            row.createCell(1).setCellValue(o.getApplicationStatus());
            row.createCell(2).setCellValue(o.getWithdrawStatus());
            row.createCell(3).setCellValue(o.getAppliedType());
            row.createCell(4).setCellValue(o.getAppliedFlatType());
            row.createCell(5).setCellValue(o.getFlatTypeBooked());
            row.createCell(6).setCellValue(o.getAppliedProject() != null ? o.getAppliedProject().getName() : "");
            row.createCell(7).setCellValue(o.getRegistrationStatus());
            row.createCell(8).setCellValue(o.getRegisteredProject() != null ? o.getRegisteredProject().getName() : "");
        }
    
        writeToFile(workbook, filename);
    }
    
    private CellStyle createDateCellStyle(Workbook workbook) {
        CellStyle dateCellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
        return dateCellStyle;
    }
    
    private void writeToFile(Workbook workbook, String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        }
    }
}