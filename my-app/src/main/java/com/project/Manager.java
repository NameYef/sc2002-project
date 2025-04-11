package com.project;

import java.util.Scanner;
import java.util.List;
public class Manager extends User{

    public Manager(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
    }
    @Override
    public String getRole() {
        return "Manager";
    }

    @Override
    public String showInterface(Scanner scanner, List<Project> projectList) {
        while (true) {
            System.out.println(" Manager Menu:");
            System.out.println("1. Reset Password");
            System.out.println("2. Logout");
            System.out.println("3. Quit");
            System.out.print("Choice: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    return resetPassword(scanner);
                case "2":
                    return "logout";
                case "3":
                    return "quit";
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
