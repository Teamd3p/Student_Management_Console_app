package com.tss.app;

import java.util.Scanner;

import com.tss.controller.SubjectController;
import com.tss.exception.ValidationException;
import com.tss.util.InputValidator;

public class SubjectManagement implements MenuHandler {
	
	private SubjectController subjectController = new SubjectController();
	private Scanner scanner = new Scanner(System.in);

	@Override
	public void showMenu() {
		final String RESET = "\u001B[0m";
	    final String BOLD = "\u001B[1m";
	    final String PURPLE = "\u001B[35m";

		
		System.out.println(PURPLE+BOLD+"╔══════════════════════════════╗"+RESET);
		System.out.println(PURPLE+BOLD+"║      SUBJECT MANAGEMENT      ║"+RESET);
		System.out.println(PURPLE+BOLD+"╠══════════════════════════════╣"+RESET);
		System.out.println(PURPLE+"║ 1. View All Subjects         ║"+RESET);
		System.out.println(PURPLE+"║ 2. Add New Subject           ║"+RESET);
		System.out.println(PURPLE+"║ 3. Update A Subject          ║"+RESET);
		System.out.println(PURPLE+"║ 4. Go Back                   ║"+RESET);
		System.out.println(PURPLE+"╚══════════════════════════════╝"+RESET);
	

	}

	@Override
	public void chooseMenu() {
		
		  int choice = -1;

	        while (true) {
	            showMenu();

	            try {
					choice = InputValidator.readChoice("Enter your choice: ");
				} catch (ValidationException e) {
					System.out.println(e.getMessage());
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
                    System.out.println("Returning to main menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please select from the menu.");
            }
        }
    }
}

