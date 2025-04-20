package com.project;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

public class Officer extends Applicant implements OfficerOperations {

    private List<Project> undertakenProjects;
    private String registrationStatus; // "Not Registered", "Pending", "Approved", "Rejected"
    private Project registeredProject = null;
    private static List<Officer> pendingOfficers = new ArrayList<>();
    private OfficerRegistrationManager registrationManager;
    private OfficerProjectManager projectManager;
    private BookingManager bookingManager;
    private InquiryResponseManager inquiryManager;

    public Officer(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
        this.registrationStatus = "Not Registered";
        this.undertakenProjects = new ArrayList<>();
        this.registrationManager = new OfficerRegistrationManager(this);
        this.projectManager = new OfficerProjectManager(this);
        this.bookingManager = new BookingManager(this);
        this.inquiryManager = new InquiryResponseManager(this);
    }

    @Override
    public String getRole() {
        return "Officer";
    }

    public Project getRegisteredProject() {
        return this.registeredProject;
    }
    
    public void setRegisteredProject(Project registeredProject) {
        this.registeredProject = registeredProject;
    }

    public String getRegistrationStatus() {
        return this.registrationStatus;
    }

    public void setRegistrationStatus(String status) {
        this.registrationStatus = status;
    }
    
    public static List<Officer> getPendingOfficers() {
        return pendingOfficers;
    }

    @Override
    public void viewUndertakenProjects() {
        projectManager.displayUndertakenProjects(this.undertakenProjects);
    }
    
    @Override
    public void fillEligibleProjects(List<Project> projectList) {
        // This method is inherited from Applicant but needs different behavior
        projectManager.fillEligibleProjects(projectList, this);
    }

    @Override
    public void replyToInquiries(Scanner scanner) {
        inquiryManager.replyToInquiries(scanner, undertakenProjects);
    }
    
    @Override
    public void registerAsOfficer(Scanner scanner, List<Project> projectList) {
        registrationManager.registerAsOfficer(scanner, projectList, pendingOfficers);
    }

    @Override
    public void viewRegistrationStatus() {
        registrationManager.displayRegistrationStatus();
    }

    @Override
    public void bookFlat(Scanner scanner) {
        List<Applicant> applicants = projectManager.findAllApplicants(undertakenProjects);
        bookingManager.bookFlat(scanner, applicants);
    }

    // Set the undertaken projects list based on projects where this officer is assigned
    public void updateUndertakenProjects(List<Project> projectList) {
        this.undertakenProjects = projectList.stream()
            .filter(project -> project.getOfficersStr().contains(this.getNric()))
            .collect(Collectors.toList());
    }

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
                case "1":
                    fillEligibleProjects(projectList);
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
                    viewUndertakenProjects();
                    break;
                case "8":
                    replyToInquiries(scanner);
                    break;
                case "9":
                    registerAsOfficer(scanner, projectList);
                    break;
                case "10":
                    viewRegistrationStatus(); 
                    break;
                case "11":
                    bookFlat(scanner);
                    break;
                case "12":
                    return resetPassword(scanner);
                case "13":
                    return "logout";
                case "14":
                    return "quit";
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}









