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
            List<Inquiry> inquiryList = reader.readInquiries("./data/InquiryList.xlsx", applicantList);


            reader.resolveProjectReferences(projectList, managerList, officerList, applicantList, inquiryList);
            System.out.println(projectList.get(0).getOfficersStr());

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

            inquiryList.clear();
            for (Project project : projectList) {
                inquiryList.addAll(project.getInquiries());
            }
            reader.writeApplicants("./data/ApplicantList.xlsx", applicantList);
            reader.writeManagers("./data/ManagerList.xlsx", managerList);
            reader.writeOfficers("./data/OfficerList.xlsx", officerList);
            reader.writeProjects("./data/ProjectList.xlsx", projectList);
            reader.writeInquiries("./data/InquiryList.xlsx", inquiryList);

        } catch (IOException e) {
            System.out.println("Error reading Excel files: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
