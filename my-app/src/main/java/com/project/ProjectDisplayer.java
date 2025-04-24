package com.project;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Helper class to display projects to applicants with filtering capabilities.
 * This class handles the presentation of project information based on applicant eligibility and user-defined filters.
 */
class ProjectDisplayer {
    /** The applicant viewing the projects */
    private Applicant applicant;
    /** Filter for projects by neighborhood */
    protected String filterNeighborhood;
    /** Filter for projects by flat type */
    protected String filterFlatType;
    
    /**
     * Constructs a ProjectDisplayer for a specific applicant
     * 
     * @param applicant the applicant viewing the projects
     */
    public ProjectDisplayer(Applicant applicant) {
        this.applicant = applicant;
    }
    
    /**
     * Displays eligible projects to the applicant with applied filters
     * 
     * @param eligibleProjects the list of projects to display
     */
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
    
    /**
     * Sets filtering options based on user input
     * 
     * @param scanner the Scanner object to read user input
     */
    public void setFilters(Scanner scanner) {
        System.out.print("Enter neighborhood to filter by (or press Enter for all): ");
        String inputNeighborhood = scanner.nextLine().trim();
        this.filterNeighborhood = inputNeighborhood.isEmpty() ? null : inputNeighborhood;
    
        System.out.print("Enter flat type to filter by (2-Room / 3-Room or press Enter for all): ");
        String inputFlatType = scanner.nextLine().trim();
        this.filterFlatType = inputFlatType.isEmpty() ? null : inputFlatType;
    
        System.out.println("Filters updated.");
    }
    
    /**
     * Displays information about a specific flat type in a project
     * 
     * @param project the project containing the flat type
     * @param typeLabel the label for the flat type (Type1 or Type2)
     * @param flatType the description of the flat type
     * @param units the number of available units
     * @param price the price of the flat type
     */
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