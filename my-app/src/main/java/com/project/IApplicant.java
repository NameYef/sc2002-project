package com.project;

import java.util.List;
import java.util.Scanner;

public interface IApplicant {
    void viewProjects();  // View available projects based on user group
    void applyForProject(Scanner scanner);  // Apply for a project
    void viewApplicationStatus();  // View application status
    void withdrawApplication();  // Withdraw application
    void submitInquiry(String inquiry);  // Submit an inquiry about a project
    void viewInquiry();  // View submitted inquiries
    void editInquiry(String newInquiry);  // Edit inquiry
    void deleteInquiry();  // Delete inquiry
}