package com.project;

import java.time.LocalDateTime;
public class Inquiry {
    private Applicant applicant;
    private String message;
    private String reply;
    private LocalDateTime timestamp;

    public Inquiry(Applicant applicant, String message) {
        this.applicant = applicant;
        this.message = message;
        this.reply = null;
        this.timestamp = LocalDateTime.now();
    }

    public Applicant getApplicant() { return applicant; }
    public String getMessage() { return message; }
    public String getReply() { return reply; }
    public void setReply(String reply) { this.reply = reply; }
    public void setMessage(String message) { this.message = message;}
    public String toString() {
        return "From: " + applicant.getName() +
               "\nMessage: " + message +
               "\nReply: " + (reply == null ? "No reply yet" : reply) +
               "\nSent on: " + timestamp;
    }
}
