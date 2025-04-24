package com.project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Represents an applicant for housing projects in the system.
 * Handles application management, project viewing, and eligibility checking operations.
 * Extends the User class and implements application and project view related operations.
 */
public class Applicant extends User implements ApplicationOperations, ProjectViewOperations {

    // Application-related fields

/** Current status of the applicant's application ("Unsuccessful", "Successful", "Pending", "Booked") */
private String applicationStatus;

/** Flag indicating whether the applicant has requested to withdraw their application */
private boolean withdrawStatus;

/** The type of flat that the applicant has successfully booked ("2-Room", "3-Room") */
private String flatTypeBooked;

/** The type of application the applicant has submitted ("type1", "type2") */
private String appliedType;

/** The specific flat type that the applicant has applied for ("2-Room", "3-Room") */
private String appliedFlatType;

/** The project that the applicant has applied for */
private Project appliedProject;

/** List of projects that the applicant is eligible to apply for */
private List<Project> eligibleProjects;

/** Utility class that checks the eligibility of the applicant for various project types */
private ProjectEligibilityChecker eligibilityChecker;

/** Utility class that handles the display of projects to the applicant */
private ProjectDisplayer projectDisplayer;

    /**
     * Creates a new Applicant with the specified personal details.
     * Initializes the applicant's eligibility checker and project displayer.
     *
     * @param name The name of the applicant
     * @param nric The National Registration Identity Card number of the applicant
     * @param age The age of the applicant
     * @param maritalStatus The marital status of the applicant
     * @param password The password for the applicant's account
     */
    public Applicant(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
        this.eligibleProjects = new ArrayList<>();
        this.applicationStatus = "Unsuccessful";
        this.eligibilityChecker = new ProjectEligibilityChecker(maritalStatus, age);
        this.projectDisplayer = new ProjectDisplayer(this);
    }

    // Getters and setters

    /** @return The project that the applicant has applied for */
    public Project getAppliedProject() { return this.appliedProject; }

    /** @return The type of application submitted ("type1" or "type2") */
    public String getAppliedType() { return this.appliedType; }

    /** @return The current status of the applicant's application */
    public String getApplicationStatus() { return applicationStatus; }

    /** @return The specific flat type that the applicant has applied for */
    public String getAppliedFlatType() { return appliedFlatType; }

    /** @return Whether the applicant has requested to withdraw their application */
    public boolean getWithdrawStatus() { return withdrawStatus; }

    /** @return The type of flat that the applicant has successfully booked */
    public String getFlatTypeBooked() { return flatTypeBooked; }

    /**
     * Sets the type of flat that the applicant has booked
     * @param flatTypeBooked The type of flat booked
     */
    public void setFlatTypeBooked(String flatTypeBooked) { this.flatTypeBooked = flatTypeBooked; }

    /**
     * Sets the withdrawal status of the applicant's application
     * @param withdrawStatus True if the application is withdrawn, false otherwise
     */
    public void setWithdrawStatus(boolean withdrawStatus) { this.withdrawStatus = withdrawStatus; }

    /**
     * Sets the specific flat type that the applicant has applied for
     * @param appliedFlatType The applied flat type
     */
    public void setAppliedFlatType(String appliedFlatType) { this.appliedFlatType = appliedFlatType; }

    /**
     * Updates the application status
     * @param status The new application status
     */
    public void setApplicationStatus(String status) { this.applicationStatus = status; }

    /**
     * Sets the project that the applicant has applied for
     * @param project The project applied for
     */
    public void setAppliedProject(Project project) { this.appliedProject = project; }

    /**
     * Sets the type of application (type1 or type2)
     * @param type The application type
     */
    public void setAppliedType(String type) { this.appliedType = type; }

    /** @return List of projects that the applicant is eligible to apply for */
    public List<Project> getEligibleProjects() { return this.eligibleProjects; }

    /** @return Get the age of the applicant */
    public int getAge() { return this.age; }

    /** @return Get the marital status of the applicant */
    public String getMaritalStatus() { return this.maritalStatus; }

    // Implementation of ProjectViewOperations

    /**
     * Populates the list of eligible projects for the applicant.
     * @param projectList The list of all available projects to check for eligibility
     */
    @Override
    public void fillEligibleProjects(List<Project> projectList) {
        eligibleProjects.clear();
        LocalDate today = LocalDate.now();
        for (Project project : projectList) {
            if (!project.isVisible()) continue;

            if (today.isBefore(project.getOpenDate()) || today.isAfter(project.getCloseDate())) {
                continue;
            }

            boolean type1Eligible = eligibilityChecker.isEligibleForFlatType(project.getType1());
            boolean type2Eligible = eligibilityChecker.isEligibleForFlatType(project.getType2());

            if (type1Eligible || type2Eligible) {
                eligibleProjects.add(project);
            }
        }
    }

    /**
     * Displays the list of projects that the applicant is eligible to apply for
     * using the project displayer.
     */
    @Override
    public void viewProjects() {
        projectDisplayer.displayProjects(eligibleProjects);
    }

    /**
     * Allows the applicant to set filters for viewing projects
     * through the project displayer.
     *
     * @param scanner Scanner for user input
     */
    @Override
    public void setProjectFilters(Scanner scanner) {
        projectDisplayer.setFilters(scanner);
    }

    /**
     * Handles the application process for a project.
     * Delegates to the ApplicationManager to manage the application.
     *
     * @param scanner Scanner for user input
     */
    @Override
    public void applyForProject(Scanner scanner) {
        ApplicationManager applicationManager = new ApplicationManager(this);
        applicationManager.applyForProject(scanner, eligibleProjects);
    }

    /**
     * Displays the current application status and details of the applied project.
     * Shows information about the project, flat type, and application dates.
     */
    @Override
    public void viewApplicationStatus() {
        if (this.appliedProject != null) {
            System.out.println("Applied for " + this.appliedProject.getName() + " " + this.appliedType);
            System.out.println("Neighborhood: " + this.appliedProject.getNeighborhood());

            if (this.appliedType.equals("type1")) {
                System.out.println("Type1: " + this.appliedProject.getType1());
                System.out.println("Available Units: " + this.appliedProject.getNoType1());
                System.out.println("Selling Price: " + this.appliedProject.getPriceType1());
            } else if (this.appliedType.equals("type2")) {
                System.out.println("Type2: " + this.appliedProject.getType2());
                System.out.println("Available Units: " + this.appliedProject.getNoType2());
                System.out.println("Selling Price: " + this.appliedProject.getPriceType2());
            }

            System.out.println("Application Start Date: " + this.appliedProject.getOpenDate());
            System.out.println("Application Close Date: " + this.appliedProject.getCloseDate());
            System.out.println("Officers: " + 
                this.appliedProject.getOfficers().stream()
                    .map(Officer::getName)
                    .collect(Collectors.joining(", "))
            );
            System.out.println("Manager: " + this.appliedProject.getManager().getName());
        }
        System.out.println("Application Status: " + this.applicationStatus);
    }

    /**
     * Processes a withdrawal request for the current application.
     * Sets the withdrawal status to true if the applicant has an active application.
     */
    @Override
    public void withdrawApplication() {
        if (this.appliedProject != null && !this.applicationStatus.equals("Unsuccessful") && !this.withdrawStatus) {
            this.withdrawStatus = true;
            System.out.println("Withdrawal has been requested");
        }
        else {
            System.out.println("You have not applied for a project yet");
        }
    }

    /**
     * Presents the inquiry interface to the applicant.
     * Allows the applicant to make inquiries about projects.
     *
     * @param scanner Scanner for user input
     * @param projectList List of available projects
     */
    public void showInquiryInterface(Scanner scanner, List<Project> projectList) {
        InquiryManager inquiryManager = new InquiryManager(this);
        inquiryManager.showInquiryInterface(scanner, projectList);
    }

    /**
     * @return The role of this user, which is "Applicant"
     */
    @Override
    public String getRole() {
        return "Applicant";
    }

    /**
     * Displays the main interface for the applicant and handles user input.
     * Provides options for viewing projects, applying, checking status, and more.
     *
     * @param scanner Scanner for user input
     * @param projectList List of available projects
     * @return Command string indicating next action (e.g., "logout", "quit")
     */
    @Override
    public String showInterface(Scanner scanner, List<Project> projectList) {
        fillEligibleProjects(projectList);
        
        while (true) {
            UIHelper.printHeader("APPLICANT MENU");
            System.out.println("1. View Projects");
            System.out.println("2. Change Filters");
            System.out.println("3. Apply For a Project");
            System.out.println("4. View Applied Project"); 
            System.out.println("5. Request for withdrawal");
            System.out.println("6. Inquiries");
            System.out.println("7. Reset Password");
            System.out.println("8. Logout");
            System.out.println("9. Quit");
            UIHelper.printProjectFooter();
            System.out.print("Enter your Choice: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    viewProjects();
                    break;
                case "2":
                    setProjectFilters(scanner);
                    break;
                case "3":
                    applyForProject(scanner);
                    break;
                case "4":
                    viewApplicationStatus();
                    break;
                case "5":
                    withdrawApplication();
                    break;
                case "6":
                    showInquiryInterface(scanner, projectList);
                    break;
                case "7":
                    return resetPassword(scanner);
                case "8":
                    return "logout";
                case "9":
                    return "quit";
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
