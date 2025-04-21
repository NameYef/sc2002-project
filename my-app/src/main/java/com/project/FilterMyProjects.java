package com.project;

import java.util.List;

public class FilterMyProjects {
	
	    public List<Project> filterMyProject(List<Project> projectList, String managerNric) {
	        return projectList.stream()
	                .filter(p -> p.getManagerStr().equals(managerNric))
	                .toList();
	    
	}


}