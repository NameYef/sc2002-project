package com.project;
import java.util.Scanner;
import java.util.List;

// Interface for project viewing operations
interface ProjectViewOperations {
    void viewProjects();
    void setProjectFilters(Scanner scanner);
    void fillEligibleProjects(List<Project> projectList);
}