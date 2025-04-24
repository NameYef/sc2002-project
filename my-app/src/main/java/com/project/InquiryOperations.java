package com.project;

import java.util.Scanner;
import java.util.List;

/**
 * The {@code InquiryOperations} interface defines the contract for managing inquiries 
 * made by an applicant. Implementing classes must provide methods for submitting, 
 * viewing, editing, deleting, and interacting with the inquiry interface.
 */
interface InquiryOperations {

    /**
     * Submits an inquiry for a selected project.
     *
     * @param scanner the Scanner object used for user input
     * @param projectList the list of eligible projects to inquire about
     */
    void submitInquiry(Scanner scanner, List<Project> projectList);

    /**
     * Displays all inquiries made by the applicant across all projects.
     *
     * @param projectList the list of all projects to search for inquiries
     */
    void viewInquiries(List<Project> projectList);

    /**
     * Allows the applicant to edit a previously submitted inquiry that has not received a reply.
     *
     * @param scanner the Scanner object used for user input
     * @param projectList the list of all projects containing inquiries
     */
    void editInquiry(Scanner scanner, List<Project> projectList);

    /**
     * Deletes a previously submitted inquiry.
     *
     * @param scanner the Scanner object used for user input
     * @param projectList the list of all projects containing inquiries
     */
    void deleteInquiry(Scanner scanner, List<Project> projectList);

    /**
     * Displays the main interface for managing inquiries, including submitting,
     * viewing, editing, and deleting them.
     *
     * @param scanner the Scanner object used for user interaction
     * @param projectList the list of projects available for inquiries
     */
    void showInquiryInterface(Scanner scanner, List<Project> projectList);
}
