package com.project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilterMyProjects {


	public List<Project> filterMyProject(List<Project> projectList, String managerNric) {


		return projectList.stream()
				.filter(p -> p.getManagerStr().equals(managerNric))
				.toList();

	}

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
