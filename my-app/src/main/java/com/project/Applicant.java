package com.project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * [Brief description of what the Applicant class represents or does]
 */
public class Applicant extends User implements ApplicationOperations, ProjectViewOperations {

    // Application-related fields

    /** [Description of applicationStatus] */
    private String applicationStatus;

    /** [Description of withdrawStatus] */
    private boolean withdrawStatus;

    /** [Description of flatTypeBooked] */
    private String flatTypeBooked;

    /** [Description of appliedType] */
    private String appliedType;

    /** [Description of appliedFlatType] */
    private String appliedFlatType;

    /** [Description of appliedProject] */
    private Project appliedProject;

    // Project-related fields

    /** [Description of eligibleProjects] */
    private List<Project> eligibleProjects;

    /** [Description of eligibilityChecker] */
    private ProjectEligibilityChecker eligibilityChecker;

    /** [Description of projectDisplayer] */
    private ProjectDisplayer projectDisplayer;

    /**
     * [Constructor description]
     *
     * @param name [Description]
     * @param nric [Description]
     * @param age [Description]
     * @param maritalStatus [Description]
     * @param password [Description]
     */
    public Applicant(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
        this.eligibleProjects = new ArrayList<>();
        this.applicationStatus = "Unsuccessful";
        this.eligibilityChecker = new ProjectEligibilityChecker(maritalStatus, age);
        this.projectDisplayer = new ProjectDisplayer(this);
    }

    // Getters and setters

    /** @return [Description] */
    public Project getAppliedProject() { return this.appliedProject; }

    /** @return [Description] */
    public String getAppliedType() { return this.appliedType; }

    /** @return [Description] */
    public String getApplicationStatus() { return applicationStatus; }

    /** @return [Description] */
    public String getAppliedFlatType() { return appliedFlatType; }

    /** @return [Description] */
    public boolean getWithdrawStatus() { return withdrawStatus; }

    /** @return [Description] */
    public String getFlatTypeBooked() { return flatTypeBooked; }

    /**
     * @param flatTypeBooked [Description]
     */
    public void setFlatTypeBooked(String flatTypeBooked) { this.flatTypeBooked = flatTypeBooked; }

    /**
     * @param withdrawStatus [Description]
     */
    public void setWithdrawStatus(boolean withdrawStatus) { this.withdrawStatus = withdrawStatus; }

    /**
     * @param appliedFlatType [Description]
     */
    public void setAppliedFlatType(String appliedFlatType) { this.appliedFlatType = appliedFlatType; }

    /**
     * @param status [Description]
     */
    public void setApplicationStatus(String status) { this.applicationStatus = status; }

    /**
     * @param project [Description]
     */
    public void setAppliedProject(Project project) { this.appliedProject = project; }

    /**
     * @param type [Description]
     */
    public void setAppliedType(String type) { this.appliedType = type; }

    /** @return [Description] */
    public List<Project> getEligibleProjects() { return this.eligibleProjects; }

    /** @return [Description] */
    public int getAge() { return this.age; }

    /** @return [Description] */
    public String getMaritalStatus() { return this.maritalStatus; }

    // Implementation of ProjectViewOperations

    /**
     * [Description of what fillEligibleProjects does]
     *
     * @param projectList [Description]
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
     * [Description of what viewProjects does]
     */
    @Override
    public void viewProjects() {
        projectDisplayer.displayProjects(eligibleProjects);
    }

    /**
     * [Description of what setProjectFilters does]
     *
     * @param scanner [Description]
     */
    @Override
    public void setProjectFilters(Scanner scanner) {
        projectDisplayer.setFilters(scanner);
    }

    // Implementation of ApplicationOperations

    /**
     * [Description of what applyForProject does]
     *
     * @param scanner [Description]
     */
    @Override
    public void applyForProject(Scanner scanner) {
        ApplicationManager applicationManager = new ApplicationManager(this);
        applicationManager.applyForProject(scanner, eligibleProjects);
    }

    /**
     * [Description of what viewApplicationStatus does]
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
     * [Description of what withdrawApplication does]
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
     * [Description of what showInquiryInterface does]
     *
     * @param scanner [Description]
     * @param projectList [Description]
     */
    public void showInquiryInterface(Scanner scanner, List<Project> projectList) {
        InquiryManager inquiryManager = new InquiryManager(this);
        inquiryManager.showInquiryInterface(scanner, projectList);
    }

    /**
     * @return [Description of what role this object represents]
     */
    @Override
    public String getRole() {
        return "Applicant";
    }

    /**
     * [Description of what showInterface does]
     *
     * @param scanner [Description]
     * @param projectList [Description]
     * @return [Description of what the return value represents]
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
