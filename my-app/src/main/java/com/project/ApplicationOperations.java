package com.project;
import java.util.Scanner;

// Interface for application-related operations
interface ApplicationOperations {
    void applyForProject(Scanner scanner);
    void viewApplicationStatus();
    void withdrawApplication();
    String getApplicationStatus();
}
