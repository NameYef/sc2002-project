package com.project;

import java.util.Scanner;
import java.util.List;

/**
 * Abstract base class for all user types in the system.
 * Provides common user attributes and behaviors that are shared across different user roles.
 */
public abstract class User {
    
    /** The name of the user */
    protected String name;
    /** The national identification number of the user */
    protected String nric;
    /** The age of the user */
    protected int age;
    /** The marital status of the user */
    protected String maritalStatus;
    /** The login password for the user */
    protected String password;

    /**
     * Constructs a User with the specified details
     * 
     * @param name the name of the user
     * @param nric the national identification number of the user
     * @param age the age of the user
     * @param maritalStatus the marital status of the user
     * @param password the login password for the user
     */
    public User(String name, String nric, int age, String maritalStatus, String password) {
        this.name = name;
        this.nric = nric;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.password = password;
    }
    
    /**
     * Gets the name of the user
     * 
     * @return the user's name
     */
    public String getName() { return name; }
    
    /**
     * Gets the national identification number of the user
     * 
     * @return the user's NRIC
     */
    public String getNric() { return nric; }
    
    /**
     * Gets the age of the user
     * 
     * @return the user's age
     */
    public int getAge() { return age; }
    
    /**
     * Gets the marital status of the user
     * 
     * @return the user's marital status
     */
    public String getMaritalStatus() { return maritalStatus; }
    
    /**
     * Gets the password of the user
     * 
     * @return the user's password
     */
    public String getPassword() { return password; }
    
    /**
     * Gets the role of the user in the system
     * 
     * @return the user's role
     */
    public abstract String getRole();
    
    /**
     * Displays the user interface specific to the user's role
     * 
     * @param scanner the Scanner object to read user input
     * @param projectList the list of available projects
     * @return a string indicating the next action to take
     */
    public abstract String showInterface(Scanner scanner, List<Project> projectList);

    /**
     * Allows the user to reset their password
     * 
     * @param scanner the Scanner object to read user input
     * @return a string indicating to log out after password reset
     */
    public String resetPassword(Scanner scanner) {
        System.out.print("Enter new password: ");
        String newPass = scanner.nextLine();
        this.password = newPass;
        System.out.println("Password successfully updated.");
        return "logout";
    }
}