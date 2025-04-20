package com.project;

import java.util.Scanner;
import java.util.List;

// Manager class for handling officer registration
public class OfficerRegistrationManager {
    private Officer officer;
    
    public OfficerRegistrationManager(Officer officer) {
        this.officer = officer;
    }
    
    public void registerAsOfficer(Scanner scanner, List<Project> projectList, List<Officer> pendingOfficers) {
        UIHelper.printSubHeader("Register as HDB Officer");
        
        if (officer.getRegistrationStatus().equals("Pending")){
            System.out.println("You have registration pending");
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
            
            // Check if officer already in officers list
            boolean alreadyOfficer = selected.getOfficersStr().contains(officer.getNric());

            // Check if officer already applied as applicant
            boolean alreadyApplicant = false;
            for (Applicant app : selected.getApplicants()) {
                if (app.getNric().equalsIgnoreCase(officer.getNric())) {
                    alreadyApplicant = true;
                    break;
                }
            }

            if (alreadyOfficer || alreadyApplicant) {
                System.out.println("You're already registered or applied to this project.");
                return;
            } else {
                officer.setRegisteredProject(selected);
                pendingOfficers.add(officer);
                officer.setRegistrationStatus("Pending");
                System.out.println("Registration submitted. Awaiting manager approval.");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }
    
    public void displayRegistrationStatus() {
        System.out.println("Officer Registration Status: " + officer.getRegistrationStatus());
        Project registeredProject = officer.getRegisteredProject();
        if (registeredProject != null) {
            System.out.println("Registered Project: " + registeredProject.getName());
        }
    }
}
