package com.project;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents an Officer in the system, who is both an {@link Applicant} and capable of performing
 * additional officer-level operations such as project registration, responding to inquiries, and flat booking.
 * Officers can register for projects and are evaluated for eligibility. They are managed through 
 * {@link OfficerRegistrationManager}, {@link OfficerProjectManager}, {@link BookingManager}, 
 * and {@link InquiryResponseManager}.
 * 
 * <p>Officers have a registration status and can undertake projects. They are also applicants and thus inherit 
 * applicant-related functionalities.
 * 
 * @see Applicant
 * @see OfficerOperations
 */
public class Officer extends Applicant implements OfficerOperations {

    private List<Project> undertakenProjects;
    private String registrationStatus;
    private Project registeredProject = null;
    private static List<Officer> pendingOfficers = new ArrayList<>();
    private OfficerRegistrationManager registrationManager;
    private OfficerProjectManager projectManager;
    private BookingManager bookingManager;
    private InquiryResponseManager inquiryManager;

    /**
     * Constructs a new Officer.
     * 
     * @param name the officer's name
     * @param nric the NRIC of the officer
     * @param age the officer's age
     * @param maritalStatus the marital status
     * @param password the password for login
     */
    public Officer(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
        this.registrationStatus = "Not Registered";
        this.undertakenProjects = new ArrayList<>();
        this.registrationManager = new OfficerRegistrationManager(this);
        this.projectManager = new OfficerProjectManager(this);
        this.bookingManager = new BookingManager(this);
        this.inquiryManager = new InquiryResponseManager(this);
    }

    /**
     * Returns the role of the user.
     * 
     * @return "Officer"
     */
    @Override
    public String getRole() {
        return "Officer";
    }

    /**
     * Gets the project for which this officer is currently registered.
     * 
     * @return the registered project
     */
    public Project getRegisteredProject() {
        return this.registeredProject;
    }

    /**
     * Sets the project to which this officer is registered.
     * 
     * @param registeredProject the registered project
     */
    public void setRegisteredProject(Project registeredProject) {
        this.registeredProject = registeredProject;
    }

    /**
     * Gets the current registration status.
     * 
     * @return registration status string
     */
    public String getRegistrationStatus() {
        return this.registrationStatus;
    }

    /**
     * Sets the registration status.
     * 
     * @param status the new registration status
     */
    public void setRegistrationStatus(String status) {
        this.registrationStatus = status;
    }

    /**
     * Returns the list of officers pending approval.
     * 
     * @return list of pending officers
     */
    public static List<Officer> getPendingOfficers() {
        return pendingOfficers;
    }

    /**
     * Displays the officer's undertaken projects.
     */
    @Override
    public void viewUndertakenProjects() {
        projectManager.displayUndertakenProjects(this.undertakenProjects);
    }

    /**
     * Populates the eligible project list for officer registration.
     * 
     * @param projectList list of all projects
     */
    @Override
    public void fillEligibleProjects(List<Project> projectList) {
        projectManager.fillEligibleProjects(projectList, this);
    }

    /**
     * Allows officer to respond to inquiries related to their projects.
     * 
     * @param scanner a {@link Scanner} for input
     */
    @Override
    public void replyToInquiries(Scanner scanner) {
        inquiryManager.replyToInquiries(scanner, undertakenProjects);
    }

    /**
     * Registers the officer to a selected project.
     * 
     * @param scanner a {@link Scanner} for input
     * @param projectList list of available projects
     */
    @Override
    public void registerAsOfficer(Scanner scanner, List<Project> projectList) {
        registrationManager.registerAsOfficer(scanner, projectList, pendingOfficers);
    }

    /**
     * Displays the current registration status to the officer.
     */
    @Override
    public void viewRegistrationStatus() {
        registrationManager.displayRegistrationStatus();
    }

    /**
     * Books a flat for an eligible applicant in the officer's project.
     * 
     * @param scanner a {@link Scanner} for input
     */
    @Override
    public void bookFlat(Scanner scanner) {
        List<Applicant> applicants = projectManager.findAllApplicants(undertakenProjects);
        bookingManager.bookFlat(scanner, applicants);
    }

    /**
     * Updates the officer's list of undertaken projects from the master project list.
     * 
     * @param projectList list of all projects
     */
    public void updateUndertakenProjects(List<Project> projectList) {
        this.undertakenProjects = projectList.stream()
            .filter(project -> project.getOfficersStr().contains(this.getNric()))
            .collect(Collectors.toList());
    }

    /**
     * Displays the interactive console interface for an officer.
     * 
     * @param scanner a {@link Scanner} for user input
     * @param projectList the full list of projects
     * @return "logout" if user logs out, "quit" if user quits
     */
    @Override
    public String showInterface(Scanner scanner, List<Project> projectList) {
        fillEligibleProjects(projectList);
        updateUndertakenProjects(projectList);

        while (true) {
            UIHelper.printHeader("OFFICER MENU");
            UIHelper.printSubHeader("Applicant Functions");
            System.out.println("1. Show Eligible Projects for Application");
            System.out.println("2. Change Filters");
            System.out.println("3. Apply For a Project");
            System.out.println("4. View Applied Project");
            System.out.println("5. Request for withdrawal");
            System.out.println("6. Inquiries");
            UIHelper.printSubHeader("Officer Functions");
            System.out.println("7. Show the undertaken projects");
            System.out.println("8. View and Reply Inquiries");
            System.out.println("9. Register for Project");
            System.out.println("10. View Registration Status");
            System.out.println("11. Book Flat for Applicant");
            UIHelper.printProjectFooter();
            System.out.println("12. Reset Password");
            System.out.println("13. Logout");
            System.out.println("14. Quit");
            System.out.print("Choice: ");
            
            String input = scanner.nextLine();
            switch (input) {
                case "1": fillEligibleProjects(projectList); viewProjects(); break;
                case "2": setProjectFilters(scanner); break;
                case "3": applyForProject(scanner); break;
                case "4": viewApplicationStatus(); break;
                case "5": withdrawApplication(); break;
                case "6": showInquiryInterface(scanner, projectList); break;
                case "7": updateUndertakenProjects(projectList); viewUndertakenProjects(); break;
                case "8": updateUndertakenProjects(projectList); replyToInquiries(scanner); break;
                case "9": registerAsOfficer(scanner, projectList); break;
                case "10": viewRegistrationStatus(); break;
                case "11": bookFlat(scanner); break;
                case "12": return resetPassword(scanner);
                case "13": return "logout";
                case "14": return "quit";
                default: System.out.println("Invalid choice.");
            }
        }
    }
}
