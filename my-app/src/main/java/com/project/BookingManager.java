package com.project;

import java.util.Scanner;
import java.util.List;
/**
 * Helper class responsible for handling the booking process of flats
 * for applicants who have been approved.
 */
public class BookingManager {
    private Officer officer;

    /**
     * Constructs a BookingManager with a specific officer.
     *
     * @param officer The officer managing the bookings.
     */
    public BookingManager(Officer officer) {
        this.officer = officer;
    }

    /**
     * Books a flat for an applicant based on their NRIC if they are eligible.
     * Updates the application status and flat availability upon successful booking.
     *
     * @param scanner    Scanner for user input.
     * @param applicants List of applicants to search from.
     */
    public void bookFlat(Scanner scanner, List<Applicant> applicants) {
        UIHelper.printSubHeader("Flat Booking");

        System.out.print("Enter applicant NRIC: ");
        String nric = scanner.nextLine().trim();

        for (Applicant app : applicants) {
            if (app.getNric().equalsIgnoreCase(nric)) {
                if (!app.getApplicationStatus().equals("Successful")) {
                    System.out.println("This applicant is not approved for booking.");
                    return;
                }

                Project proj = app.getAppliedProject();
                System.out.println("Flat Type applied: " + app.getAppliedType());

                if (app.getAppliedType().equals("type1") && proj.getNoType1() > 0) {
                    proj.setNoType1(proj.getNoType1() - 1);
                    app.setApplicationStatus("Booked");
                    app.setFlatTypeBooked(proj.getType1());
                } else if (app.getAppliedType().equals("type2") && proj.getNoType2() > 0) {
                    proj.setNoType2(proj.getNoType2() - 1);
                    app.setApplicationStatus("Booked");
                    app.setFlatTypeBooked(proj.getType2());
                } else {
                    System.out.println("No more units left for the selected flat type.");
                    return;
                }

                System.out.println("Booking confirmed");
                generateReceipt(app);
                return;
            }
        }

        System.out.println("Applicant not found.");
    }

    /**
     * Generates and displays a booking receipt with the applicant's details
     * and the flat they have booked.
     *
     * @param app The applicant for whom the receipt is generated.
     */
    public void generateReceipt(Applicant app) {
        UIHelper.printSubHeader("Booking Receipt");

        System.out.println("Name: " + app.getName());
        System.out.println("NRIC: " + app.getNric());
        System.out.println("Age: " + app.getAge());
        System.out.println("Marital Status: " + app.getMaritalStatus());
        System.out.println("Flat Type Booked: " + app.getFlatTypeBooked());
        System.out.println("Project Name: " + app.getAppliedProject().getName());
        System.out.println("Neighborhood: " + app.getAppliedProject().getNeighborhood());
        UIHelper.printDivider();
    }
}
