package com.project;
import java.util.Scanner;
import java.util.List;

// Manager class for handling inquiry responses
public class InquiryResponseManager {
    private Officer officer;
    
    public InquiryResponseManager(Officer officer) {
        this.officer = officer;
    }
    
    public void replyToInquiries(Scanner scanner, List<Project> undertakenProjects) {
        UIHelper.printSubHeader("Reply to Inquiries");
        
        if (undertakenProjects.isEmpty()) {
            System.out.println("You have no projects to manage inquiries for.");
            return;
        }
    
        for (int i = 0; i < undertakenProjects.size(); i++) {
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
            System.out.println("Reply recorded.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input");
        }
    }
}