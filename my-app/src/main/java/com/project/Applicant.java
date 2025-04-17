package com.project;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Applicant extends User {

    protected String applicationStatus;
    protected String flatTypeBooked; // type1 or type2, set by Officer
    protected Project appliedProject;
    protected String appliedType; // stored this just in case, value can only be type1 or type2
    protected List<Project> elligibleProjects;

    public Applicant(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
        this.applicationStatus = "Unsuccessful";
        this.elligibleProjects = new ArrayList<>();
    }

    public String getApplicationStatus() { return applicationStatus; }
    public String getPassword() { return password; }
    
    protected boolean isEligibleForFlatType(String flatType) {
        if (maritalStatus.equals("Married") && age >= 21) {
            return true;
        }
        return maritalStatus.equals("Single") && age >= 35 && flatType.equals("2-Room");
    }
    
    protected void fillElligibleProjects(List<Project> projectList) {
        elligibleProjects.clear(); 
    
        LocalDate today = LocalDate.now();
        for (Project project : projectList) {
            if (!project.isVisible()) continue;

            // Uncomment below 3 lines if you want to also consider open dates and close dates

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
    
    protected void displayFlatType(Project project, String typeLabel, String flatType, int units, double price) {
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
    

    // @Override
    protected void viewProjects() {
        System.out.println("Viewing available projects for " + this.getRole() + " (" + maritalStatus + ", " + age + " years old).");
        System.out.println("Current Filters -> Neighborhood: " + 
            (filterNeighborhood == null ? "All" : filterNeighborhood) + 
            ", Flat Type: " + (filterFlatType == null ? "All" : filterFlatType));
    
        for (Project project : elligibleProjects) {
            // Filter by neighborhood if set
            if (filterNeighborhood != null && !project.getNeighborhood().equalsIgnoreCase(filterNeighborhood)) {
                continue;
            }
    
            // Filter by flat type before displaying
            if (filterFlatType == null || filterFlatType.equalsIgnoreCase(project.getType1())) {
                displayFlatType(project, "Type1", project.getType1(), project.getNoType1(), project.getPriceType1());
            }
            if (filterFlatType == null || filterFlatType.equalsIgnoreCase(project.getType2())) {
                displayFlatType(project, "Type2", project.getType2(), project.getNoType2(), project.getPriceType2());
            }
        }
    }
    
    protected void setProjectFilters(Scanner scanner) {
        System.out.print("Enter neighborhood to filter by (or press Enter for all): ");
        String inputNeighborhood = scanner.nextLine().trim();
        filterNeighborhood = inputNeighborhood.isEmpty() ? null : inputNeighborhood;
    
        System.out.print("Enter flat type to filter by (2-Room / 3-Room or press Enter for all): ");
        String inputFlatType = scanner.nextLine().trim();
        filterFlatType = inputFlatType.isEmpty() ? null : inputFlatType;
    
        System.out.println("Filters updated.");
    }
    

    // @Override
    public void applyForProject(Scanner scanner) {
        if (this.appliedProject != null || !this.applicationStatus.equals("Unsuccessful")) {
            System.out.println("You have already applied for a project.");
            return;
        }

        System.out.println("Enter the name of the project to apply (or type 'exit' to cancel):");
        String inputProject = scanner.nextLine().trim();

        if (inputProject.equalsIgnoreCase("exit")) return;

        for (Project project : elligibleProjects) {
            if (project.getName().equalsIgnoreCase(inputProject)) {
                this.appliedProject = project;
                break;
            }
        }

        if (this.appliedProject == null) {
            System.out.println("Invalid project name.");
            return;
        }

        System.out.println("Select flat type: 1 for Type1, 2 for Type2");
        String inputType = scanner.nextLine().trim();

        switch (inputType) {
            case "1":
                if (!isEligibleForFlatType(this.appliedProject.getType1()) || this.appliedProject.getNoType1() <= 0) {
                    System.out.println("Invalid choice.");
                    this.appliedProject = null;
                } else {
                    appliedType = "type1";
                    applicationStatus = "Pending";
                    this.appliedProject.addApplicant(this);
                    System.out.println("Successfully applied for " + this.appliedProject.getName() + " (" + this.appliedProject.getType1() + ")");
                }
                break;

            case "2":
                if (!isEligibleForFlatType(this.appliedProject.getType2()) || this.appliedProject.getNoType2() <= 0) {
                    System.out.println("Invalid choice.");
                    this.appliedProject = null;
                } else {
                    appliedType = "type2";
                    applicationStatus = "Pending";
                    this.appliedProject.addApplicant(this);
                    System.out.println("Successfully applied for " + this.appliedProject.getName() + " (" + this.appliedProject.getType2() + ")");
                }
                break;

            default:
                System.out.println("Invalid input.");
                this.appliedProject = null;
                break;
        }
    }

    // @Override
    public void viewApplicationStatus() {
        if (this.appliedProject != null) {
            System.out.println("Applied for " + this.appliedProject.getName() + " " + appliedType);
        }
        System.out.println("Application Status: " + this.applicationStatus);
    }

    // @Override
    public void withdrawApplication() {
        if (this.appliedProject != null) {
            this.applicationStatus = "Unsuccessful";
            // this.appliedProject = null;
            // this.appliedType = "";
            this.appliedProject.removeApplicant(this); 

            System.out.println("Application has been withdrawn.");
        } else {
            System.out.println("You have not applied for a project yet.");
        }
    }

    private void submitInquiry(Scanner scanner) {
        System.out.println("Available projects to inquire:");
        for (int i = 0; i < elligibleProjects.size(); i++) {
            System.out.println((i + 1) + ". " + elligibleProjects.get(i).getName());
        }
    
        System.out.print("Select project number: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;
    
        if (index < 0 || index >= elligibleProjects.size()) {
            System.out.println("Invalid selection.");
            return;
        }
    
        Project selectedProject = elligibleProjects.get(index);
    
        System.out.print("Enter your inquiry: ");
        String message = scanner.nextLine();
    
        Inquiry inquiry = new Inquiry(this, message);
        selectedProject.addInquiry(inquiry);
    
        System.out.println("Inquiry submitted!");
    }
    
    private void viewInquiries(List<Project> projectList) {
        System.out.println("Your Inquiries:");
    
        for (Project project : projectList) {
            for (Inquiry inquiry : project.getInquiries()) {
                if (inquiry.getApplicant() == this) {
                    System.out.println("\n[Project: " + project.getName() + "]");
                    System.out.println(inquiry);
                }
            }
        }
    }
    
    private void editInquiry(Scanner scanner, List<Project> projectList) {
        // temp array to store enquiries that have not been replied yet
        List<Inquiry> myInquiries = new ArrayList<>();
        for (Project project : projectList) {
            for (Inquiry inquiry : project.getInquiries()) {
                if (inquiry.getApplicant() == this && inquiry.getReply() == null) {
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
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return;
        }
    

    


    }

    public void deleteInquiry(Scanner scanner, List<Project> projectList) {
        List<Inquiry> myInquiries = new ArrayList<>();
        List<Project> inquiryProjects = new ArrayList<>();
    
        // Collect all inquiries made by this applicant
        for (Project project : projectList) {
            for (Inquiry inquiry : project.getInquiries()) {
                if (inquiry.getApplicant() == this) {
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
        System.out.println("Your inquiries:");
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
            System.out.println("\n--- Inquiry Menu ---");
            System.out.println("1. Submit Inquiry");
            System.out.println("2. View My Inquiries");
            System.out.println("3. Edit Inquiry");
            System.out.println("4. Delete Inquiry");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();
    
            switch (choice) {
                case "1":
                    submitInquiry(scanner);
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
    

    // @Override
    public String getRole() {
        return "Applicant";
    }

    // @Override
    public String showInterface(Scanner scanner, List<Project> projectList) {

        this.fillElligibleProjects(projectList);
        
        while (true) {
            System.out.println(" Applicant Menu:");
            System.out.println("1. View Projects");
            System.out.println("2. Change Filters");
            System.out.println("3. Apply For a Project");
            System.out.println("4. View Applied Project"); 
            System.out.println("5. Request for withdrawal");
            System.out.println("6. Enquiries");
            System.out.println("7. Reset Password");
            System.out.println("8. Logout");
            System.out.println("9. Quit");
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
                    return resetPassword(scanner);
                case "8":
                    return "logout";
                case "9":
                    return "quit";
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

}