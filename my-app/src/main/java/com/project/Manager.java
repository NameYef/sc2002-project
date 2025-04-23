package com.project;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Manager extends User {

	private List<Project> myProjects;
	Project selectedProject;
	Project activeProject;
	List<Project> filteredList;

	DateTimeFormatter dateForm = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	LocalDate openDate = null;
	LocalDate closeDate = null;
	String flat = null;

	public Manager(String name, String nric, int age, String maritalStatus, String password) {
		super(name, nric, age, maritalStatus, password);
	}

	@Override
	public String getRole() {
		return "Manager";
	}

	FilterMyProjects filter = new FilterMyProjects();

	// CREATE A PROJECT
	public void createProject(Scanner scanner, List<Project> projectList){
		if (getActiveProject() != null){
			UIHelper.printDivider();
			System.out.println("\nYou already have an Active Project.....");
			System.out.println("You are not allowed to create a New Project!");

		}
		else {
			UIHelper.printAction("Add new Project");
			UIHelper.printAction("Create a New Project");

			try {
			System.out.println("Enter name of new Project:");
			String name = scanner.nextLine();

			System.out.println("Enter name of neighbourhood:");
			String neighbourhood = scanner.nextLine();
			
			System.out.println("Flat type of type 1 flats (2-Room / 3-Room):");
			String type1 = scanner.nextLine();
			
			System.out.println("Enter the number of type 1 flats:");
			int notype1 = Integer.parseInt(scanner.nextLine());

			System.out.println("Enter the price of type 1 flats:");
			double pricetype1 = Double.parseDouble(scanner.nextLine());

			System.out.println("Flat type of type 2 flats (2-Room / 3-Room):");
			String type2 = scanner.nextLine();
			
			System.out.println("Enter the number of type 2 flats:");
			int notype2 = Integer.parseInt(scanner.nextLine());

			System.out.println("Enter the price of type 2 flats:");
			double pricetype2 = Double.parseDouble(scanner.nextLine());

			System.out.println("Enter the number of officers to undertake the project:");
			int officerSlot = Integer.parseInt(scanner.nextLine());
			
			while (true) {
		        System.out.println("Enter application opening date (dd/MM/yyyy):");
		        try {openDate = LocalDate.parse(scanner.nextLine(), dateForm);

		        if (openDate.isAfter(LocalDate.now())) {
		            break;
		        } else {
		            System.out.println("Opening date must be after today!\n");
		        }} catch (Exception e) {
					System.out.println("Invalid Input!");
				}
		    }

			while (true) {
		        System.out.println("Enter application closing date (dd/MM/yyyy):");
		        try {closeDate = LocalDate.parse(scanner.nextLine(), dateForm);

		        if (closeDate.isAfter(openDate)) {
					Project conflict = getDateConflictProject(openDate, closeDate, projectList);
					if (conflict != null) {
						System.out.println("Your project dates overlap with project: " + conflict.getName());
						UIHelper.printDivider();
						System.out.println(conflict +" Project Range: " + conflict.getOpenDate() + " to " + conflict.getCloseDate());
						UIHelper.printDivider();
						return;
					}
		            break;
		        } else {
		            System.out.println("Closing date must be after opening date!\n");
		        }} catch (Exception e) {
					System.out.println("Invalid Input!");
				}
		    }

			Project newProj = new Project(name, neighbourhood, type1, notype1, pricetype1, type2, notype2, pricetype2,openDate, closeDate, this.getNric(),officerSlot, false, this);

			projectList.add(newProj);
			System.out.println("Project " + newProj.getName() + " added successfully!");
			System.out.println("\nYou can review the New Project in - View My Project List");} catch (NumberFormatException e) {
				System.out.println("Invalid Input!");
			}
		}
	}

	// EDIT A PROJECT 
	public void editProject(Scanner scanner, List<Project> projectList) {
		UIHelper.printAction("Edit My Project");

		
		selectedProject = selectProjectFromMyProjects(projectList, scanner);
	    if (selectedProject == null) return;

		System.out.println();
		
		UIHelper.printProjectHeader("Current Project Details:");
		UIHelper.printField("1. Name", selectedProject.getName());
	    UIHelper.printField("2. Neighbourhood", selectedProject.getNeighborhood());
	    UIHelper.printField("3. Number of OfficerSlots", String.valueOf(selectedProject.getOfficerSlot()));
	    UIHelper.printField("4. Number of 2-room units", String.valueOf(selectedProject.getNoType1()));
	    UIHelper.printField("5. Number of 3-room units", String.valueOf(selectedProject.getNoType2()));
	    UIHelper.printField("6. Price of 2-room", String.valueOf(selectedProject.getPriceType1()));
	    UIHelper.printField("7. Price of 3-room", String.valueOf(selectedProject.getPriceType2()));
	    UIHelper.printField("8. Application open-date", selectedProject.getOpenDate().toString());
	    UIHelper.printField("9. Application close-date", selectedProject.getCloseDate().toString());
//	    UIHelper.printField("10.Visibility", selectedProject.isVisible() ? "ON" : "OFF");
		UIHelper.printDivider();
		System.out.println();
		
		System.out.println("Which Field do you want to edit?");

		String detail = scanner.nextLine().trim();

		switch (detail) {
		case "1" : {
			System.out.print("Enter the new name of the project: ");
			selectedProject.setName(scanner.nextLine());
			break;
		}
		case "2" : {
			System.out.print("Enter new neighbourhood: ");
			selectedProject.setNeighborhood(scanner.nextLine());
			break;
		}
		case "3" : {
			System.out.print("Enter new number of officer slots: ");
			selectedProject.setOfficerSlot(Integer.parseInt(scanner.nextLine()));
			break;
		}
		case "4" : {
			System.out.print("Enter new number of 2-room units: ");
			selectedProject.setNoType1(Integer.parseInt(scanner.nextLine()));
			break;
		}
		case "5" : {
			System.out.print("Enter new number of 3-room units: ");
			selectedProject.setNoType2(Integer.parseInt(scanner.nextLine()));
			break;
		}
		case "6" : {
			System.out.print("Enter new price of 2-room: ");
			selectedProject.setPriceType1(Integer.parseInt(scanner.nextLine()));
			break;
		}
		case "7" : {
			System.out.print("Enter new price of 3-room: ");
			selectedProject.setPriceType2(Integer.parseInt(scanner.nextLine()));
			break;
		}
		case "8" : {
			System.out.print("Enter new application open date (dd/MM/yyyy): ");
			DateTimeFormatter fdate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			selectedProject.setOpenDate(LocalDate.parse(scanner.nextLine(), fdate));
			break;
		}
		case "9" : {
			System.out.print("Enter new application close date (dd/MM/yyyy): ");
			DateTimeFormatter fdate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			selectedProject.setCloseDate(LocalDate.parse(scanner.nextLine(), fdate));
			break;
		}
//		case "10" : {
//			UIHelper.printDivider();
//			System.out.println("Toggling visibility status...");
//			UIHelper.printDivider();
//			selectedProject.toggleVisibility(!selectedProject.isVisible());
//			System.out.println("New Visibility Status: " + (selectedProject.isVisible() ? "ON" : "OFF"));
//			break;
//		}
		default : System.out.println("Invalid selection....");
	}
	System.out.println("Updated Successfully!");
	}

	//DELETE A PROJECT
	public void deleteEntry(Scanner scanner, List<Project> projectList) {
		UIHelper.printAction("Delete an Existing Project");

		myProjects = filter.filterMyProject(projectList, this.getNric());
			

		boolean exit = false;
		while (!exit) {
			if (myProjects.isEmpty()) {
				System.out.println("There are no Projects to Delete.....");
				UIHelper.printDivider();
				return;
			}

			printProjectSelectionMenu(myProjects);

			System.out.print("Which Project would you like to Delete? (type 'exit' to leave)\n");
			UIHelper.printDivider();
			String input = scanner.nextLine();

			if (input.equalsIgnoreCase("exit")) {
				exit = true;
				continue;
			}

			try {
				int index = Integer.parseInt(input) - 1;

				if (index < 0 || index >= projectList.size()) {
					System.out.println("The number is not in the list!");
				} else {
					Project removed = myProjects.get(index);
					if (activeProject == removed) {
						System.out.println("You cannot delete active projects");
						return;
					}
					projectList.remove(removed);
					System.out.println("\nThe Project Deleted was: " + removed.getName());
						
					setActiveProject(projectList);
						
					exit = true;
				}
				} catch (NumberFormatException e) {
					System.out.println("Please enter a valid number....");
				}
			}
			UIHelper.printDivider();
		}

		// SEE MY PROJECTS
	public void seeMyProjectList(Scanner scanner,List<Project> projectList) {
		UIHelper.printAction("View My Projects");
		myProjects = filter.filterMyProject(projectList, this.getNric());
		UIHelper.printDivider();
		System.out.println("Do you want to View with Filters? Y/N");
		UIHelper.printDivider();
		String choice = scanner.nextLine().trim();

		switch (choice.toUpperCase()) {
			case "Y":
				FilterMenu(scanner,myProjects);
				break;

			case "N":
				for(Project p : myProjects) {
					System.out.println();
					UIHelper.printProjectHeader("Project Details");
					printProjectDetails(p);
					UIHelper.printDivider();

				}
				break;

			default: System.out.println("Invalid choice!");

		}

	}
	// SEE ALL PROJECTS
	public void seeAllProjectList(Scanner scanner,List<Project> projectList) {
		UIHelper.printAction("View All Projects");
		UIHelper.printDivider();
		System.out.println("Do you want to View with Filters? Y/N");
		UIHelper.printDivider();
		String choice = scanner.nextLine().trim();

		switch (choice.toUpperCase()) {
			case "Y":
				FilterMenu(scanner,projectList);
				break;

			case "N":
				for(Project p : projectList) {
					System.out.println();
					UIHelper.printProjectHeader("Project Details - Manager (" + p.getManager().getName() + ")");
					printProjectDetails(p);
					UIHelper.printDivider();

				}
				break;

			default: System.out.println("Invalid choice!");

		}
	}
	
	// TOGGLE VISIBILITY
	public void toggleVisibility(Scanner scanner, List<Project> projectList) {
		UIHelper.printAction("Toggle Project Visibility Status");
		myProjects = filter.filterMyProject(projectList, this.getNric());
		UIHelper.printProjectHeader("Visibility Status of each Project");
		
		int i = 1;
		for (Project p : myProjects) {
			System.out.println(i + ". " + p.getName() + " status is " + (p.isVisible() ? "ON" : "OFF"));
			i++;
			
		}
		UIHelper.printDivider();
		System.out.println();
		System.out.println("Which Project do you want to Toggle Visibility?");
		String choice = scanner.nextLine().trim();
		try {
		int target = Integer.parseInt(choice) - 1;
		
		selectedProject = myProjects.get(target);
		
		selectedProject.toggleVisibility(!selectedProject.isVisible()); //eror
		
		System.out.println("Toggling Visibility....");
		
		System.out.println("Updated Visibility Status: " + (selectedProject.isVisible() ? "ON" : "OFF"));
		} catch (NumberFormatException e) {
			System.out.println("Invalid input. Please enter a number.");

		}
	}
	
	//   VIEW ALL INQUIRIES
	public void viewallInquiries(Scanner scanner, List<Project> projectList) {
		UIHelper.printAction("View Inquiries of All Projects");
		boolean exit = false;
		int index = 0;
	while (!exit) {
		UIHelper.printProjectHeader("All Project List");
		System.out.println("Choose the Project to view the Inquiries");
		int i = 1;

		for (Project p : projectList) {
			System.out.println(i + ": " + p.getName());
			i++;
		}
		
		UIHelper.printDivider();
		System.out.println("Enter your choice");
		try {
			index = Integer.parseInt(scanner.nextLine().trim()) - 1;
			if (index < 0 || index >= projectList.size()) {
				System.out.println("Invalid selection.");
			} else {
				exit = true;
			}
		} catch (NumberFormatException e) {
			System.out.println("Invalid input. Please enter a number.");
		}

	}
	selectedProject = projectList.get(index);
	System.out.println("You have selected Project " + selectedProject.getName());

	List<Inquiry> inquiryList = selectedProject.getInquiries();


	if (inquiryList.isEmpty()) {
		System.out.println("\nNo Inquiries!");

	}
	else {
		UIHelper.printSubHeader(selectedProject.getName() + " Inquiries");

		for (int i = 0; i < inquiryList.size(); i++) {
			System.out.println();
			Inquiry inquiry = inquiryList.get(i);
			System.out.println((i + 1) + ". " + inquiry.toString());
			UIHelper.printDivider();
		}
	}
}
	
	// APPROVE OFFICERS
	public void approverejectOfficers(Scanner scanner, List<Project> projectList) {
		UIHelper.printAction("Approve or Reject Officer Applications");

		selectedProject = selectProjectFromMyProjects(projectList, scanner);
		if (selectedProject == null) return;

		List<Officer> pendingOfficers = new ArrayList<>();


		for (Officer officer : Officer.getPendingOfficers()) {
			if (officer.getRegistrationStatus().equals("Pending") && selectedProject.getName().equals(officer.getRegisteredProject().getName())) {
				pendingOfficers.add(officer);
			}
		}
	    
	    UIHelper.printDivider();

		if (pendingOfficers.isEmpty()) {
	        System.out.println("\nNo Officers have registered for this project.");
	        return;
	    }

	    UIHelper.printSubHeader("Pending Officers for : " + selectedProject.getName());
	    for (Officer officer : pendingOfficers) {
	        UIHelper.printField("Name", officer.getName());
	        UIHelper.printField("NRIC", officer.getNric());
	        UIHelper.printField("Age", String.valueOf(officer.getAge()));
	        UIHelper.printDivider();
	    }
	    int availableSlots = selectedProject.getOfficerSlot() - selectedProject.getOfficers().size();
	    UIHelper.printField("\nAvailable slots: ",String.valueOf(availableSlots));

	    while (true) {
	        if (availableSlots <= 0) {
	            System.out.println("No more officer slots available.");
	            break;
	        }

	        System.out.print("Enter Officer NRIC to approve/reject (0 to exit): ");
	        String nric = scanner.nextLine().trim().toUpperCase();

	        if (nric.equalsIgnoreCase("0")) break;

	        Officer targetOfficer = null;
	        for (Officer officer : pendingOfficers) {
	            if (officer.getNric().equalsIgnoreCase(nric)) {
	                targetOfficer = officer;
	                break;
	            }
	        }

	        if (targetOfficer == null) {
	            System.out.println("No pending officer found with that NRIC.");
	            continue;
	        }

	        System.out.print("Approve (A) / Reject (R)? ");
	        String reply = scanner.nextLine().trim();

	        switch (reply.toUpperCase()) {
	            case "A":
	            	selectedProject.addOfficersStr(targetOfficer.getNric());
					selectedProject.addOfficers(targetOfficer);
	                Officer.getPendingOfficers().remove(targetOfficer);
	                availableSlots -= 1;
					targetOfficer.setRegistrationStatus("Approved");
					
					System.out.println("Officer " + targetOfficer.getName() + " approved and added to project.");
	                System.out.println("Remaining slots: " + availableSlots);
	                break;

	            case "R":
	                Officer.getPendingOfficers().remove(targetOfficer);
	                System.out.println("Officer " + targetOfficer.getName() + " rejected.");
					targetOfficer.setRegistrationStatus("Rejected");
	                break;

	            default:
	                System.out.println("Invalid input.");
	                break;
	        }
	        pendingOfficers.remove(targetOfficer);
	    }
	}
	// APPROVE APPLICANTS
	public void approveRejectApplicants(Scanner scanner, List<Project> projectList) {
		UIHelper.printAction("Approve or Reject Project Applications");

		selectedProject = selectProjectFromMyProjects(projectList, scanner);
		if (selectedProject == null) return;
		
		List<Applicant> pendingApp = selectedProject.getApplicants();


	    UIHelper.printDivider();

	    if (pendingApp.isEmpty()) {
	        System.out.println("\nNo Applicants have applied for this project.");
	        return;
	    }


	    UIHelper.printProjectHeader("Pending Applications for : " + selectedProject.getName());
	    for (Applicant applicant : pendingApp) {
	        UIHelper.printField("Name", applicant.getName());
	        UIHelper.printField("NRIC", applicant.getNric());
	        UIHelper.printField("Age", String.valueOf(applicant.getAge()));

			if (applicant.getAppliedType().equalsIgnoreCase("type1")){
				flat = "2-Room";
			}
			else if (applicant.getAppliedType().equalsIgnoreCase("type2")){
				flat = "3-Room";
			}
			UIHelper.printField("Flat Type", flat);
	        UIHelper.printDivider();
	    }
	    
	    System.out.println("\n" + selectedProject.getType1() +  " units\t:" + selectedProject.getNoType1());
	    System.out.println(selectedProject.getType2() +  " units	:" + selectedProject.getNoType2());
		System.out.println();
	    while (!pendingApp.isEmpty()) {
	        System.out.print("Enter Applicant NRIC to approve/reject (or type '0' to exit): ");
	        String nric = scanner.nextLine().trim().toUpperCase();

	        if (nric.equals("0")) {
	        	break;
	        }

	        Applicant targetApp = null;
	        for (Applicant applicant : pendingApp) {
	            if (applicant.getNric().equalsIgnoreCase(nric)) {
	                targetApp = applicant;
	                break;
	            }
	        }

	        if (targetApp == null) {
	            System.out.println("No pending Application found with that NRIC.");
	            continue;
	        }

	        System.out.print("Approve (A) / Reject (R)? ");
	        String reply = scanner.nextLine().trim();
			int noflats2 = selectedProject.getNoType1();
			int noflats3 = selectedProject.getNoType2();

	        switch (reply.toUpperCase()) {
	            case "A":
	                String type = targetApp.getAppliedFlatType();
	                if (type.equalsIgnoreCase("2-Room")) {
	                    if (noflats2 <= 0) {
	                        System.out.println("No 2-Room units left. Cannot approve any more Applicants.");
	                        break;
			    }
				noflats2 = noflats2 - 1;
//	                     selectedProject.setNoType1(selectedProject.getNoType1() - 1);
	                } else if (type.equalsIgnoreCase("3-Room")) {
	                    if (noflats3 <= 0) {
	                        System.out.println("No 3-Room units left. Cannot approve any more Applicants.");
	                        break;
	                    }
				noflats3 = noflats3 - 1 ;
//	                     selectedProject.setNoType2(selectedProject.getNoType2() - 1);
	                } else {
	                    System.out.println("Unknown flat type.");
	                    break;
	                }

	                targetApp.setApplicationStatus("Successful");
	                selectedProject.addApprovedApplicant(targetApp);  
	    
	                System.out.println("Applicant " + targetApp.getName() + " approved.");
	                break;

	            case "R":
	                targetApp.setApplicationStatus("Unsuccessful");
	                selectedProject.removeApplicant(targetApp);
	                
	                System.out.println("Applicant " + targetApp.getName() + " rejected.");
	                break;

	            default:
	                System.out.println("Invalid input. Skipping.");
	                break;
	        }

			UIHelper.printDivider();
	        UIHelper.printField("Remaining " + selectedProject.getType1() +  " units", String.valueOf(noflats2));
	        UIHelper.printField("Remaining " + selectedProject.getType2() +  " units", String.valueOf(noflats3));
			UIHelper.printDivider();
	    }

	}
		
	// REPLY TO MY INQUIRIES
	public void replytoInquiries(Scanner scanner, List<Project> projectList) {
		UIHelper.printAction("Reply to Your Project Inquiries");
		
		selectedProject = selectProjectFromMyProjects(projectList, scanner);
		if (selectedProject == null) return;
			
		System.out.println("You selected project: " + selectedProject.getName());
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
        try {
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
        System.out.println("Reply recorded.");} catch (NumberFormatException e) {
            System.out.println("Invalid input");
        }

	}
	// APPROVE APPLICANTS WITHDRAWAL
	public void approveRejectWithdrawals(Scanner scanner, List<Project> projectList) {
		UIHelper.printAction("Approve or Reject Applicant Withdrawal");
		
		selectedProject = selectProjectFromMyProjects(projectList, scanner);
		if (selectedProject == null) return;
	    
	    
	    List<Applicant> WithdrawApplicants = new ArrayList<>();

		for (Applicant applicant : selectedProject.getApplicants()) {
			if (applicant.getWithdrawStatus()) {WithdrawApplicants.add(applicant);}
		}

	    
	    UIHelper.printDivider();

	    if (WithdrawApplicants.isEmpty()) {
	        System.out.println("\nNo Withdrawal Application for " + selectedProject.getName());
	        return;
	    }
	    
	    UIHelper.printProjectHeader("Pending Withdrawal Applications for: " + selectedProject.getName());
	    
	    for(Applicant applicants : WithdrawApplicants) {
	    	UIHelper.printField("Name", applicants.getName());
	        UIHelper.printField("NRIC", applicants.getNric());
	        UIHelper.printDivider();
	    }
	    
	    System.out.print("Enter Applicant NRIC to approve/reject  ");
        String nric = scanner.nextLine().trim().toUpperCase();
        
        Applicant targetapp = null;
        for (Applicant app : WithdrawApplicants) {
            if (app.getNric().equalsIgnoreCase(nric)) {
                targetapp = app;
                break;
            }
        }
        
        if (targetapp == null) {
        	System.out.println("\nNo Applicant found with that NRIC.");
        	return;

        }
        
        System.out.print("Approve (A) / Reject (R)? ");
        String reply = scanner.nextLine().trim();
        
        switch (reply.toUpperCase()) {
        	case "A":
        		UIHelper.printDivider();
        		System.out.println("Withdrawal Application Approved");
        		UIHelper.printDivider();
        		
        		
        		String type = targetapp.getAppliedType();
        	    if (type != null && targetapp.getApplicationStatus().equals("Booked")) {
        	        if (type.equalsIgnoreCase("2-Room")) {
        	            targetapp.getAppliedProject().setNoType1(
        	                targetapp.getAppliedProject().getNoType1() + 1
        	            );
        	        } 
        	        else if (type.equalsIgnoreCase("3-Room")) {
        	            targetapp.getAppliedProject().setNoType2(
        	                targetapp.getAppliedProject().getNoType2() + 1
        	            );
        	        }
        	    }
        		targetapp.getAppliedProject().getApprovedApplicants().remove(targetapp);
				targetapp.getAppliedProject().getApplicantsStr().remove(targetapp.getNric());
        		targetapp.getAppliedProject().removeApplicant(targetapp);
        	    targetapp.setApplicationStatus("Unsuccessful");
        	    targetapp.setAppliedType("");
				targetapp.setAppliedFlatType("");
				targetapp.setWithdrawStatus(false);

        	    
        		break;
        	case "R":
        		System.out.println("Withdrawal Application Rejected");
				targetapp.setWithdrawStatus(false);
        		break;
        	default:
        		System.out.println("Invalid input");
        		break;
        		
        		
        }
        	
 }
	//GENERATE APPLICANT REPORT
	public void generateApplicantReport(Scanner scanner, List<Project> projectList) {
		UIHelper.printAction("Generate Project Report");

		selectedProject = selectProjectFromMyProjects(projectList, scanner);
		if (selectedProject == null) return;

		UIHelper.printDivider();
		System.out.println("By Which Field do you want to Filter?");
		UIHelper.printDivider();
		System.out.println("1. Flat Type| 2. Applicant's Marital Status| 3. Applicant's Age");
		UIHelper.printDivider();
		int field = Integer.parseInt(scanner.nextLine().trim());

		List<Applicant> applicants = selectedProject.getApprovedApplicants();
		List<Applicant> filteredApplicants = new ArrayList<>();

		switch (field) {
			case 1:
				System.out.println("By which Flat Type?  1. 2-Room | 2. 3-Room");
				int flatTypeChoice = Integer.parseInt(scanner.nextLine().trim());
				String typeFilter = (flatTypeChoice == 1) ? "2-Room" : "3-Room";
				for (Applicant app : applicants) {
					if (app.getAppliedFlatType().equalsIgnoreCase(typeFilter)) {
						filteredApplicants.add(app);
					}
				}
				break;

			case 2:
				System.out.print("Enter Marital Status to Filter (Single/Married): ");
				String statusFilter = scanner.nextLine().trim();
				for (Applicant app : applicants) {
					if (app.getMaritalStatus().equalsIgnoreCase(statusFilter)) {
						filteredApplicants.add(app);
					}
				}
				break;

			case 3:
				System.out.print("Enter Minimum Age: ");
				int minAge = Integer.parseInt(scanner.nextLine().trim());
				System.out.print("Enter Maximum Age: ");
				int maxAge = Integer.parseInt(scanner.nextLine().trim());
				for (Applicant app : applicants) {
					if (app.getAge() >= minAge && app.getAge() <= maxAge) {
						filteredApplicants.add(app);
					}
				}
				break;

			default:
				System.out.println("Invalid filter option.");
				return;
		}

		UIHelper.printDivider();
		System.out.println("Filtered Applicant Report for Project: " + selectedProject.getName());
		UIHelper.printDivider();

		if (filteredApplicants.isEmpty()) {
			System.out.println("No applicants found for the selected filter.");
		} else {
			for (Applicant app : filteredApplicants) {
				if (app.getAppliedType().equalsIgnoreCase("type1")){
					flat = "2-Room";
				}
				else if (app.getAppliedType().equalsIgnoreCase("type2")){
					flat = "3-Room";
				}
				UIHelper.printField("Name", app.getName());
				UIHelper.printField("NRIC", app.getNric());
				UIHelper.printField("Age", String.valueOf(app.getAge()));
				UIHelper.printField("Marital Status", app.getMaritalStatus());
				UIHelper.printField("Applied Flat Type",flat);
				UIHelper.printDivider();
			}
		}
	}

	// TO CHOOSE MANAGER SELECTIVE
	private void printProjectSelectionMenu(List<Project> projects) {
	    UIHelper.printProjectHeader("MY PROJECTS");
	    for (int i = 0; i < projects.size(); i++) {
	        System.out.println((i + 1) + ": " + projects.get(i).getName());
	    }
	    UIHelper.printDivider();
	}
	
	// TO SHOW PROJECT DETAILS 
	private void printProjectDetails(Project p) {
	    UIHelper.printField("1. Name", p.getName());
	    UIHelper.printField("2. Neighbourhood", p.getNeighborhood());
	    UIHelper.printField("3. Number of OfficerSlots", String.valueOf(p.getOfficerSlot()));
	    UIHelper.printField("4. Number of 2-room units", String.valueOf(p.getNoType1()));
	    UIHelper.printField("5. Number of 3-room units", String.valueOf(p.getNoType2()));
	    UIHelper.printField("6. Price of 2-room", String.valueOf(p.getPriceType1()));
	    UIHelper.printField("7. Price of 3-room", String.valueOf(p.getPriceType2()));
	    UIHelper.printField("8. Application open-date", p.getOpenDate().toString());
	    UIHelper.printField("9. Application close-date", p.getCloseDate().toString());

		UIHelper.printField("10. Officers:", 
            p.getOfficers().stream()
                .map(Officer::getName)
                .collect(Collectors.joining(", "))
        );
	    UIHelper.printField("11. Visibility", p.isVisible() ? "ON" : "OFF");
	}

	
	// TO SHOW MANAGER SELECTIVE PROJECTS
	private Project selectProjectFromMyProjects(List<Project> projectList, Scanner scanner) {
	    myProjects = filter.filterMyProject(projectList, this.getNric());

	    if (myProjects.isEmpty()) {
	        System.out.println("You currently have no projects to manage.");
	        return null;
	    }

	    printProjectSelectionMenu(myProjects);

	    System.out.print("Which Project do you want to handle? ");
	    int target = Integer.parseInt(scanner.nextLine().trim()) - 1;

	    if (target < 0 || target >= myProjects.size()) {
	        System.out.println("Invalid project number selected.");
	        return null;
	    }

	    return myProjects.get(target);
	}

	// FILTER MENU FOR MANAGER
	private void FilterMenu(Scanner scanner,List<Project> projectList) {
		System.out.println("------------------Filter Menu----------------------");
		System.out.println("1.Neighbourhood |2.Application Date |3.Price Range ");
		System.out.println("---------------------------------------------------");
		System.out.println("Enter your choice: ");

		int target = Integer.parseInt(scanner.nextLine().trim());
		UIHelper.printDivider();
		switch(target) {
			case 1:

				System.out.println("Enter the Neighbourhood you want to filter by:");
				String place = scanner.nextLine().trim();
				filteredList = filter.filterByNeighborhood(projectList,place);
				if (filteredList.isEmpty()) {
					UIHelper.printDivider();
					System.out.println("No Projects with that filter");
					UIHelper.printDivider();
					return;
				}

				for (Project p : filteredList) {
					System.out.println();
					UIHelper.printProjectHeader("Project List based on " + place);
					printProjectDetails(p);
					UIHelper.printDivider();

				}
				break;
			case 2:
				try {
					System.out.println("Enter the Opening Application Date (DD/MM/YYYY):");
					openDate = LocalDate.parse(scanner.nextLine().trim(), dateForm);

					System.out.println("Enter the Closing Application Date (DD/MM/YYYY):");
					closeDate = LocalDate.parse(scanner.nextLine().trim(), dateForm);

					if (closeDate.isBefore(openDate)) {
						System.out.println("Closing date cannot be before the opening date.");
						UIHelper.printDivider();
						return;
					}

					filteredList = filter.filterByDateRange(projectList, openDate, closeDate);
					if (filteredList.isEmpty()) {
						UIHelper.printDivider();
						System.out.println("No Projects with that filter.");
						UIHelper.printDivider();
						return;
					}

					for (Project p : filteredList) {
						System.out.println();
						UIHelper.printProjectHeader("Project List from " + openDate + " to " + closeDate);
						printProjectDetails(p);
						UIHelper.printDivider();
					}

				} catch (Exception e) {
					System.out.println("Invalid date format. Please enter in DD/MM/YYYY.");
					UIHelper.printDivider();
				}
				break;
			case 3:
				System.out.println("Enter the Minimum Price you want to filter by:");
				double minPrice = Double.parseDouble(scanner.nextLine());

				System.out.println("Enter the Maximum Price you want to filter by:");
				double maxPrice = Double.parseDouble(scanner.nextLine());
				UIHelper.printDivider();
				System.out.println("Apply filter to both flat types? Y/N");
				String choice = scanner.nextLine().trim();

				filteredList = filter.filterByPriceRange(projectList, minPrice, maxPrice, choice.toUpperCase());
				if (filteredList.isEmpty()) {
					UIHelper.printDivider();
					System.out.println("No Projects with that filter");
					UIHelper.printDivider();
					return;
				}
				for(Project p : filteredList) {
					System.out.println();
					UIHelper.printProjectHeader("Project List ranging from " + "$"+minPrice + " to " + "$"+ maxPrice);
					printProjectDetails(p);
					UIHelper.printDivider();

				}
		}

	}
	// CHECK IF GOT CONFLICT WITH OTHER PRJ WHEN CREATING A NEW PROJECT
	private Project getDateConflictProject(LocalDate newOpen, LocalDate newClose, List<Project> projectList) {
		return projectList.stream()
				.filter(p -> p.getManagerStr().equals(this.getNric()))
				.filter(p -> !(newClose.isBefore(p.getOpenDate()) || newOpen.isAfter(p.getCloseDate())))
				.findFirst()
				.orElse(null);
	}

	//RETURN ACTIVE PROJECT OF THE MANAGER
	public void setActiveProject(List<Project> projectList) {
		LocalDate today = LocalDate.now();

		this.activeProject = projectList.stream()
				.filter(project -> project.getManagerStr() != null && project.getManagerStr().equals(this.getNric()))
				.filter(Project::isVisible)
				.filter(project ->
						(today.isEqual(project.getOpenDate()) || today.isEqual(project.getCloseDate())) ||
								(project.getOpenDate().isBefore(today) && project.getCloseDate().isAfter(today))
				)
				.findFirst()
				.orElse(null);

		if (this.activeProject == null) {
			UIHelper.printProjectHeader("You have no Active Project");
		}
	}

	// GET THE ACTIVE PROJECT
	public Project getActiveProject(){
		return this.activeProject;
	}

	// MANAGER MENU 
	public String showInterface(Scanner scanner, List<Project> projectList) {
    	System.out.println();
    	System.out.println("[ LOGIN SUCCESSFUL ] Welcome, " + getName() + "!\n");
    	setActiveProject(projectList);
    	Project current = getActiveProject();
    	if (current != null) {
    	    UIHelper.printProjectHeader("Your Active Project is " + current.getName());
    	} else {
    	    UIHelper.printProjectHeader("You have no Active Project at the moment..");
    	}

    	while (true) {

    	    UIHelper.printHeader("MANAGER MENU");

    	    System.out.println("Choose an action:");
    	    System.out.println(" 1. Add New Project");
    	    System.out.println(" 2. Edit Project");
    	    System.out.println(" 3. Delete Project");
    	    System.out.println(" 4. View My Project List");
    	    System.out.println(" 5. View All Project List");
    	    System.out.println(" 6. Toggle Project Visibility");
    	    System.out.println(" 7. View All Project Inquiries");
    	    System.out.println(" 8. View Your Project Inquiries");
    	    System.out.println(" 9. Approve/Reject Officers");
    	    System.out.println("10. Approve/Reject Applicants");
    	    System.out.println("11. Approve/Reject Applicant Withdrawal");
    	    System.out.println("12. Generate a Report");
    	    System.out.println("13. Reset Password");
    	    System.out.println("14. Logout");
    	    System.out.println("15. Quit App");

    	    UIHelper.printProjectFooter();

    	    System.out.print("\nEnter your choice: ");
    	    String input = scanner.nextLine().trim();

    	    switch (input) {
    	        case "1":
    	            setActiveProject(projectList);
    	            createProject(scanner, projectList);
    	            break;
    	        case "2":
    	            editProject(scanner, projectList);
    	            break;
    	        case "3":
    	            deleteEntry(scanner, projectList);
    	            break;
    	        case "4":
    	            seeMyProjectList(scanner,projectList);
    	            break;
    	        case "5":
    	            seeAllProjectList(scanner,projectList);
    	            break;
    	        case "6":
    	            toggleVisibility(scanner, projectList);
    	            break;
    	        case "7":
    	            viewallInquiries(scanner, projectList);
    	            break;
    	        case "8":
    	            replytoInquiries(scanner, projectList);
    	            break;
    	        case "9":
    	            approverejectOfficers(scanner, projectList);
    	            break;
    	        case "10":
    	            approveRejectApplicants(scanner, projectList);
    	            break;
    	        case "11":
    	            approveRejectWithdrawals(scanner, projectList);
    	            break;
    	        case "12":
    	        	generateApplicantReport(scanner, projectList);
    	            break;
    	        case "13":
    	            return resetPassword(scanner);
    	        case "14":
    	            System.out.println("Logging out....");
    	            return "logout";
    	        case "15":
    	            return "quit";
    	        default:
    	            System.out.println("Invalid choice. Please try again.");
    	            break;
    	    }

    	    }
	}
}


