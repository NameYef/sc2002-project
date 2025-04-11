package com.project;

import java.util.List;
import java.util.Scanner;

public class LoginService {

    private final List<User> users;

    public LoginService(List<User> users) {
        this.users = users;
    }

    public User login(Scanner scanner) {
        System.out.println("=== Login ===");
        System.out.print("Enter user ID: ");
        String inputId = scanner.nextLine();
        System.out.print("Enter password: ");
        String inputPass = scanner.nextLine();

        for (User user : users) {
            if (user.getNric().equals(inputId) && user.getPassword().equals(inputPass)) {
                System.out.println("Logged in as " + user.getRole() + " " + user.getName());
                return user;
            }
        }

        System.out.println("Invalid user ID or password. Try again.");
        return null;
    }
}
