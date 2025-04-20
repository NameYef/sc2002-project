package com.project;

import java.util.Scanner;
import java.util.List;

public abstract class User {
    
    protected String name;
    protected String nric;
    protected int age;
    protected String maritalStatus;
    protected String password;

    public User(String name, String nric, int age, String maritalStatus, String password) {
        this.name = name;
        this.nric = nric;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.password = password;
    }
    public String getName() { return name; }
    public String getNric() { return nric; }
    public int getAge() { return age; }
    public String getMaritalStatus() { return maritalStatus; }
    public String getPassword() { return password; }
    
    
    public abstract String getRole();
    public abstract String showInterface(Scanner scanner, List<Project> projectList);

    public String resetPassword(Scanner scanner) {
        System.out.print("Enter new password: ");
        String newPass = scanner.nextLine();
        this.password = newPass;
        System.out.println("Password successfully updated.");
        return "logout";
    }
}
