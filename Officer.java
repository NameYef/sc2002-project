package com.project;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.List;
public class Officer extends Applicant{

    public Officer(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
    }
    @Override
    public String getRole() {
        return "Officer";
    }

    
    public void viewundertakenProjects() {
    	
        System.out.println("Viewing projects undertaken by officer (" + name   + " " + maritalStatus  +" "+ age + " years old):");
    }
    
    public String showProjects(int input, List<Project> projectList) {
    	String showResults = "";
    	if(input == 4) {
    		viewundertakenProjects();
    		List<Project> undertakenProjects = projectList.stream().filter(obj->obj.getOfficers().contains(name)).collect(Collectors.toList());
    	   // undertakenProjects.forEach(obj-> System.out.printf("\n"+"Name:" + obj.getName() + ", "+ "Neighbourhood:" + obj.getNeighborhood() + ", "+ "Number of type 1:" + obj.getNoType1() + ", "+ "Price:" + obj.getPriceType1()+ " "+ "Number of type 2:" +obj.getNoType2()+ ", "+ "Price:" + obj.getNoType2()+ ", " + "Open date:" + obj.getOpenDate() + ", "+ "Close date:" + obj.getCloseDate()+ ", " + "Manager:" + obj.getManager()+ "\n"));
    	    showResults = undertakenProjects.stream().map(obj->String.format("\n Name: %s, Neighbourhood: %s, Type 1: %s,  Number of type 1:%d, Price: %d, Type 2:%s, Number of type 2:%s, Price: %s, Open date:%s, Close date:%s, Manager: %s \n ", obj.getName(), obj.getNeighborhood(),obj.getType1(),obj.getNoType1(),obj.getPriceType1(),obj.getType2(),obj.getNoType2(),obj.getPriceType2(),obj.getOpenDate(), obj.getCloseDate(),obj.getManager())).collect(Collectors.joining());
    	}
    	
    	//if checking for oneself
    	
    	else if(input == 5){
    		viewProjects();
    		if (this.maritalStatus == "Single") {
        		//stream api to filter
        		if (this.age >= 35) {
        			viewProjects();
        			
        			showResults = projectList.stream().map(obj->String.format("\n Name: %s, Neighbourhood: %s, Type 1: %s,  Number of type 1:%d, Price: %d, Type 2:%s, Number of type 2:%s, Price: %s, Open date:%s, Close date:%s, Officers: %s, Manager: %s \n ", obj.getName(), obj.getNeighborhood(),obj.getType1(),obj.getNoType1(),obj.getPriceType1(),obj.getType2(),obj.getNoType2(),obj.getPriceType2(),obj.getOpenDate(), obj.getCloseDate(),obj.getOfficers(), obj.getManager())).collect(Collectors.joining());
            	}
        			//elligibleProjects.forEach(obj-> System.out.printf("Name:" + obj.getName() + "Neighbourhood:" + obj.getNeighborhood() + "Number of type 1:" + obj.getNoType1() + "Price:" + obj.getPriceType1()+ "Number of type 2:" +obj.getNoType2()+ "Price:" + obj.getNoType2() + "Open date:" + obj.getOpenDate() + "Close date:" + obj.getCloseDate() + "Manager:" + obj.getManager()+ "\n"));
        		}
        		else {
        			showResults = "Apologies, you are not elligible for any of the flat types.";
        		}
        	}
        	
        	//if married, check age
        	if (this.maritalStatus == "Married") {
        		if(this.age>=21) {
        			List<Project> elligibleProjects = projectList.stream().filter(obj->obj.getType1().equals("2-Room") || obj.getType2().equals("2-Room")).collect(Collectors.toList());
        			showResults = elligibleProjects.stream().map(obj->String.format("\n Name: %s, Neighbourhood: %s, Type 1: %s,  Number of type 1:%d, Price: %d, Type 2:%s, Number of type 2:%s, Price: %s, Open date:%s, Close date:%s, Officers: %s, Manager: %s \n ", obj.getName(), obj.getNeighborhood(),obj.getType1(),obj.getNoType1(),obj.getPriceType1(),obj.getType2(),obj.getNoType2(),obj.getPriceType2(),obj.getOpenDate(), obj.getCloseDate(),obj.getOfficers(), obj.getManager())).collect(Collectors.joining());
        		}
        		else {
        			showResults = "Apologies, you are not elligible for any of the flat types.";
        		}
        	}
        	
        return showResults;
    	}
      
   
    
    @Override
    public String showInterface(Scanner scanner, List<Project> projectList) {
        while (true) {
            System.out.println("Officer Menu:");
            System.out.println("1. Reset Password");
            System.out.println("2. Logout");
            System.out.println("3. Quit");
            System.out.println("4. Show the undertaken projects");
            System.out.println("5. Show projects that you are elligile for");
            System.out.print("Choice: ");
            String input = scanner.nextLine();
            String result;
            switch (input) {
                case "1":
                    return resetPassword(scanner);
                   
                case "2":
                    return "logout";
                    
                case "3":
                    return"quit";
                   
                case "4":
                	result = showProjects(4, projectList);
                	System.out.println(result);
                	break;
                	
                case "5":
                	result = showProjects(5,projectList);
                	System.out.println(result);
                	break;
                
                		
                default:
                    System.out.println("Invalid choice.");
            }
            
         
        }
        
        
    }
}

