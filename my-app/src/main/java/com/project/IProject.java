package com.project;

/**
 * Interface defining the essential operations for managing project visibility.
 * This interface establishes the contract for showing or hiding projects from applicants.
 */
public interface IProject {
    /**
     * Toggles the visibility of the project
     * 
     * @param isVisible true to make the project visible, false to hide it
     */
    void toggleVisibility(boolean isVisible);  // Toggle project visibility
    
    /**
     * Checks if the project is currently visible to applicants
     * 
     * @return true if the project is visible, false otherwise
     */
    boolean isVisible();  // Check if the project is visible to the applicant
}