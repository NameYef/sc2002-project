package com.project;

import java.io.IOException;
import java.util.*;

public class MainApplication {

    public static void main(String[] args) {
        boolean run = true;
        Scanner scanner = new Scanner(System.in);
        ExcelReader reader = new ExcelReader();

        try {
            // Load data from Excel
            List<Project> projectList = reader.readProjects("./data/ProjectList.xlsx");
            List<Applicant> applicantList = reader.readApplicants("./data/ApplicantList.xlsx");
            List<Manager> managerList = reader.readManagers("./data/ManagerList.xlsx");
            List<Officer> officerList = reader.readOfficers("./data/OfficerList.xlsx");

            for (Project project : projectList) {
                // Set Manager
                String managerNRIC = project.getManagerStr(); // or however it's stored
                for (Manager manager : managerList) {
                    if (manager.getNric().equals(managerNRIC)) {
                        project.setManager(manager);
                        break;
                    }
                }
            
                // Set Officers
                List<String> officerNRICs = project.getOfficersStr(); // String NRICs from Excel
                List<Officer> officers = new ArrayList<>();
                for (String officerNRIC : officerNRICs) {
                    for (Officer officer : officerList) {
                        if (officer.getNric().equals(officerNRIC)) {
                            officers.add(officer);
                            break;
                        }
                    }
                }
                project.setOfficers(officers); // Final list of Officer objects
            }
            // Combine all users for login
            List<User> allUsers = new ArrayList<>();
            allUsers.addAll(applicantList);
            allUsers.addAll(managerList);
            allUsers.addAll(officerList);

            LoginService loginService = new LoginService(allUsers);
            User currentUser = null;
            
            while (run) {
                currentUser = loginService.login(scanner);
                if (currentUser == null) continue;

                boolean loggedIn = true;
                while (loggedIn) {
                    String action = currentUser.showInterface(scanner, projectList);
                    switch (action) {
                        case "logout" -> {
                            loggedIn = false;
                            currentUser = null;
                        }
                        case "quit" -> {
                            loggedIn = false;
                            run = false;
                        }
                    }
                }
            }

            System.out.println("Terminating...");

        } catch (IOException e) {
            System.out.println("Error reading Excel files: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
