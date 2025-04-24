package com.project;

import java.util.Scanner;

/**
 * Utility class providing methods for standardized UI display throughout the application.
 * Contains static methods for formatting headers, messages, and other UI elements.
 */
public class UIHelper {

    /** The standard width for UI elements */
    private static final int WIDTH = 50;

    /**
     * Prints a main header with the given title
     * 
     * @param title the title to display in the header
     */
    public static void printHeader(String title) {
    	System.out.println();
    	String line = "=".repeat(WIDTH);
    	System.out.println(line);
    	System.out.println(centerText(title, WIDTH));
    	System.out.println(line);
    }

    /**
     * Prints a sub-header with the given subtitle
     * 
     * @param subtitle the subtitle to display
     */
    public static void printSubHeader(String subtitle) {
        System.out.println();
        System.out.println("------------ " + subtitle + " ------------");
    }

    /**
     * Prints a success message
     * 
     * @param message the success message to display
     */
    public static void printSuccess(String message) {
        System.out.println("[SUCCESS] " + message);
    }

    /**
     * Prints an error message
     * 
     * @param message the error message to display
     */
    public static void printError(String message) {
        System.out.println("[ERROR] " + message);
    }

    /**
     * Prints a prompt for user input
     * 
     * @param prompt the prompt message to display
     */
    public static void printPrompt(String prompt) {
        System.out.print(prompt + ": ");
    }

    /**
     * Prints a divider line
     */
    public static void printDivider() {
    	String line = "-".repeat(WIDTH);
    	System.out.println(line);
    }

    /**
     * Centers text within a specified width
     * 
     * @param text the text to center
     * @param width the width to center within
     * @return the centered text
     */
    private static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < padding; i++) {
            sb.append(' ');
        }
        sb.append(text);
        return sb.toString();
    }

    /**
     * Prints a header for project information
     * 
     * @param title the title to display in the header
     */
    public static void printProjectHeader(String title) {
        String line = "-".repeat(WIDTH);
        String centeredTitle = center(title, WIDTH);
        System.out.println(line);
        System.out.println(centeredTitle);
        System.out.println(line);
    }

    /**
     * Prints a footer for project information
     */
    public static void printProjectFooter() {
        System.out.println("=".repeat(WIDTH));
    }
    
    /**
     * Prints a message indicating the action chosen by the user
     * 
     * @param action the action chosen
     */
    public static void printAction(String action) {
        System.out.println("[ACTION CHOSEN] " + action + " selected.");
    }

    /**
     * Prints a field with its label and value
     * 
     * @param label the field label
     * @param value the field value
     */
    public static void printField(String label, String value) {
        System.out.printf("%-25s : %s%n", label, value);
    }

    /**
     * Centers text within a specified width
     * 
     * @param text the text to center
     * @param width the width to center within
     * @return the centered text string
     */
    private static String center(String text, int width) {
        int pad = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, pad)) + text;
   }
    
    /**
     * Reads and validates flat type input from the user
     * Ensures the input is either "2-Room" or "3-Room"
     * 
     * @param scanner the Scanner object to read user input
     * @return the validated flat type
     */
    public static String readFlatType(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("2-Room") || input.equalsIgnoreCase("3-Room")) {
                return input;
            }
            System.out.println("Invalid flat type. Only '2-Room' or '3-Room' allowed.");
            System.out.println("Enter flat type 1 (2-Room or 3-Room):");
        }
    }
}