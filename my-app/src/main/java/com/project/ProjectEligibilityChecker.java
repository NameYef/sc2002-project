package com.project;

/**
 * Helper class to check applicant eligibility for different flat types based on marital status and age.
 * This class applies the housing project eligibility rules to determine if an applicant qualifies.
 */
public class ProjectEligibilityChecker {
    /** The marital status of the applicant */
    private String maritalStatus;
    /** The age of the applicant */
    private int age;
    
    /**
     * Constructs a ProjectEligibilityChecker with the applicant's marital status and age
     * 
     * @param maritalStatus the marital status of the applicant
     * @param age the age of the applicant
     */
    public ProjectEligibilityChecker(String maritalStatus, int age) {
        this.maritalStatus = maritalStatus;
        this.age = age;
    }
    
    /**
     * Determines if the applicant is eligible for the specified flat type based on marital status and age
     * 
     * @param flatType the type of flat to check eligibility for
     * @return true if the applicant is eligible for the flat type, false otherwise
     */
    public boolean isEligibleForFlatType(String flatType) {
        if (maritalStatus.equals("Married") && age >= 21) {
            return true;
        }
        return maritalStatus.equals("Single") && age >= 35 && flatType.equals("2-Room");
    }
}