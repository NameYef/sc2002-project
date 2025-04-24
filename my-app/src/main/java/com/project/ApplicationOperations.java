/**
 * [Interface for defining operations related to housing applications.]
 */
package com.project;

import java.util.Scanner;

/**
 * Defines the contract for application-related operations such as applying, viewing, and withdrawing applications.
 */
interface ApplicationOperations {

    /**
     * [Handles the logic for applying to a project.]
     *
     * @param scanner [Scanner used for receiving user input during the application process.]
     */
    void applyForProject(Scanner scanner);

    /**
     * [Displays the current application status to the user.]
     */
    void viewApplicationStatus();

    /**
     * [Allows the user to withdraw their current application.]
     */
    void withdrawApplication();

    /**
     * [Returns the current status of the user's application.]
     *
     * @return [The application status, e.g., "Pending", "Successful", or "Unsuccessful".]
     */
    String getApplicationStatus();
}
