package com.project;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


public class Project implements IProject {
    private String name;
    private String neighborhood;
    private String type1;
    private int noType1;
    private int priceType1;
    private String type2;
    private int noType2;
    private int priceType2;
    private LocalDate openDate;
    private LocalDate closeDate;
    private String managerStr;
    private int officerSlot;
    private List<String> officersStr;

    private Manager manager;
    private List<Officer> officers;

    private boolean visibility;
    private List<Applicant> applicants;
    private List<Inquiry> inquiries = new ArrayList<>();

    public Project(String name, String neighborhood, String type1, int noType1, int priceType1, String type2, int noType2, int priceType2, LocalDate openDate, LocalDate closeDate, String managerStr, int officerSlot, List<String> officersStr, boolean visibility) {
        this.name = name;
        this.neighborhood = neighborhood;
        this.type1 = type1;
        this.noType1 = noType1;
        this.priceType1 = priceType1;
        this.type2 = type2;
        this.noType2 = noType2;
        this.priceType2 = priceType2;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.managerStr = managerStr;
        this.officerSlot = officerSlot;
        this.officersStr = officersStr;


        this.visibility = visibility;  
        this.applicants = new ArrayList<>(); // Fixed: Properly initialized
    }

    @Override
    public void toggleVisibility(boolean isVisible) {
        this.visibility = isVisible;
        System.out.println("Project " + name + " visibility set to: " + isVisible);
    }

    @Override
    public boolean isVisible() {
        return visibility;
    }

    public String getName() {
        return name;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public int getNoType1() {
        return noType1;
    }

    public void setNoType1(int noType1) {
        this.noType1 = noType1;
    }

    public int getPriceType1() {
        return priceType1;
    }

    public void setPriceType1(int priceType1) {
        this.priceType1 = priceType1;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public int getNoType2() {
        return noType2;
    }

    public void setNoType2(int noType2) {
        this.noType2 = noType2;
    }

    public int getPriceType2() {
        return priceType2;
    }

    public void setPriceType2(int priceType2) {
        this.priceType2 = priceType2;
    }

    public LocalDate getOpenDate() {
        return openDate;
    }

    public void setOpenDate(LocalDate openDate) {
        this.openDate = openDate;
    }

    public LocalDate getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
    }

    public String getManagerStr() {
        return managerStr;
    }

    public void setManager(String manager) {
        this.managerStr = manager;
    }

    public int getOfficerSlot() {
        return officerSlot;
    }

    public void setOfficerSlot(int officerSlot) {
        this.officerSlot = officerSlot;
    }

    public List<String> getOfficersStr() {
        return officersStr;
    }

    public void setOfficersStr(List<String> officers) {
        this.officersStr = officers;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Manager getManager() {
        return manager;
    }
    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public List<Officer> getOfficers() {
        return officers;
    }
    public void setOfficers(List<Officer> officers) {
        this.officers = officers;
    }

    public void addApplicant(Applicant applicant) {
        applicants.add(applicant);
    }

    public void removeApplicant(Applicant applicant) {
        applicants.remove(applicant);
    }
    

    public void addInquiry(Inquiry inquiry) {
        inquiries.add(inquiry);
    }
    
    public List<Inquiry> getInquiries() {
        return inquiries;
    }
    
    public List<Applicant> getApplicants() {
        return applicants;
    }
    
}