package com.project;

import java.util.Scanner;
import java.util.stream.Collectors;
// import java.util.stream.Stream;
import java.util.List;
public class Officer extends Applicant{

    List<Project> undertakenProjects;


    public Officer(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
    }


    @Override
    public String getRole() {
        return "Officer";
    }

    
    public String showProjects(int input, List<Project> projectList) {
    	String showResults = "";
    	if(input == 4) {
    		System.out.println("Viewing projects undertaken by officer (" + name   + " " + maritalStatus  +" "+ age + " years old):");
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
      
        
        public void replyToInquiry(Scanner scanner) {
            System.out.println("\n--- Reply to Inquiries ---");
        
            for (int i = 0 ; i < undertakenProjects.size(); i++) {
                System.out.println("[" + (i+1) + "] " + undertakenProjects.get(i).getName());
            }
        
            System.out.print("Select project index to reply to: ");
            int projectIndex = Integer.parseInt(scanner.nextLine());
            if (projectIndex < 1 || projectIndex > undertakenProjects.size()) {
                System.out.println("Invalid index.");
                return;
            }
        
            Project selectedProject = undertakenProjects.get(projectIndex-1);
            List<Inquiry> inquiries = selectedProject.getInquiries();
        
            if (inquiries.isEmpty()) {
                System.out.println("No inquiries in this project.");
                return;
            }
        
            for (int i = 0; i < inquiries.size(); i++) {
                Inquiry inquiry = inquiries.get(i);
                System.out.println("[" + (i+1) + "] " + inquiry.getMessage());
                System.out.println("     ↳ Reply: " + (inquiry.isReplied() ? inquiry.getReply() : "(No reply yet)"));
            }
        
            System.out.print("Select inquiry index to reply to: ");
            int inquiryIndex = Integer.parseInt(scanner.nextLine());
            if (inquiryIndex < 1 || inquiryIndex > inquiries.size()) {
                System.out.println("Invalid index.");
                return;
            }
        
            Inquiry selectedInquiry = inquiries.get(inquiryIndex-1);
            if (selectedInquiry.isReplied()) {
                System.out.println("This inquiry has already been replied to.");
                return;
            }
        
            System.out.print("Enter your reply: ");
            String reply = scanner.nextLine();
            selectedInquiry.setReply(reply);
            System.out.println("Reply recorded.");
        }
        
    
    @Override
    public String showInterface(Scanner scanner, List<Project> projectList) {
        undertakenProjects = projectList.stream().filter(obj->obj.getOfficers().contains(this.name)).collect(Collectors.toList());
        while (true) {
            System.out.println("Officer Menu:");
            System.out.println("1. Show the undertaken projects");
            System.out.println("2. Show projects that you are elligile for");
            System.out.println("3. View and Reply Inquiries");
            System.out.println("4. Reset Password");
            System.out.println("5. Logout");
            System.out.println("6. Quit");
            System.out.print("Choice: ");
            String input = scanner.nextLine();
            String result;
            switch (input) {
                case "1":
                result = showProjects(4, projectList);
                System.out.println(result);
                break;
                
                case "2":
                result = showProjects(5,projectList);
                System.out.println(result);
                break;
                
                case "3":
                replyToInquiry(scanner);
                break;

                case "4":
                    return resetPassword(scanner);
                   
                case "5":
                    return "logout";
                    
                case "6":
                    return"quit";
                default:
                    System.out.println("Invalid choice.");
            }
            
         
        }
        
        
    }
}


