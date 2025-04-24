package com.project;

import java.time.LocalDate;

/**
 * Represents an inquiry submitted by an applicant regarding a specific project.
 * An inquiry contains the applicant's message, an optional reply, and a timestamp.
 */
public class Inquiry {
    private Applicant applicant;
    private String projectName;
    private String message;
    private String reply;
    private LocalDate timestamp;

    /**
     * Constructs an Inquiry with the current date as the timestamp and no reply.
     *
     * @param applicant   the applicant who submitted the inquiry
     * @param message     the message of the inquiry
     * @param projectName the name of the project the inquiry is related to
     */
    public Inquiry(Applicant applicant, String message, String projectName) {
        this.projectName = projectName;
        this.applicant = applicant;
        this.message = message;
        this.reply = null;
        this.timestamp = LocalDate.now();
    }

    /**
     * Constructs an Inquiry with all fields explicitly specified.
     * This constructor is mainly used when reading inquiries from external sources like Excel files.
     *
     * @param applicant   the applicant who submitted the inquiry
     * @param message     the message of the inquiry
     * @param projectName the name of the project the inquiry is related to
     * @param reply       the reply to the inquiry (may be null)
     * @param timestamp   the date when the inquiry was submitted
     */
    public Inquiry(Applicant applicant, String message, String projectName, String reply, LocalDate timestamp) {
        this.projectName = projectName;
        this.applicant = applicant;
        this.message = message;
        this.reply = reply;
        this.timestamp = timestamp;
    }

    /**
     * Returns the applicant who submitted the inquiry.
     *
     * @return the applicant
     */
    public Applicant getApplicant() {
        return applicant;
    }

    /**
     * Returns the message submitted in the inquiry.
     *
     * @return the inquiry message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the reply to the inquiry.
     *
     * @return the reply, or null if not yet replied
     */
    public String getReply() {
        return reply;
    }

    /**
     * Returns the name of the project the inquiry is related to.
     *
     * @return the project name
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Returns whether the inquiry has been replied to.
     *
     * @return true if a reply exists, false otherwise
     */
    public boolean isReplied() {
        return reply != null;
    }

    /**
     * Sets the reply to the inquiry.
     *
     * @param reply the reply message
     */
    public void setReply(String reply) {
        this.reply = reply;
    }

    /**
     * Updates the inquiry message.
     *
     * @param message the new message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the timestamp of when the inquiry was submitted.
     *
     * @return the submission date
     */
    public LocalDate getTimestamp() {
        return this.timestamp;
    }

    /**
     * Returns a string representation of the inquiry, including applicant name, message, reply, and timestamp.
     *
     * @return a formatted string representing the inquiry
     */
    public String toString() {
        return "From: " + applicant.getName() +
               "\nMessage: " + message +
               "\nReply: " + (reply == null ? "No reply yet" : reply) +
               "\nSent on: " + timestamp;
    }
}
