package com.project;

import java.util.Scanner;
import java.util.List;

/**
 * The {@code InquiryResponseManager} class is responsible for managing and replying to 
 * inquiries submitted by applicants for projects that an officer is undertaking.
 * <p>
 * This class allows an {@link Officer} to view inquiries for their assigned projects 
 * and provide responses where applicable.
 */
public class InquiryResponseManager {

    /** The officer associated with this response manager. */
    private Officer officer;

    /**
     * Constructs an {@code InquiryResponseManager} for a specific officer.
     *
     * @param officer the officer responsible for responding to inquiries
     */
    public InquiryResponseManager(Officer officer) {
        this.officer = officer;
    }

    /**
     * Allows the officer to view and respond to inquiries for their undertaken projects.
     * The method displays a list of the officer’s projects, then lists the inquiries
     * for the selected project, and finally allows the officer to reply to a selected inquiry.
     *
     * @param scanner the {@code Scanner} object for reading user input
     * @param undertakenProjects the list of projects the officer is managing
     */
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
