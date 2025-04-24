package com.project;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * The {@code LoginService} class handles the login functionality for users in the system.
 * <p>
 * It verifies user credentials based on NRIC format and password, and returns the authenticated {@link User} if successful.
 */
public class LoginService {

    /** The list of registered users. */
    private final List<User> users;

    /** The pattern used to validate NRIC format (S or T followed by 7 digits and a capital letter). */
    private static final Pattern NRIC_PATTERN = Pattern.compile("^[ST]\\d{7}[A-Z]$");

    /**
     * Constructs a new {@code LoginService} with a list of existing users.
     *
     * @param users the list of users eligible for login
     */
    public LoginService(List<User> users) {
        this.users = users;
    }

    /**
     * Authenticates a user based on NRIC and password input.
     * <p>
     * The NRIC must match the format: starts with 'S' or 'T', followed by 7 digits, and ends with a capital letter.
     * If the credentials match a registered user, the user is logged in and returned.
     *
     * @param scanner the {@code Scanner} object for reading user input
     * @return the authenticated {@code User} if login is successful; {@code null} otherwise
     */
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
