package com.project;
public interface IProject {
    void toggleVisibility(boolean isVisible);  // Toggle project visibility
    boolean isVisible();  // Check if the project is visible to the applicant
    boolean isEligible(Applicant applicant);  // Check if applicant is eligible for this project
    void addApplication(Applicant applicant);  // Add applicant to the project application list
}
