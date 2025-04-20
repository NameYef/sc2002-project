package com.project;

import java.util.Scanner;

public class UIHelper {

    public static void printHeader(String title) {
    	System.out.println();
    	String line = "=".repeat(WIDTH);
    	System.out.println(line);
    	System.out.println(centerText(title, WIDTH));
    	System.out.println(line);
    	
    	
        
//        System.out.println("========================================");
//        System.out.println(centerText(title, 40));
//        System.out.println("========================================");
    }

    public static void printSubHeader(String subtitle) {
        System.out.println();
        System.out.println("------------ " + subtitle + " ------------");
    }

    public static void printSuccess(String message) {
        System.out.println("[SUCCESS] " + message);
    }

    public static void printError(String message) {
        System.out.println("[ERROR] " + message);
    }

    public static void printPrompt(String prompt) {
        System.out.print(prompt + ": ");
    }

    public static void printDivider() {
    	String line = "-".repeat(WIDTH);
    	System.out.println(line);
        
    }

    private static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < padding; i++) {
            sb.append(' ');
        }
        sb.append(text);
        return sb.toString();
    }


        private static final int WIDTH = 50;

        public static void printProjectHeader(String title) {
            String line = "-".repeat(WIDTH);
            String centeredTitle = center(title, WIDTH);
            System.out.println(line);
            System.out.println(centeredTitle);
            System.out.println(line);
        }

        public static void printProjectFooter() {
            System.out.println("=".repeat(WIDTH));
        }
        
        public static void printAction(String action) {
            System.out.println("[ACTION CHOSEN] " + action + " selected.");
        }


        public static void printField(String label, String value) {
            System.out.printf("%-25s : %s%n", label, value);
        }

        private static String center(String text, int width) {
            int pad = (width - text.length()) / 2;
            return " ".repeat(Math.max(0, pad)) + text;
       }
    
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

