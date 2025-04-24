package com.project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for filtering projects based on various criteria.
 * Provides methods to filter projects by manager, date range, neighborhood, and price range.
 */
public class FilterMyProjects {

    /**
     * Filters projects to return only those managed by the specified manager.
     *
     * @param projectList The list of all projects to filter
     * @param managerNric The NRIC of the manager whose projects should be returned
     * @return A list containing only projects managed by the specified manager
     */
    public List<Project> filterMyProject(List<Project> projectList, String managerNric) {
        return projectList.stream()
                .filter(p -> p.getManagerStr().equals(managerNric))
                .toList();
    }

    /**
     * Filters projects by date range, returning projects whose application period
     * overlaps with the specified date range.
     *
     * @param projects  The list of projects to filter
     * @param startDate The start date of the range (inclusive), can be null for no lower bound
     * @param endDate   The end date of the range (inclusive), can be null for no upper bound
     * @return A list of visible projects that fall within the specified date range
     */
    public List<Project> filterByDateRange(List<Project> projects, LocalDate startDate, LocalDate endDate) {
        if (startDate == null && endDate == null) {
            return new ArrayList<>(projects);
        }

        return projects.stream()
                .filter(project -> {
                    boolean afterStartDate = startDate == null ||
                            (project.getCloseDate() != null && !project.getCloseDate().isBefore(startDate));
                    boolean beforeEndDate = endDate == null ||
                            (project.getOpenDate() != null && !project.getOpenDate().isAfter(endDate));

                    return project.isVisible() && afterStartDate && beforeEndDate;
                })
                .toList();
    }

    /**
     * Filters projects by neighborhood name, case-insensitive matching.
     *
     * @param projects     The list of projects to filter
     * @param neighborhood The neighborhood name to filter by
     * @return A list of visible projects in the specified neighborhood
     */
    public List<Project> filterByNeighborhood(List<Project> projects, String neighborhood) {
        if (neighborhood == null || neighborhood.isEmpty()) {
            return new ArrayList<>(projects);
        }

        return projects.stream()
                .filter(project -> project.isVisible() &&
                        (project.getNeighborhood() != null &&
                                project.getNeighborhood().equalsIgnoreCase(neighborhood)))
                .toList();
    }

    /**
     * Filters projects by price range, based on the flat types' prices.
     *
     * @param projects The list of projects to filter
     * @param minPrice The minimum price threshold (inclusive)
     * @param maxPrice The maximum price threshold (inclusive)
     * @param choice   "Y" to match projects where both flat types are in range,
     *                 "N" to match projects where either flat type is in range
     * @return A list of projects that match the price criteria, or null if an invalid choice is provided
     */
    public List<Project> filterByPriceRange(List<Project> projects, double minPrice, double maxPrice, String choice) {
        if (minPrice <= 0 && maxPrice <= 0) {
            return new ArrayList<>(projects);
        }

        if (choice.equals("Y")) {
            return projects.stream()
                    .filter(project ->
                            project.getPriceType1() >= minPrice && project.getPriceType1() <= maxPrice &&
                                    project.getPriceType2() >= minPrice && project.getPriceType2() <= maxPrice
                    )
                    .collect(Collectors.toList());
        } else if (choice.equals("N")) {
            return projects.stream()
                    .filter(project ->
                            project.getPriceType1() >= minPrice && project.getPriceType1() <= maxPrice ||
                                    project.getPriceType2() >= minPrice && project.getPriceType2() <= maxPrice
                    )
                    .collect(Collectors.toList());
        } else {
            System.out.println("No such Projects Available");
            return null;
        }
    }
}