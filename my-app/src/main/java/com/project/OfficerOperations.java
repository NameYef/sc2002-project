package com.project;
import java.util.Scanner;
import java.util.List;
// Interface to define Officer-specific operations
interface OfficerOperations {
    void viewUndertakenProjects();
    void replyToInquiries(Scanner scanner);
    void registerAsOfficer(Scanner scanner, List<Project> projectList);
    void viewRegistrationStatus();
    void bookFlat(Scanner scanner);
}
