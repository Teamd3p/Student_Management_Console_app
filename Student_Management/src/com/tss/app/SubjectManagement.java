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
		System.out.println("╔══════════════════════════════╗");
		System.out.println("║      SUBJECT MANAGEMENT      ║");
		System.out.println("╠══════════════════════════════╣");
		System.out.println("║ 1. View All Subjects         ║");
		System.out.println("║ 2. Add New Subject           ║");
		System.out.println("║ 3. Update A Subject          ║");
		System.out.println("║ 4. Delete A Subject          ║");
		System.out.println("║ 5. Go Back                   ║");
		System.out.println("╚══════════════════════════════╝");

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

