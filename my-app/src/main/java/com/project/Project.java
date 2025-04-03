package com.project;
import java.util.ArrayList;
import java.util.List;

public class Project implements IProject {
    private String name;
    private boolean visibility;
    private List<Applicant> applicants;

    public Project(String name) {
        this.name = name;
        this.visibility = true;  // Default visibility is ON
        this.applicants = new ArrayList<>(); // Fixed: Properly initialized
    }

    @Override
    public void toggleVisibility(boolean isVisible) {
        this.visibility = isVisible;
        System.out.println("Project " + name + " visibility set to: " + isVisible);
    }

    @Override
    public boolean isVisible() {
        return visibility;
    }

    @Override
    public boolean isEligible(Applicant applicant) {
        if (applicant.getMaritalStatus().equals("Single") && applicant.getAge() >= 35) {
            return name.equals("2-Room");  // Single applicants 35+ can apply for 2-Room only
        }
        if (applicant.getMaritalStatus().equals("Married") && applicant.getAge() >= 21) {
            return true;  // Married applicants can apply for any flat type
        }
        return false;
    }

    @Override
    public void addApplication(Applicant applicant) {
        if (isEligible(applicant)) {
            applicants.add(applicant);
            System.out.println("Applicant " + applicant.getNric() + " applied for " + name);
        } else {
            System.out.println("Applicant " + applicant.getNric() + " is not eligible for " + name);
        }
    }

    public String getName() { return name; }
}