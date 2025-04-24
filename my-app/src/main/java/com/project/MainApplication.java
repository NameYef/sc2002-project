package com.project;

import java.io.IOException;
import java.util.*;

/**
 * The {@code MainApplication} class serves as the entry point for the project management system.
 * <p>
 * It performs the following operations:
 * <ul>
 *     <li>Reads data from Excel files using {@link ExcelReader}</li>
 *     <li>Initializes the project, user, and inquiry lists</li>
 *     <li>Handles user login via {@link LoginService}</li>
 *     <li>Directs users to their respective interfaces</li>
 *     <li>Writes any updates back to Excel using {@link ExcelWriter}</li>
 * </ul>
 */
public class MainApplication {

    /**
     * The main method that launches the application.
     * <p>
     * It handles initialization of data, user authentication, and user interaction loop.
     * Upon quitting, it writes updated data back to the Excel files.
     *
     * @param args the command-line arguments (not used)
     */
    public static void main(String[] args) {
        boolean run = true;
        Scanner scanner = new Scanner(System.in);
        ExcelReader reader = new ExcelReader();
        ExcelWriter writer = new ExcelWriter();
        try {
            // Load data from Excel
            List<Project> projectList = reader.readProjects("./data/ProjectList.xlsx");
            List<Applicant> applicantList = reader.readApplicants("./data/ApplicantList.xlsx");
            List<Manager> managerList = reader.readManagers("./data/ManagerList.xlsx");
            List<Officer> officerList = reader.readOfficers("./data/OfficerList.xlsx");
            List<Inquiry> inquiryList = reader.readInquiries("./data/InquiryList.xlsx", applicantList);
            reader.readApplicantStatusList("./data/ApplicantStatusList.xlsx", applicantList, projectList);
            reader.readOfficerStatusList("./data/OfficerStatusList.xlsx", officerList, projectList);

            reader.resolveProjectReferences(projectList, managerList, officerList, applicantList, inquiryList);

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

            // Rebuild inquiries list from projects and write everything back to Excel
            inquiryList.clear();
            for (Project project : projectList) {
                inquiryList.addAll(project.getInquiries());
            }

            writer.writeApplicants("./data/ApplicantList.xlsx", applicantList);
            writer.writeManagers("./data/ManagerList.xlsx", managerList);
            writer.writeOfficers("./data/OfficerList.xlsx", officerList);
            writer.writeProjects("./data/ProjectList.xlsx", projectList);
            writer.writeInquiries("./data/InquiryList.xlsx", inquiryList);
            writer.writeApplicantStatusList("./data/ApplicantStatusList.xlsx", applicantList);
            writer.writeOfficerStatusList("./data/OfficerStatusList.xlsx", officerList);

        } catch (IOException e) {
            System.out.println("Error reading Excel files: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
