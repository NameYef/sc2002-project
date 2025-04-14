package com.project;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class Manager extends User{

    public Manager(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
    }
    @Override
    public String getRole() {
        return "Manager";
    }
    
    //https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/format/DateTimeFormatter.html
    //for parsing as datetime
    
    //add entry
    public void addEntry(Scanner scanner, List<Project> projectList) {
    	//specifies the format of the date time formatting, in this case, day, month, year
    	DateTimeFormatter dateForm = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    	List <String> officersString = new ArrayList <>();
    	//to manually enter the project details to create a new entry
    	System.out.println("Enter name of project");
    	String name = scanner.nextLine();
    	System.out.println("Enter name of neighbourhood");
    	String neighbourhood = scanner.nextLine();
    	System.out.println("Enter the type of 1st set of flats (2-Room, 3-Room, etc)");
    	String type1 = scanner.nextLine().toString();
    	System.out.println("Enter the number of type 1 flats");
    	int notype1 = Integer.parseInt(scanner.nextLine());
    	System.out.println("Enter the price of type 1 flats");
    	int pricetype1 =  Integer.parseInt(scanner.nextLine());
    	System.out.println("Enter the type of 2nd set of flats (2-Room, 3-Room, etc)");
    	String type2 = scanner.nextLine();
    	System.out.println("Enter the number of type 2 flats");
    	int notype2 = Integer.parseInt(scanner.nextLine());
    	System.out.println("Enter the price of type 2 flats");
    	int pricetype2 =  Integer.parseInt(scanner.nextLine());
    	System.out.println("\nEnter application opening date (dd/mm/yy):");
    	String strOpening = scanner.nextLine();
    	//now, use public static DateTimeFormatter ofPattern(String pattern,
    	 //Locale locale). This adjusts things to formayting as specified at start of method;
    	LocalDate openDate = LocalDate.parse(strOpening, dateForm);
    	System.out.println("Enter application opening date (dd/mm/yy):");
    	String strClosing= scanner.nextLine();
    	//now, use public static DateTimeFormatter ofPattern(String pattern,
    	 //Locale locale). This adjusts things to formayting as specified at start of method;
    	LocalDate closeDate = LocalDate.parse(strClosing, dateForm);
    	System.out.println("Enter the name of the manager overseeing the project:");
    	String manager = scanner.nextLine();
    	System.out.println("Enter the number of officers to undertake the project:");
    	int officerSlot =  Integer.parseInt(scanner.nextLine());
    	//loop to accept input and add input to object list for officersstring
    	for(int i = 1; i<=officerSlot;i++) {
    	 System.out.printf("Enter the name of officer no. %d that will be undertaking this project:", i);
    	 String officeStr = scanner.nextLine();
    	 officersString.add(officeStr);
    	}
    	
    	
    	Project newProj = new Project(name,neighbourhood,type1,notype1,pricetype1,type2,notype2,pricetype2,
    			openDate,closeDate,manager,officerSlot,officersString);
    	
    	projectList.add(newProj);
    }
    
    //to delete entry
    public void deleteEntry(Scanner scanner, List<Project> projectList) {
    	
    	projectList.forEach(obj->System.out.printf("\n"+"Name:" + obj.getName() + ", "+ "Neighbourhood:" + obj.getNeighborhood() + ", "+ "Type 1: "+ obj.getType1() + ", "+ "Number of type 1:" + obj.getNoType1() + ", "+ "Price:" + obj.getPriceType1()+ ", "
    	+ obj.getType2()+ ", "+ "Number of type 2:" + +obj.getNoType2()+ ", "+ "Price:" + obj.getPriceType2()
    	+ ", " + "Open date:" + obj.getOpenDate() + ", "+ "Close date:" + obj.getCloseDate()+ ", " + "Manager:" + obj.getManager()
    	+ ", "+ "No of officers:" + ", "+ obj.getOfficerSlot()+ "Names of officers:" + ", " + obj.getOfficers() + "\n"));
    	System.out.println("Enter the number of the entry of the project to delete (1 onwards):");
    	int projDel = scanner.nextInt();
    	projDel-=1;
    	projectList.remove(projDel);
    	
    }
    
    //to see the project list and check for updates
    public void seeProjectList(List <Project> projectList) {
    	projectList.forEach(obj->System.out.printf("\n"+"Name:" + obj.getName() + ", "+ "Neighbourhood:" + obj.getNeighborhood() + ", "+ "Type 1: "+ obj.getType1() + ", "+ "Number of type 1:" + obj.getNoType1() + ", "+ "Price:" + obj.getPriceType1()+ ", "
    	    	+ obj.getType2()+ ", "+ "Number of type 2:" + +obj.getNoType2()+ ", "+ "Price:" + obj.getPriceType2()
    	    	+ ", " + "Open date:" + obj.getOpenDate() + ", "+ "Close date:" + obj.getCloseDate()+ ", " + "Manager:" + obj.getManager()
    	    	+ ", "+ "No of officers:" + ", "+ obj.getOfficerSlot()+ "Names of officers:" + ", " + obj.getOfficers() + "\n"));
    }
    @Override
    public String showInterface(Scanner scanner, List<Project> projectList) {
        while (true) {
            System.out.println(" Manager Menu:");
            System.out.println("1. Reset Password");
            System.out.println("2. Logout");
            System.out.println("3. Quit");
            System.out.println("4. Add project entry");
            System.out.println("5. Delete project entry");
            System.out.println("6. Show current projectList");
            System.out.println("7.Adjust visibility");
            System.out.print("Choice: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    return resetPassword(scanner);
                case "2":
                    return "logout";
                case "3":
                    return "quit";
                case "4": 
                	addEntry(scanner, projectList);
                	break;
                case "5":
                	deleteEntry(scanner, projectList);
           
                	break;
                case "6":
                	seeProjectList(projectList);
                	break;
                //7 isnt programmed yet
                case "7":
                	break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
