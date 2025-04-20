package com.project;
import java.util.Scanner;
import java.util.List;
// Interface for inquiry-related operations
interface InquiryOperations {
    void submitInquiry(Scanner scanner, List<Project> projectList);
    void viewInquiries(List<Project> projectList);
    void editInquiry(Scanner scanner, List<Project> projectList);
    void deleteInquiry(Scanner scanner, List<Project> projectList);
    void showInquiryInterface(Scanner scanner, List<Project> projectList);
}
