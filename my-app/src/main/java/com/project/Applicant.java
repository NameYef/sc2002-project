package com.project;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Applicant extends User implements IApplicant {

    private String applicationStatus;
    private List<String> inquiries; // Fixed: Initialized in the constructor

    public Applicant(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
        this.applicationStatus = "Pending";
        this.inquiries = new ArrayList<>();  // Fixed: Properly initialized
    }

    public String getApplicationStatus() { return applicationStatus; }
    public String getPassword() { return password; }

    @Override
    public void viewProjects() {
        System.out.println("Viewing available projects for Applicant (" + maritalStatus + ", " + age + " years old).");
    }

    @Override
    public void applyForProject(Project project) {
        if (project.isEligible(this)) {
            project.addApplication(this);
            this.applicationStatus = "Pending";
            System.out.println(nric + " applied for project: " + project.getName());
        } else {
            System.out.println("Applicant not eligible for this project.");
        }
    }

    @Override
    public void viewApplicationStatus() {
        System.out.println("Application Status: " + this.applicationStatus);
    }

    @Override
    public void withdrawApplication() {
        if (!this.applicationStatus.equals("Booked")) {
            this.applicationStatus = "Withdrawn";
            System.out.println("Application has been withdrawn.");
        } else {
            System.out.println("Cannot withdraw after booking a flat.");
        }
    }

    @Override
    public void submitInquiry(String inquiry) {
        inquiries.add(inquiry);
        System.out.println("Submitted Inquiry: " + inquiry);
    }

    @Override
    public void viewInquiry() {
        if (inquiries.isEmpty()) {
            System.out.println("No inquiries available.");
        } else {
            System.out.println("Submitted Inquiries:");
            for (String inquiry : inquiries) {
                System.out.println(inquiry);
            }
        }
    }

    @Override
    public void editInquiry(String newInquiry) {
        if (!inquiries.isEmpty()) {
            inquiries.set(inquiries.size() - 1, newInquiry);
            System.out.println("Updated Inquiry: " + newInquiry);
        } else {
            System.out.println("No inquiries available to edit.");
        }
    }

    @Override
    public void deleteInquiry() {
        if (!inquiries.isEmpty()) {
            inquiries.remove(inquiries.size() - 1);
            System.out.println("Last inquiry deleted.");
        } else {
            System.out.println("No inquiries to delete.");
        }
    }

    @Override
    public String getRole() {
        return "Applicant";
    }

    @Override
    public String showInterface(Scanner scanner, List<Project> projectList) {
        while (true) {
            System.out.println(" Applicant Menu:");
            System.out.println("1. Reset Password");
            System.out.println("2. Logout");
            System.out.println("3. Quit");
            System.out.print("Choice: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    return resetPassword(scanner);
                case "2":
                    return "logout";
                case "3":
                    return "quit";
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

}