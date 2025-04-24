/**
 * [Helper class responsible for managing the application process for housing projects.]
 */
package com.project;

import java.util.Scanner;
import java.util.List;

/**
 * [Handles the application logic for a given applicant.]
 */
class ApplicationManager {
    private Applicant applicant;

    /**
     * [Constructor that initializes the manager with the specified applicant.]
     *
     * @param applicant [The applicant who is applying for a project.]
     */
    public ApplicationManager(Applicant applicant) {
        this.applicant = applicant;
    }

    /**
     * [Allows the applicant to apply for one of the eligible housing projects.]
     *
     * @param scanner [Scanner used for user input.]
     * @param eligibleProjects [List of projects the applicant is eligible to apply for.]
     */
    public void applyForProject(Scanner scanner, List<Project> eligibleProjects) {
        if (!applicant.getApplicationStatus().equals("Unsuccessful")) {
            System.out.println("You have already applied for a project.");
            return;
        }

        UIHelper.printProjectHeader("Available Projects you can apply for:");
        int i = 1;
        for (Project projects : eligibleProjects) {
            System.out.println(i + ":" + projects.getName());
            i++;
        }

        UIHelper.printDivider();

        System.out.println("Enter the name of the project to apply (or type 'exit' to cancel):");
        String inputProject = scanner.nextLine().trim();

        if (inputProject.equalsIgnoreCase("exit")) return;

        Project selectedProject = null;
        for (Project project : eligibleProjects) {
            if (project.getName().equalsIgnoreCase(inputProject)) {
                selectedProject = project;
                break;
            }
        }

        if (selectedProject == null) {
            System.out.println("Invalid project name.");
            return;
        }

        System.out.println("Select flat type: 1 for Type1, 2 for Type2");
        String inputType = scanner.nextLine().trim();

        ProjectEligibilityChecker checker = new ProjectEligibilityChecker(
            applicant.getMaritalStatus(), applicant.getAge());

        switch (inputType) {
            case "1":
                if (!checker.isEligibleForFlatType(selectedProject.getType1()) || selectedProject.getNoType1() <= 0) {
                    System.out.println("Invalid choice.");
                } else {
                    completeApplication(selectedProject, "type1", selectedProject.getType1());
                }
                break;

            case "2":
                if (!checker.isEligibleForFlatType(selectedProject.getType2()) || selectedProject.getNoType2() <= 0) {
                    System.out.println("Invalid choice.");
                } else {
                    completeApplication(selectedProject, "type2", selectedProject.getType2());
                }
                break;

            default:
                System.out.println("Invalid input.");
                break;
        }
    }

    /**
     * [Finalizes the application for the selected project and updates applicant/project data.]
     *
     * @param project [The project the applicant is applying to.]
     * @param type [The flat type identifier, e.g., "type1" or "type2".]
     * @param flatType [The actual flat type name.]
     */
    private void completeApplication(Project project, String type, String flatType) {
        applicant.setAppliedProject(project);
        applicant.setAppliedType(type);
        applicant.setAppliedFlatType(flatType);
        applicant.setApplicationStatus("Pending");
        project.addApplicant(applicant);
        project.addApplicantStr(applicant.getNric());
        System.out.println("Successfully applied for " + project.getName() + " (" + flatType + ")");
    }
}
