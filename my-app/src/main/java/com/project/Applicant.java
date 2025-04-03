package com.project;
import java.util.ArrayList;
import java.util.List;

public class Applicant implements IApplicant {
    private String name;
    private String nric;
    private int age;
    private String maritalStatus;
    private String applicationStatus;
    private String password;
    private List<String> inquiries; // Fixed: Initialized in the constructor

    public Applicant(String name, String nric, int age, String maritalStatus, String password) {
        this.name = name;
        this.nric = nric;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.password = password;
        this.applicationStatus = "Pending";
        this.inquiries = new ArrayList<>();  // Fixed: Properly initialized
    }

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
    public String getName() {
        return name;
    }
    public String getNric() { return nric; }
    public int getAge() { return age; }
    public String getMaritalStatus() { return maritalStatus; }
    public String getApplicationStatus() { return applicationStatus; }
}