package com.project;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
// Helper class to manage inquiries
class InquiryManager implements InquiryOperations{
    private Applicant applicant;
    
    public InquiryManager(Applicant applicant) {
        this.applicant = applicant;
    }
    
    public void submitInquiry(Scanner scanner, List<Project> eligibleProjects) {
        UIHelper.printProjectHeader("Available projects to enquire:");
        UIHelper.printDivider();
        for (int i = 0; i < eligibleProjects.size(); i++) {
            System.out.println((i + 1) + ". " + eligibleProjects.get(i).getName());
        }
        UIHelper.printDivider();
        System.out.print("Select project number: ");
        
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
        
            if (index < 0 || index >= eligibleProjects.size()) {
                System.out.println("Invalid selection.");
                return;
            }
        
            Project selectedProject = eligibleProjects.get(index);
        
            System.out.print("Enter your inquiry: ");
            String message = scanner.nextLine();
        
            Inquiry inquiry = new Inquiry(applicant, message, selectedProject.getName());
            selectedProject.addInquiry(inquiry);
        
            System.out.println("Inquiry submitted!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input");
        }
    }
    
    public void viewInquiries(List<Project> projectList) {
        UIHelper.printProjectHeader("Your Inquiries:");
    
        for (Project project : projectList) {
            for (Inquiry inquiry : project.getInquiries()) {
                if (inquiry.getApplicant() == applicant) {
                    System.out.println("\n[Project: " + project.getName() + "]");
                    System.out.println(inquiry);
                }
            }
        }
    }
    
    public void editInquiry(Scanner scanner, List<Project> projectList) {
        List<Inquiry> myInquiries = new ArrayList<>();
        for (Project project : projectList) {
            for (Inquiry inquiry : project.getInquiries()) {
                if (inquiry.getApplicant() == applicant && inquiry.getReply() == null) {
                    myInquiries.add(inquiry);
                }
            }
        }
    
        if (myInquiries.isEmpty()) {
            System.out.println("No editable (unreplied) inquiries.");
            return;
        }
    
        for (int i = 0; i < myInquiries.size(); i++) {
            System.out.println((i + 1) + ". " + myInquiries.get(i).getMessage());
        }
    
        System.out.print("Select inquiry to edit: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice < 1 || choice > myInquiries.size()) {
                System.out.println("Invalid selection.");
                return;
            }
            System.out.print("Enter new message: ");
            String newMsg = scanner.nextLine();
            myInquiries.get(choice-1).setMessage(newMsg);
            System.out.println("Inquiry updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }
    
    public void deleteInquiry(Scanner scanner, List<Project> projectList) {
        List<Inquiry> myInquiries = new ArrayList<>();
        List<Project> inquiryProjects = new ArrayList<>();
    
        // Collect all inquiries made by this applicant
        for (Project project : projectList) {
            for (Inquiry inquiry : project.getInquiries()) {
                if (inquiry.getApplicant() == applicant) {
                    myInquiries.add(inquiry);
                    inquiryProjects.add(project); // Track which project it belongs to
                }
            }
        }
    
        if (myInquiries.isEmpty()) {
            System.out.println("You have no inquiries to delete.");
            return;
        }
    
        // Display inquiries with indices
        UIHelper.printProjectHeader("Your Inquiries:");
        for (int i = 0; i < myInquiries.size(); i++) {
            Inquiry inq = myInquiries.get(i);
            System.out.println((i + 1) + ". [" + inquiryProjects.get(i).getName() + "] " + inq.getMessage());
            if (inq.getReply() != null) {
                System.out.println("   ↳ Reply: " + inq.getReply());
            }
        }
    
        // Ask which one to delete
        System.out.print("Enter the number of the inquiry you want to delete: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());
    
            if (choice < 1 || choice > myInquiries.size()) {
                System.out.println("Invalid choice.");
                return;
            }
    
            // Remove the inquiry from the correct project
            Project targetProject = inquiryProjects.get(choice-1);
            targetProject.getInquiries().remove(myInquiries.get(choice-1));
            System.out.println("Inquiry deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }
    
    public void showInquiryInterface(Scanner scanner, List<Project> projectList) {
        while (true) {
            UIHelper.printHeader("ENQUIRY MENU");
            System.out.println("1. Submit Inquiry");
            System.out.println("2. View My Inquiries");
            System.out.println("3. Edit Inquiry");
            System.out.println("4. Delete Inquiry");
            System.out.println("5. Exit");
            UIHelper.printProjectFooter();
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();
    
            switch (choice) {
                case "1":
                    submitInquiry(scanner, projectList);
                    break;
                case "2":
                    viewInquiries(projectList);
                    break;
                case "3":
                    editInquiry(scanner, projectList);
                    break;
                case "4":
                    deleteInquiry(scanner, projectList);
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
