package com.project;

import java.util.List;

/**
 * Utility class for filtering projects based on the manager's NRIC.
 */
public class FilterMyProjects {

    /**
     * Filters a list of projects and returns only those managed by a specific manager.
     *
     * @param projectList the list of all projects to filter from
     * @param managerNric the NRIC of the manager whose projects should be retained
     * @return a list of projects managed by the manager with the specified NRIC
     */
    public List<Project> filterMyProject(List<Project> projectList, String managerNric) {
        return projectList.stream()
                .filter(p -> p.getManagerStr().equals(managerNric))
                .toList();
    }
}
