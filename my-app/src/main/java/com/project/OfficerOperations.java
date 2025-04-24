package com.project;
import java.util.Scanner;
import java.util.List;

/**
 * Interface defining the operations that can be performed by officers.
 * This interface establishes the contract for officer-specific functionality within the system.
 */
interface OfficerOperations {
    /**
     * Displays all projects the officer has undertaken
     */
    void viewUndertakenProjects();
    
    /**
     * Allows the officer to reply to inquiries from applicants
     * 
     * @param scanner the Scanner object to read user input
     */
    void replyToInquiries(Scanner scanner);
    
    /**
     * Registers the user as an officer for a project
     * 
     * @param scanner the Scanner object to read user input
     * @param projectList the list of available projects
     */
    void registerAsOfficer(Scanner scanner, List<Project> projectList);
    
    /**
     * Displays the current registration status of the officer
     */
    void viewRegistrationStatus();
    
    /**
     * Processes flat booking requests from applicants
     * 
     * @param scanner the Scanner object to read user input
     */
    void bookFlat(Scanner scanner);
}