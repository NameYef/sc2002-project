package com.project;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
// Helper class to display projects
class ProjectDisplayer {
    private Applicant applicant;
    protected String filterNeighborhood;
    protected String filterFlatType;
    
    public ProjectDisplayer(Applicant applicant) {
        this.applicant = applicant;
    }
    
    public void displayProjects(List<Project> eligibleProjects) {
        UIHelper.printProjectHeader("Available projects for " + applicant.getName() + " (" + 
                                   applicant.getMaritalStatus() + ", " + applicant.getAge() + " years old).");
        System.out.println("Current Filters -> Neighborhood: " + 
            (this.filterNeighborhood == null ? "All" : this.filterNeighborhood) + 
            ", Flat Type: " + (this.filterFlatType == null ? "All" : this.filterFlatType));
    
        for (Project project : eligibleProjects) {
            // Filter by neighborhood if set
            if (this.filterNeighborhood != null && !project.getNeighborhood().equalsIgnoreCase(this.filterNeighborhood)) {
                continue;
            }
    
            // Filter by flat type before displaying
            if (this.filterFlatType == null || this.filterFlatType.equalsIgnoreCase(project.getType1())) {
                displayFlatType(project, "Type1", project.getType1(), project.getNoType1(), project.getPriceType1());
            }
            if (this.filterFlatType == null || this.filterFlatType.equalsIgnoreCase(project.getType2())) {
                displayFlatType(project, "Type2", project.getType2(), project.getNoType2(), project.getPriceType2());
            }
        }
    }
    
    public void setFilters(Scanner scanner) {
        System.out.print("Enter neighborhood to filter by (or press Enter for all): ");
        String inputNeighborhood = scanner.nextLine().trim();
        this.filterNeighborhood = inputNeighborhood.isEmpty() ? null : inputNeighborhood;
    
        System.out.print("Enter flat type to filter by (2-Room / 3-Room or press Enter for all): ");
        String inputFlatType = scanner.nextLine().trim();
        this.filterFlatType = inputFlatType.isEmpty() ? null : inputFlatType;
    
        System.out.println("Filters updated.");
    }
    
    private void displayFlatType(Project project, String typeLabel, String flatType, int units, double price) {
        ProjectEligibilityChecker checker = new ProjectEligibilityChecker(applicant.getMaritalStatus(), applicant.getAge());
        if (!checker.isEligibleForFlatType(flatType)) return;
    
        System.out.println(project.getName());
        System.out.println("Neighborhood: " + project.getNeighborhood());
        System.out.println(typeLabel + ": " + flatType);
        System.out.println("Available Units: " + units);
        System.out.println("Selling Price: " + price);
        System.out.println("Application Start Date: " + project.getOpenDate());
        System.out.println("Application Close Date: " + project.getCloseDate());
        System.out.println("Officers: " + 
            project.getOfficers().stream()
                .map(Officer::getName)
                .collect(Collectors.joining(", "))
        );
        System.out.println("Manager: " + project.getManager().getName());
        
        System.out.println("---------------------------");
    }
}