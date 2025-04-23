package com.project;

// Helper class to check project eligibility
public class ProjectEligibilityChecker {
    private String maritalStatus;
    private int age;
    
    public ProjectEligibilityChecker(String maritalStatus, int age) {
        this.maritalStatus = maritalStatus;
        this.age = age;
    }
    
    public boolean isEligibleForFlatType(String flatType) {
        if (maritalStatus.equals("Married") && age >= 21) {
            return true;
        }
        return maritalStatus.equals("Single") && age >= 35 && flatType.equals("2-Room");
    }
}
