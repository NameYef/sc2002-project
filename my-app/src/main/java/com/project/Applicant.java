package com.project;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Applicant extends User implements IApplicant {

    private String applicationStatus;
    private String flatTypeBooked; // type1 or type2, set by Officer
    private List<String> inquiries; // Fixed: Initialized in the constructor
    private Project appliedProject;
    private String appliedType; // stored this just in case, value can only be type1 or type2
    private List<Project> elligibleProjects;

    public Applicant(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
        this.applicationStatus = "N/A";
        this.inquiries = new ArrayList<>();  // Fixed: Properly initialized
        this.elligibleProjects = new ArrayList<>();
    }

    public String getApplicationStatus() { return applicationStatus; }
    public String getPassword() { return password; }
    
    private boolean isEligibleForFlatType(String flatType) {
        if (maritalStatus.equals("Married") && age >= 21) {
            return true;
        }
        return maritalStatus.equals("Single") && age >= 35 && flatType.equals("2-Room");
    }
    
    public void fillElligibleProjects(List<Project> projectList) {
        elligibleProjects.clear(); 
    
        LocalDate today = LocalDate.now();
        for (Project project : projectList) {
            if (!project.isVisible()) continue;
            
            // Uncomment below if you want to also consider open dates and close dates

            // if (today.isBefore(project.getOpenDate()) || today.isAfter(project.getCloseDate())) {
            //     continue; // Skip projects that aren't open yet or already closed
            // }

            boolean type1Eligible = isEligibleForFlatType(project.getType1());
            boolean type2Eligible = isEligibleForFlatType(project.getType2());
    
            if (type1Eligible || type2Eligible) {
                elligibleProjects.add(project);
            }
        }
    }
    
    private void displayFlatType(Project project, String typeLabel, String flatType, int units, double price) {
        if (!isEligibleForFlatType(flatType)) return;
    
        System.out.println(project.getName());
        System.out.println("Neighborhood: " + project.getNeighborhood());
        System.out.println(typeLabel + ": " + flatType);
        System.out.println("Available Units: " + units);
        System.out.println("Selling Price: " + price);
        System.out.println("Application Start Date: " + project.getOpenDate());
        System.out.println("Application Close Date: " + project.getCloseDate());
        System.out.println("---------------------------");
    }
    

    @Override
    public void viewProjects() {
        System.out.println("Viewing available projects for Applicant (" + maritalStatus + ", " + age + " years old).");

        for (Project project : elligibleProjects) {
            displayFlatType(project, "Type1", project.getType1(), project.getNoType1(), project.getPriceType1());
            displayFlatType(project, "Type2", project.getType2(), project.getNoType2(), project.getPriceType2());
        }
    }

    @Override
    public void applyForProject(Scanner scanner) {
        if (appliedProject != null) {
            System.out.println("You have already applied for a project.");
            return;
        }

        System.out.println("Enter the name of the project to apply (or type 'exit' to cancel):");
        String inputProject = scanner.nextLine().trim();

        if (inputProject.equalsIgnoreCase("exit")) return;

        for (Project project : elligibleProjects) {
            if (project.getName().equalsIgnoreCase(inputProject)) {
                appliedProject = project;
                break;
            }
        }

        if (appliedProject == null) {
            System.out.println("Invalid project name.");
            return;
        }

        System.out.println("Select flat type: 1 for Type1, 2 for Type2");
        String inputType = scanner.nextLine().trim();

        switch (inputType) {
            case "1":
                if (!isEligibleForFlatType(appliedProject.getType1()) || appliedProject.getNoType1() <= 0) {
                    System.out.println("Invalid choice.");
                    appliedProject = null;
                } else {
                    appliedType = "type1";
                    applicationStatus = "pending";
                    System.out.println("Successfully applied for " + appliedProject.getName() + " (" + appliedProject.getType1() + ")");
                }
                break;

            case "2":
                if (!isEligibleForFlatType(appliedProject.getType2()) || appliedProject.getNoType2() <= 0) {
                    System.out.println("Invalid choice.");
                    appliedProject = null;
                } else {
                    appliedType = "type2";
                    applicationStatus = "pending";
                    System.out.println("Successfully applied for " + appliedProject.getName() + " (" + appliedProject.getType2() + ")");
                }
                break;

            default:
                System.out.println("Invalid input.");
                appliedProject = null;
                break;
        }
    }

    @Override
    public void viewApplicationStatus() {
        if (appliedProject != null) {
            System.out.println("Applied for " + appliedProject.getName() + " " + appliedType);
        }
        System.out.println("Application Status: " + this.applicationStatus);
    }

    @Override
    public void withdrawApplication() {
        if (!this.applicationStatus.equals("Booked")) {
            this.applicationStatus = "Withdrawn";
            System.out.println("Application has been withdrawn.");
        } else {
            System.out.println("Cannot withdraw after booking a flat.");
        }
    }

    @Override
    public void submitInquiry(String inquiry) {
        inquiries.add(inquiry);
        System.out.println("Submitted Inquiry: " + inquiry);
    }

    @Override
    public void viewInquiry() {
        if (inquiries.isEmpty()) {
            System.out.println("No inquiries available.");
        } else {
            System.out.println("Submitted Inquiries:");
            for (String inquiry : inquiries) {
                System.out.println(inquiry);
            }
        }
    }

    @Override
    public void editInquiry(String newInquiry) {
        if (!inquiries.isEmpty()) {
            inquiries.set(inquiries.size() - 1, newInquiry);
            System.out.println("Updated Inquiry: " + newInquiry);
        } else {
            System.out.println("No inquiries available to edit.");
        }
    }

    @Override
    public void deleteInquiry() {
        if (!inquiries.isEmpty()) {
            inquiries.remove(inquiries.size() - 1);
            System.out.println("Last inquiry deleted.");
        } else {
            System.out.println("No inquiries to delete.");
        }
    }

    @Override
    public String getRole() {
        return "Applicant";
    }

    @Override
    public String showInterface(Scanner scanner, List<Project> projectList) {
        if (this.elligibleProjects.isEmpty()) {
            this.fillElligibleProjects(projectList);
        }
        while (true) {
            System.out.println(" Applicant Menu:");
            System.out.println("1. View Projects");
            System.out.println("2. Apply For a Project");
            System.out.println("3. View Applied Project"); 
            System.out.println("4. Request for withdrawal");
            System.out.println("5. Enquiries");
            System.out.println("6. Reset Password");
            System.out.println("7. Logout");
            System.out.println("8. Quit");
            System.out.print("Choice: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    viewProjects();
                    break;
                case "2":
                    applyForProject(scanner);
                    break;
                case "3":
                    viewApplicationStatus();
                    break;
                case "4":
                    break;
                case "5":
                    break;
                case "6":
                    return resetPassword(scanner);
                case "7":
                    return "logout";
                case "8":
                    return "quit";
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

}