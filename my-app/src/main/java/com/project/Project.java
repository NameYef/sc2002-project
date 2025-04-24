package com.project;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

/**
 * The Project class represents a housing project with details about units, pricing, dates, and personnel.
 * This class implements the IProject interface and manages project visibility, applications, and inquiries.
 */
public class Project implements IProject {
    /** The name of the project */
    private String name;
    /** The neighborhood where the project is located */
    private String neighborhood;
    /** Description of the first housing unit type */
    private String type1;
    /** Number of available units of the first type */
    private int noType1;
    /** Price of the first housing unit type */
    private double priceType1;
    /** Description of the second housing unit type */
    private String type2;
    /** Number of available units of the second type */
    private int noType2;
    /** Price of the second housing unit type */
    private double priceType2;
    /** Date when the project application opens */
    private LocalDate openDate;
    /** Date when the project application closes */
    private LocalDate closeDate;
    /** String identifier for the project manager */
    private String managerStr;
    /** Number of officer positions available for this project */
    private int officerSlot;
    /** List of officer identifiers in string format */
    private List<String> officersStr;
    /** List of applicant identifiers in string format */
    private List<String> applicantsStr;

    /** Manager associated with this project */
    private Manager manager;
    /** List of officers assigned to this project */
    private List<Officer> officers;
    /** Flag indicating whether the project is visible to applicants */
    private boolean visibility;
    /** List of applicants for this project */
    private List<Applicant> applicants;
    /** List of approved applicants for this project */
    private List<Applicant> approvedApplicants = new ArrayList<>();
    /** List of inquiries about this project */
    private List<Inquiry> inquiries = new ArrayList<>();

    /**
     * Gets the visibility status of the project
     * 
     * @return true if the project is visible, false otherwise
     */
    public boolean isVisibility() {
        return visibility;
    }

    /**
     * Constructs a Project with string representation of manager, officers, and applicants
     * 
     * @param name The name of the project
     * @param neighborhood The neighborhood where the project is located
     * @param type1 Description of the first housing unit type
     * @param noType1 Number of available units of the first type
     * @param priceType1 Price of the first housing unit type
     * @param type2 Description of the second housing unit type
     * @param noType2 Number of available units of the second type
     * @param priceType2 Price of the second housing unit type
     * @param openDate Date when the project application opens
     * @param closeDate Date when the project application closes
     * @param managerStr String identifier for the project manager
     * @param officerSlot Number of officer positions available for this project
     * @param officersStr List of officer identifiers in string format
     * @param visibility Flag indicating whether the project is visible to applicants
     * @param applicantsStr List of applicant identifiers in string format
     */
    public Project(String name, String neighborhood, String type1, int noType1, double priceType1, String type2, int noType2, double priceType2, LocalDate openDate, LocalDate closeDate, String managerStr, int officerSlot, List<String> officersStr, boolean visibility, List<String> applicantsStr) {
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
        this.applicantsStr = applicantsStr;
        this.officers = new ArrayList<>();
        this.visibility = visibility;  
        this.applicants = new ArrayList<>();
    }

    /**
     * Constructs a Project with a Manager object
     * 
     * @param name The name of the project
     * @param neighborhood The neighborhood where the project is located
     * @param type1 Description of the first housing unit type
     * @param noType1 Number of available units of the first type
     * @param priceType1 Price of the first housing unit type
     * @param type2 Description of the second housing unit type
     * @param noType2 Number of available units of the second type
     * @param priceType2 Price of the second housing unit type
     * @param openDate Date when the project application opens
     * @param closeDate Date when the project application closes
     * @param managerStr String identifier for the project manager
     * @param officerSlot Number of officer positions available for this project
     * @param visibility Flag indicating whether the project is visible to applicants
     * @param manager Manager object associated with this project
     */
    public Project(String name, String neighborhood, String type1, int noType1, double priceType1, String type2, int noType2, double priceType2, LocalDate openDate, LocalDate closeDate, String managerStr, int officerSlot, boolean visibility, Manager manager) {
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
        this.manager = manager;
        this.visibility = visibility;  
        this.applicants = new ArrayList<>();
        this.officersStr = new ArrayList<>();
        this.officers = new ArrayList<>();
        this.applicantsStr = new ArrayList<>();
        this.applicants = new ArrayList<>();
    }

    /**
     * Toggle the visibility of the project
     * 
     * @param isVisible boolean value to set the visibility
     */
    @Override
    public void toggleVisibility(boolean isVisible) {
        this.visibility = isVisible;
        System.out.println("Project " + name + " visibility set to: " + isVisible);
    }

    /**
     * Checks if the project is visible
     * 
     * @return true if the project is visible, false otherwise
     */
    @Override
    public boolean isVisible() {
        return visibility;
    }

    /**
     * Gets the name of the project
     * 
     * @return the project name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the neighborhood where the project is located
     * 
     * @return the neighborhood
     */
    public String getNeighborhood() {
        return neighborhood;
    }

    /**
     * Sets the neighborhood where the project is located
     * 
     * @param neighborhood the neighborhood to set
     */
    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    /**
     * Gets the description of the first housing unit type
     * 
     * @return the first unit type description
     */
    public String getType1() {
        return type1;
    }

    /**
     * Sets the description of the first housing unit type
     * 
     * @param type1 the first unit type to set
     */
    public void setType1(String type1) {
        this.type1 = type1;
    }

    /**
     * Gets the number of available units of the first type
     * 
     * @return the number of first type units
     */
    public int getNoType1() {
        return noType1;
    }

    /**
     * Sets the number of available units of the first type
     * 
     * @param noType1 the number of first type units to set
     */
    public void setNoType1(int noType1) {
        this.noType1 = noType1;
    }

    /**
     * Gets the price of the first housing unit type
     * 
     * @return the price of the first unit type
     */
    public double getPriceType1() {
        return priceType1;
    }

    /**
     * Sets the price of the first housing unit type
     * 
     * @param priceType1 the price of first unit type to set
     */
    public void setPriceType1(int priceType1) {
        this.priceType1 = priceType1;
    }

    /**
     * Gets the description of the second housing unit type
     * 
     * @return the second unit type description
     */
    public String getType2() {
        return type2;
    }

    /**
     * Sets the description of the second housing unit type
     * 
     * @param type2 the second unit type to set
     */
    public void setType2(String type2) {
        this.type2 = type2;
    }

    /**
     * Gets the number of available units of the second type
     * 
     * @return the number of second type units
     */
    public int getNoType2() {
        return noType2;
    }

    /**
     * Sets the number of available units of the second type
     * 
     * @param noType2 the number of second type units to set
     */
    public void setNoType2(int noType2) {
        this.noType2 = noType2;
    }

    /**
     * Gets the price of the second housing unit type
     * 
     * @return the price of the second unit type
     */
    public double getPriceType2() {
        return priceType2;
    }

    /**
     * Sets the price of the second housing unit type
     * 
     * @param priceType2 the price of second unit type to set
     */
    public void setPriceType2(int priceType2) {
        this.priceType2 = priceType2;
    }

    /**
     * Gets the date when the project application opens
     * 
     * @return the opening date
     */
    public LocalDate getOpenDate() {
        return openDate;
    }

    /**
     * Sets the date when the project application opens
     * 
     * @param openDate the opening date to set
     */
    public void setOpenDate(LocalDate openDate) {
        this.openDate = openDate;
    }

    /**
     * Gets the date when the project application closes
     * 
     * @return the closing date
     */
    public LocalDate getCloseDate() {
        return closeDate;
    }

    /**
     * Sets the date when the project application closes
     * 
     * @param closeDate the closing date to set
     */
    public void setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
    }

    /**
     * Gets the string identifier for the project manager
     * 
     * @return the manager identifier string
     */
    public String getManagerStr() {
        return managerStr;
    }

    /**
     * Sets the string identifier for the project manager
     * 
     * @param manager the manager identifier string to set
     */
    public void setManager(String manager) {
        this.managerStr = manager;
    }

    /**
     * Gets the number of officer positions available for this project
     * 
     * @return the number of officer slots
     */
    public int getOfficerSlot() {
        return officerSlot;
    }

    /**
     * Sets the number of officer positions available for this project
     * 
     * @param officerSlot the number of officer slots to set
     */
    public void setOfficerSlot(int officerSlot) {
        this.officerSlot = officerSlot;
    }

    /**
     * Gets the list of officer identifiers in string format
     * 
     * @return the list of officer identifiers
     */
    public List<String> getOfficersStr() {
        return officersStr;
    }

    /**
     * Sets the list of officer identifiers in string format
     * 
     * @param officers the list of officer identifiers to set
     */
    public void setOfficersStr(List<String> officers) {
        this.officersStr = officers;
    }

    /**
     * Adds an officer identifier to the list
     * 
     * @param officer the officer identifier to add
     */
    public void addOfficersStr(String officer) {
        this.officersStr.add(officer);
    }

    /**
     * Sets the name of the project
     * 
     * @param name the project name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the Manager object associated with this project
     * 
     * @return the manager object
     */
    public Manager getManager() {
        return manager;
    }

    /**
     * Sets the Manager object associated with this project
     * 
     * @param manager the manager object to set
     */
    public void setManager(Manager manager) {
        this.manager = manager;
    }

    /**
     * Gets the list of officers assigned to this project
     * 
     * @return the list of officer objects
     */
    public List<Officer> getOfficers() {
        return officers;
    }

    /**
     * Sets the list of officers assigned to this project
     * 
     * @param officers the list of officer objects to set
     */
    public void setOfficers(List<Officer> officers) {
        this.officers = officers;
    }

    /**
     * Adds an officer to the project
     * 
     * @param officer the officer object to add
     */
    public void addOfficers(Officer officer) {
        this.officers.add(officer);
    }

    /**
     * Gets the list of applicant identifiers in string format
     * 
     * @return the list of applicant identifiers
     */
    public List<String> getApplicantsStr() {
        return applicantsStr;
    }

    /**
     * Adds an applicant identifier to the list
     * 
     * @param applicantNric the applicant NRIC identifier to add
     */
    public void addApplicantStr(String applicantNric) {
        applicantsStr.add(applicantNric);
    }

    /**
     * Sets the list of applicants for this project
     * 
     * @param applicants the list of applicant objects to set
     */
    public void setApplicants(List<Applicant> applicants) {
        this.applicants = applicants;
    }

    /**
     * Adds an applicant to the project
     * 
     * @param applicant the applicant object to add
     */
    public void addApplicant(Applicant applicant) {
        applicants.add(applicant);
    }

    /**
     * Removes an applicant from the project
     * 
     * @param applicant the applicant object to remove
     */
    public void removeApplicant(Applicant applicant) {
        applicants.remove(applicant);
    }
    
    /**
     * Adds an inquiry about the project
     * 
     * @param inquiry the inquiry object to add
     */
    public void addInquiry(Inquiry inquiry) {
        inquiries.add(inquiry);
    }
    
    /**
     * Gets the list of inquiries about this project
     * 
     * @return the list of inquiry objects
     */
    public List<Inquiry> getInquiries() {
        return inquiries;
    }
    
    /**
     * Sets the list of inquiries about this project
     * 
     * @param inquiries the list of inquiry objects to set
     */
    public void setInquiries(List<Inquiry> inquiries) {
        this.inquiries = inquiries;
    }
    
    /**
     * Gets the list of applicants for this project
     * 
     * @return the list of applicant objects
     */
    public List<Applicant> getApplicants() {
        return applicants;
    }

    /**
     * Gets the list of approved applicants for this project
     * 
     * @return the list of approved applicant objects
     */
    public List<Applicant> getApprovedApplicants() {
        return approvedApplicants;
    }

    /**
     * Adds an approved applicant to the project
     * 
     * @param applicant the applicant object to add to approved list
     */
    public void addApprovedApplicant(Applicant applicant) {
        approvedApplicants.add(applicant);
    }
}