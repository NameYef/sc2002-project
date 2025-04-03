package com.project;

public class Manager {
    private String name;
    private String nric;
    private int age;
    private String maritalStatus;
    private String password;

    public Manager(String name, String nric, int age, String maritalStatus, String password) {
        this.name = name;
        this.nric = nric;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.password = password;
    }

    public String getName() {
        return name;
    }
}
