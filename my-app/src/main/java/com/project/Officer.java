package com.project;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.time.LocalDate;
// import java.util.stream.Stream;
import java.util.List;
import java.util.ArrayList;
public class Officer extends Applicant{

    List<Project> undertakenProjects;
    private String registrationStatus = "Not Registered"; // "Not Registered", "Pending", "Approved", "Rejected"
    private Project registeredProject = null;
    public static List<Officer> pendingOfficer = new ArrayList<>();

    public Officer(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
    }


    @Override
    public String getRole() {
        return "Officer";
    }

    public String getRegistrationStatus() {
        return this.registrationStatus;
    }

    public void setRegistrationStatus(String status) {
        this.registrationStatus = status;
    }
    public static List<Officer> getPendingOfficers() {
        return pendingOfficer;
    }


    public void viewUndertakenProjects() {
        for (int i=0; i < undertakenProjects.size(); i++) {
            System.out.println("[" + (i+1) + "] " + undertakenProjects.get(i).getName());
            System.out.println("Neighborhood: " + undertakenProjects.get(i).getNeighborhood());
            System.out.println("Type1: " + undertakenProjects.get(i).getType1());
            System.out.println("Available Units: " + undertakenProjects.get(i).getNoType1());
            System.out.println("Selling Price: " + undertakenProjects.get(i).getPriceType1());
            System.out.println("Type2: " + undertakenProjects.get(i).getType2());
            System.out.println("Available Units: " + undertakenProjects.get(i).getNoType2());
            System.out.println("Selling Price: " + undertakenProjects.get(i).getPriceType2());
            System.out.println("Application Start Date: " + undertakenProjects.get(i).getOpenDate());
            System.out.println("Application Close Date: " + undertakenProjects.get(i).getCloseDate());
            System.out.println("Officers: " + 
                undertakenProjects.get(i).getOfficers().stream()
                    .map(Officer::getName) // extract the name
                    .collect(Collectors.joining(", "))
            );
            System.out.println("Manager: " + undertakenProjects.get(i).getManager().getName());
            UIHelper.printProjectFooter();
        }
    }
    
    @Override
    public void fillElligibleProjects(List<Project> projectList) {
        elligibleProjects.clear(); 
        LocalDate today = LocalDate.now();

        for (Project project : projectList) {
            if (!project.isVisible()) continue;

            // Skip if this officer is already handling the project
            if (project.getOfficersStr().contains(this.nric)) {
                // System.out.println("SKipping");
                continue;}

            // Optionally check open/close dates
            if (today.isBefore(project.getOpenDate()) || today.isAfter(project.getCloseDate())) continue;

            boolean type1Eligible = isEligibleForFlatType(project.getType1());
            boolean type2Eligible = isEligibleForFlatType(project.getType2());

            if (type1Eligible || type2Eligible) {
                elligibleProjects.add(project);
            }
        }
    }

    public void replyToInquiries(Scanner scanner) {
        UIHelper.printSubHeader("Reply to Inquiries");
        
    
        for (int i = 0 ; i < undertakenProjects.size(); i++) {
            System.out.println("[" + (i+1) + "] " + undertakenProjects.get(i).getName());
        }
    
        System.out.print("Select project index to reply to: ");
        try {
        int projectIndex = Integer.parseInt(scanner.nextLine());
        if (projectIndex < 1 || projectIndex > undertakenProjects.size()) {
            System.out.println("Invalid index.");
            return;
        } 
    
        Project selectedProject = undertakenProjects.get(projectIndex-1);
        List<Inquiry> inquiries = selectedProject.getInquiries();
    
        if (inquiries.isEmpty()) {
            System.out.println("No inquiries in this project.");
            return;
        }
    
        for (int i = 0; i < inquiries.size(); i++) {
            Inquiry inquiry = inquiries.get(i);
            System.out.println("[" + (i+1) + "] " + inquiry.getMessage());
            System.out.println("     ↳ Reply: " + (inquiry.isReplied() ? inquiry.getReply() : "(No reply yet)"));
        }
    
        System.out.print("Select inquiry index to reply to: ");
        
        int inquiryIndex = Integer.parseInt(scanner.nextLine());
        if (inquiryIndex < 1 || inquiryIndex > inquiries.size()) {
            System.out.println("Invalid index.");
            return;
        }
    
        Inquiry selectedInquiry = inquiries.get(inquiryIndex-1);
        if (selectedInquiry.isReplied()) {
            System.out.println("This inquiry has already been replied to.");
            return;
        }
    
        System.out.print("Enter your reply: ");
        String reply = scanner.nextLine();
        selectedInquiry.setReply(reply);
        System.out.println("Reply recorded.");} catch (NumberFormatException e) {
            System.out.println("Invalid input");
        }
    }
    
    public void registerAsOfficer(Scanner scanner, List<Project> projectList) {
        UIHelper.printSubHeader("Register as HDB Officer");
        if (this.registrationStatus.equals("Registered") || this.registrationStatus.equals("Approved")) {
            System.out.println("You have registration pending / already registered");
            return;
        }
        for (int i = 0; i < projectList.size(); i++) {
            Project p = projectList.get(i);
            
            System.out.println("[" + (i + 1) + "] " + p.getName());
            
        }

        System.out.print("Select project number to register for: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            if (idx < 0 || idx >= projectList.size()) {
                System.out.println("Invalid index.");
                return;
            }

            Project selected = projectList.get(idx);
            if (selected.getOfficers().size() >= selected.getOfficerSlot()) {
                System.out.println("Max number of officers for this project reached already");
                return;
            }
            // check if officer already in officers list
            boolean alreadyOfficer = selected.getOfficersStr().contains(this.nric);

            // check if officer already applied as applicant
            boolean alreadyApplicant = false;
            for (Applicant app : selected.getApplicants()) {
                if (app.getNric().equalsIgnoreCase(this.nric)) {
                    alreadyApplicant = true;
                    break;
                }
            }

            if (alreadyOfficer || alreadyApplicant) {
                System.out.println("You're already registered or applied to this project.");
                return;
            }
            else{
                registeredProject = selected;
                pendingOfficer.add(this);
                registrationStatus = "Pending";
                System.out.println("Registration submitted. Awaiting manager approval.");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    public void viewRegistrationStatus() {
        System.out.println("Officer Registration Status: " + registrationStatus);
        if (registeredProject != null) {
            System.out.println("Registered Project: " + registeredProject.getName());
        }
    }

    public void bookFlat(Scanner scanner, List<Applicant> applicants) {
        UIHelper.printSubHeader("Flat Booking");

        System.out.print("Enter applicant NRIC: ");
        String nric = scanner.nextLine().trim();

        for (Applicant app : applicants) {
            if (app.getNric().equalsIgnoreCase(nric)) {
                if (!app.getApplicationStatus().equals("Successful")) {
                    System.out.println("This applicant is not approved for booking.");
                    return;
                }

                Project proj = app.appliedProject;
                System.out.println("Flat Type applied: " + app.appliedType);

                if (app.appliedType.equals("type1") && proj.getNoType1() > 0) {
                    proj.setNoType1(proj.getNoType1() - 1);
                    app.applicationStatus = "Booked";
                    app.flatTypeBooked = proj.getType1();
                } else if (app.appliedType.equals("type2") && proj.getNoType2() > 0) {
                    proj.setNoType2(proj.getNoType2() - 1);
                    app.applicationStatus = "Booked";
                    app.flatTypeBooked = proj.getType2();
                } else {
                    System.out.println("No more units left for the selected flat type.");
                    return;
                }

                System.out.println("Booking confirmed");
                generateReceipt(app);
                return;
            }
        }

        System.out.println("Applicant not found.");
    }

    public void generateReceipt(Applicant app) {
        UIHelper.printSubHeader("Booking Receipt");

        System.out.println("Name: " + app.getName());
        System.out.println("NRIC: " + app.getNric());
        System.out.println("Age: " + app.getAge());
        System.out.println("Marital Status: " + app.getMaritalStatus());
        System.out.println("Flat Type Booked: " + app.flatTypeBooked);
        System.out.println("Project Name: " + app.appliedProject.getName());
        System.out.println("Neighborhood: " + app.appliedProject.getNeighborhood());
        UIHelper.printDivider();
    }
    @Override
    public String showInterface(Scanner scanner, List<Project> projectList) {
        this.fillElligibleProjects(projectList);
        this.undertakenProjects = projectList.stream().filter(obj->obj.getOfficersStr().contains(this.nric)).collect(Collectors.toList());
        while (true) {
            UIHelper.printHeader("OFFICER MENU");
            UIHelper.printSubHeader("Aplicant Functions");
            
            System.out.println("1. Show Eligible Projects for Application");
            System.out.println("2. Change Filters");
            System.out.println("3. Apply For a Project");
            System.out.println("4. View Applied Project"); 
            System.out.println("5. Request for withdrawal");
            System.out.println("6. Enquiries");

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
                    bookFlat(scanner, findAllApplicants(projectList));
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
    private List<Applicant> findAllApplicants(List<Project> projects) {
        List<Applicant> result = new java.util.ArrayList<>();
        for (Project p : projects) {
            result.addAll(p.getApplicants());
        }
        return result;
    }
}


