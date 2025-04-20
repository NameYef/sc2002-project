package com.project;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class LoginService {

    private final List<User> users;
    private static final Pattern NRIC_PATTERN = Pattern.compile("^[ST]\\d{7}[A-Z]$");

    public LoginService(List<User> users) {
        this.users = users;
    }

    public User login(Scanner scanner) {
        UIHelper.printSubHeader("Login Page");
        System.out.print("Enter user ID: ");

        String inputNric = scanner.nextLine().trim().toUpperCase();

        if (!NRIC_PATTERN.matcher(inputNric).matches()) {
            System.out.println("Invalid NRIC format. NRIC starts with S or T, followed by 7 digit number and ends with another letter");
            return null;
        }
        System.out.print("Enter password: ");
        String inputPass = scanner.nextLine();

        for (User user : users) {
            if (user.getNric().equals(inputNric) && user.getPassword().equals(inputPass)) {
                System.out.println("Logged in as " + user.getRole() + " " + user.getName());
                return user;
            }
        }

        System.out.println("Invalid user ID or password. Try again.");
        return null;
    }
}
