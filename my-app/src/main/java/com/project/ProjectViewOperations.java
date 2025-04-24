package com.project;
import java.util.Scanner;
import java.util.List;

/**
 * Interface for project viewing operations.
 * Defines the methods required for viewing, filtering, and managing eligible projects.
 */
interface ProjectViewOperations {
    /**
     * Displays the available projects to the user
     */
    void viewProjects();
    
    /**
     * Sets filters for project viewing based on user input
     * 
     * @param scanner the Scanner object to read user input
     */
    void setProjectFilters(Scanner scanner);
    
    /**
     * Populates the list of eligible projects based on the applicant's criteria
     * 
     * @param projectList the master list of all available projects
     */
    void fillEligibleProjects(List<Project> projectList);
}