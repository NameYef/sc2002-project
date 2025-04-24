package com.project;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

/**
 * Utility class to write various types of project-related data to Excel (.xlsx) files.
 * Uses Apache POI for Excel generation.
 */
public class ExcelWriter {

    /**
     * Writes a list of projects to an Excel file with various project-related fields.
     *
     * @param filePath the path to the Excel file to be created
     * @param projects the list of Project objects to be written
     * @throws IOException if an I/O error occurs
     */
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

    /**
     * Writes a list of applicants to an Excel file.
     *
     * @param filePath   the path to the Excel file to be created
     * @param applicants the list of Applicant objects
     * @throws IOException if an I/O error occurs
     */
    public void writeApplicants(String filePath, List<Applicant> applicants) throws IOException {
        writeUsers(filePath, applicants);
    }

    /**
     * Writes a list of managers to an Excel file.
     *
     * @param filePath the path to the Excel file to be created
     * @param managers the list of Manager objects
     * @throws IOException if an I/O error occurs
     */
    public void writeManagers(String filePath, List<Manager> managers) throws IOException {
        writeUsers(filePath, managers);
    }

    /**
     * Writes a list of officers to an Excel file.
     *
     * @param filePath the path to the Excel file to be created
     * @param officers the list of Officer objects
     * @throws IOException if an I/O error occurs
     */
    public void writeOfficers(String filePath, List<Officer> officers) throws IOException {
        writeUsers(filePath, officers);
    }

    /**
     * Writes a list of inquiries to an Excel file.
     *
     * @param filePath  the path to the Excel file to be created
     * @param inquiries the list of Inquiry objects
     * @throws IOException if an I/O error occurs
     */
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

    /**
     * Writes the status information of a list of applicants to an Excel file.
     *
     * @param filename       the path to the Excel file to be created
     * @param applicantList  the list of Applicant objects
     * @throws IOException if an I/O error occurs
     */
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

    /**
     * Writes the status information of a list of officers to an Excel file.
     *
     * @param filename     the path to the Excel file to be created
     * @param officerList  the list of Officer objects
     * @throws IOException if an I/O error occurs
     */
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

    /**
     * Creates a date cell style with format "dd/mm/yyyy" for Excel date cells.
     *
     * @param workbook the workbook for which the style is created
     * @return a CellStyle with a date format
     */
    private CellStyle createDateCellStyle(Workbook workbook) {
        CellStyle dateCellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
        return dateCellStyle;
    }

    /**
     * Writes a list of users to an Excel file. Works for any subclass of User.
     *
     * @param filePath the path to the Excel file
     * @param users    the list of users
     * @param <T>      a type that extends User
     * @throws IOException if an I/O error occurs
     */
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

    /**
     * Writes the provided workbook to a file at the specified file path.
     *
     * @param workbook the Excel workbook
     * @param filePath the output file path
     * @throws IOException if an I/O error occurs
     */
    private void writeToFile(Workbook workbook, String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        }
    }
}
