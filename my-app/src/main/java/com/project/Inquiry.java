package com.project;

import java.time.LocalDate;

public class Inquiry {
    private Applicant applicant;
    private String projectName;
    private String message;
    private String reply;
    private LocalDate timestamp;

    public Inquiry(Applicant applicant, String message, String projectName) {
        this.projectName = projectName;
        this.applicant = applicant;
        this.message = message;
        this.reply = null;
        this.timestamp = LocalDate.now();

    }

    // for excel reading
    public Inquiry(Applicant applicant, String message, String projectName, String reply, LocalDate timestamp) {
        this.projectName = projectName;
        this.applicant = applicant;
        this.message = message;
        this.reply = reply;
        this.timestamp = timestamp;

    }

    public Applicant getApplicant() { return applicant; }
    public String getMessage() { return message; }
    public String getReply() { return reply; }
    public String getProjectName() { return projectName; }
    public boolean isReplied() { return reply != null; }
    public void setReply(String reply) { this.reply = reply; }
    public void setMessage(String message) { this.message = message;}
    public LocalDate getTimestamp() { return this.timestamp; }
    public String toString() {
        return "From: " + applicant.getName() +
               "\nMessage: " + message +
               "\nReply: " + (reply == null ? "No reply yet" : reply) +
               "\nSent on: " + timestamp;
    }
}
