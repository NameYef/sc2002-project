package com.project;

import java.util.Scanner;

public class Officer extends Applicant{

    public Officer(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
    }
    @Override
    public String getRole() {
        return "Officer";
    }

    @Override
    public String showInterface(Scanner scanner) {
        while (true) {
            System.out.println(" Officer Menu:");
            System.out.println("1. Reset Password");
            System.out.println("2. Logout");
            System.out.println("3. Quit");
            System.out.print("Choice: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    resetPassword(scanner);
                    break;
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

