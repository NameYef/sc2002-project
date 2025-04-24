package com.project;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.time.LocalDate;

/**
 * Manages officer-related project functionalities including viewing 
 * undertaken projects, determining project eligibility, and retrieving applicants.
 */
public class OfficerProjectManager {
    private Officer officer;

    /**
     * Constructs an OfficerProjectManager for a specific officer.
     *
     * @param officer the officer associated with this manager
     */
    public OfficerProjectManager(Officer officer) {
        this.officer = officer;
    }

    /**
     * Displays detailed information about the officer's undertaken projects.
     *
     * @param undertakenProjects list of projects currently handled by the officer
     */
    public void displayUndertakenProjects(List<Project> undertakenProjects) {
        for (int i = 0; i < undertakenProjects.size(); i++) {
            Project project = undertakenProjects.get(i);
            System.out.println("[" + (i + 1) + "] " + project.getName());
            System.out.println("Neighborhood: " + project.getNeighborhood());
            System.out.println("Type1: " + project.getType1());
            System.out.println("Available Units: " + project.getNoType1());
            System.out.println("Selling Price: " + project.getPriceType1());
            System.out.println("Type2: " + project.getType2());
            System.out.println("Available Units: " + project.getNoType2());
            System.out.println("Selling Price: " + project.getPriceType2());
            System.out.println("Application Start Date: " + project.getOpenDate());
            System.out.println("Application Close Date: " + project.getCloseDate());
            System.out.println("Officers: " + 
                project.getOfficers().stream()
                    .map(Officer::getName)
                    .collect(Collectors.joining(", "))
            );
            System.out.println("Manager: " + project.getManager().getName());
            UIHelper.printProjectFooter();
        }
    }

    /**
     * Fills the officer's eligible project list with projects that:
     * - are currently open,
     * - are visible,
     * - the officer is not already assigned to,
     * - and the officer is eligible for at least one of the flat types.
     *
     * @param projectList list of all available projects
     * @param officer the officer to evaluate eligibility for
     */
    public void fillEligibleProjects(List<Project> projectList, Officer officer) {
        List<Project> eligibleProjects = officer.getEligibleProjects();
        eligibleProjects.clear();

        if (officer.getRegistrationStatus().equals("Pending") || 
            officer.getRegistrationStatus().equals("Approved")) {
            return;
        }

        LocalDate today = LocalDate.now();

        for (Project project : projectList) {
            if (!project.isVisible()) continue;

            if (project.getOfficersStr().contains(officer.getNric())) continue;

            if (today.isBefore(project.getOpenDate()) || today.isAfter(project.getCloseDate())) continue;

            ProjectEligibilityChecker checker = new ProjectEligibilityChecker(
                officer.getMaritalStatus(), officer.getAge());

            boolean type1Eligible = checker.isEligibleForFlatType(project.getType1());
            boolean type2Eligible = checker.isEligibleForFlatType(project.getType2());

            if (type1Eligible || type2Eligible) {
                eligibleProjects.add(project);
            }
        }
    }

    /**
     * Finds and returns all applicants from a list of projects.
     *
     * @param projects list of projects to collect applicants from
     * @return list of applicants across the given projects
     */
    public List<Applicant> findAllApplicants(List<Project> projects) {
        List<Applicant> result = new ArrayList<>();
        for (Project p : projects) {
            result.addAll(p.getApplicants());
        }
        return result;
    }
}
