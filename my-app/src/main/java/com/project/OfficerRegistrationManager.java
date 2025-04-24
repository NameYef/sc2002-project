package com.project;

import java.util.Scanner;
import java.util.List;

/**
 * Manages the registration process of an {@link Officer} to a {@link Project}.
 */
public class OfficerRegistrationManager {
    private Officer officer;

    /**
     * Constructs an OfficerRegistrationManager for a specific officer.
     *
     * @param officer the officer to manage registration for
     */
    public OfficerRegistrationManager(Officer officer) {
        this.officer = officer;
    }

    /**
     * Initiates the officer registration process by prompting the user to select a project.
     * 
     * <p>Performs the following checks before registration:</p>
     * <ul>
     *   <li>If the officer already has a pending registration</li>
     *   <li>If the selected project has available officer slots</li>
     *   <li>If the officer is already registered or has applied to the project</li>
     * </ul>
     *
     * @param scanner         the {@link Scanner} used to read user input
     * @param projectList     the list of available projects for registration
     * @param pendingOfficers the list to which the officer will be added upon pending registration
     */
    public void registerAsOfficer(Scanner scanner, List<Project> projectList, List<Officer> pendingOfficers) {
        UIHelper.printSubHeader("Register as HDB Officer");

        if (officer.getRegistrationStatus().equals("Pending")) {
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

            boolean alreadyOfficer = selected.getOfficersStr().contains(officer.getNric());

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

    /**
     * Displays the current registration status of the officer and the project
     * they have registered for, if any.
     */
    public void displayRegistrationStatus() {
        System.out.println("Officer Registration Status: " + officer.getRegistrationStatus());
        Project registeredProject = officer.getRegisteredProject();
        if (registeredProject != null) {
            System.out.println("Registered Project: " + registeredProject.getName());
        }
    }
}
