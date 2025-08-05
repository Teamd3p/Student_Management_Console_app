package com.tss.app;

import java.util.Scanner;

import com.tss.controller.SubjectController;

public class SubjectManagement implements MenuHandler {
	
	private SubjectController subjectController = new SubjectController();
	private Scanner scanner = new Scanner(System.in);

	@Override
	public void showMenu() {
		System.out.println("\n+------------------------------+");
        System.out.println("|      SUBJECT MANAGEMENT      |");
        System.out.println("+------------------------------+");
        System.out.println("| 1. View All Subjects         |");
        System.out.println("| 2. Add New Subject           |");
        System.out.println("| 3. Update A Subject          |");
        System.out.println("| 4. Delete A Subject          |");
        System.out.println("| 5. Go Back                   |");
        System.out.println("+------------------------------+");
        System.out.print("Enter your choice: ");
	}

	@Override
	public void chooseMenu() {
		
		  int choice;

	        while (true) {
	            showMenu();

	            try {
	                choice = Integer.parseInt(scanner.nextLine());
	            } catch (NumberFormatException e) {
	                System.out.println("❌ Invalid input. Please enter a number.");
	                continue;
	            }
	            
	            switch (choice) {
                case 1:
                    subjectController.readAllSubjects();
                    break;
                case 2:
                    subjectController.addSubject();
                    break;
                case 3:
                    subjectController.updateSubject();
                    break;
                case 4:
                    System.out.println("🔙 Returning to main menu...");
                    return;
                default:
                    System.out.println("❌ Invalid choice. Please select from the menu.");
            }
        }
    }
}

