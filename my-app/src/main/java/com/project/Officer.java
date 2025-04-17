package com.project;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.time.LocalDate;
// import java.util.stream.Stream;
import java.util.List;
public class Officer extends Applicant{

    List<Project> undertakenProjects;


    public Officer(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
    }


    @Override
    public String getRole() {
        return "Officer";
    }

    
    public void viewUndertakenProjects() {
        for (int i=0; i < undertakenProjects.size(); i++) {
            System.out.println("[" + (i+1) + "] " + undertakenProjects.get(i).getName());
        }
    }
    
    @Override
    public void fillElligibleProjects(List<Project> projectList) {
        elligibleProjects.clear(); 
        LocalDate today = LocalDate.now();

        for (Project project : projectList) {
            if (!project.isVisible()) continue;

            // Skip if this officer is already handling the project
            if (project.getOfficers().contains(this.name)) {
                System.out.println("SKipping");
                continue;}

            // Optionally check open/close dates
            // if (today.isBefore(project.getOpenDate()) || today.isAfter(project.getCloseDate())) continue;

            boolean type1Eligible = isEligibleForFlatType(project.getType1());
            boolean type2Eligible = isEligibleForFlatType(project.getType2());

            if (type1Eligible || type2Eligible) {
                elligibleProjects.add(project);
            }
        }
    }

    public void replyToInquiry(Scanner scanner) {
        System.out.println("\n--- Reply to Inquiries ---");
    
        for (int i = 0 ; i < undertakenProjects.size(); i++) {
            System.out.println("[" + (i+1) + "] " + undertakenProjects.get(i).getName());
        }
    
        System.out.print("Select project index to reply to: ");
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
        System.out.println("Reply recorded.");
    }
    
    
    @Override
    public String showInterface(Scanner scanner, List<Project> projectList) {
        this.fillElligibleProjects(projectList);
        this.undertakenProjects = projectList.stream().filter(obj->obj.getOfficers().contains(this.name)).collect(Collectors.toList());
        while (true) {
            System.out.println("Officer Menu:");
            System.out.println("Applicant functions");
            System.out.println("1. Show Elligible Projects for Application");
            System.out.println("2. Change Filters");
            System.out.println("3. Apply For a Project");
            System.out.println("4. View Applied Project"); 
            System.out.println("5. Request for withdrawal");
            System.out.println("6. Enquiries");

            System.out.println("Officer functions");
            System.out.println("7. Show the undertaken projects");
            System.out.println("8. View and Reply Inquiries");




            System.out.println("-----------------");
            System.out.println("9. Reset Password");
            System.out.println("10. Logout");
            System.out.println("11. Quit");
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
                    replyToInquiry(scanner);
                    break;
                case "9":
                    return resetPassword(scanner);
                case "10":
                    return "logout";
                case "11":
                    return"quit";
                default:
                    System.out.println("Invalid choice.");
            }
            
         
        }
        
        
    }
}


