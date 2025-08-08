package com.tss.app;

import java.util.Scanner;

import com.tss.controller.TeacherController;
import com.tss.exception.ValidationException;
import com.tss.util.InputValidator;

public class TeacherManagement implements MenuHandler {
	TeacherController teacherController = new TeacherController();

	private Scanner scanner = new Scanner(System.in);

	@Override
	public void showMenu() {
		final String RESET = "\u001B[0m";
	    final String BOLD = "\u001B[1m";
	    final String PURPLE = "\u001B[35m";


	    System.out.println(PURPLE +BOLD+ "╔══════════════════════════════╗"+RESET);
	    System.out.println(PURPLE +BOLD+"║       TEACHER MANAGEMENT     ║"+RESET);
	    System.out.println(PURPLE +BOLD+"╠══════════════════════════════╣"+RESET);
	    System.out.println(PURPLE+"║ 1. View All Teachers         ║"+RESET);
	    System.out.println(PURPLE+"║ 2. Add New Teacher           ║"+RESET);
	    System.out.println(PURPLE+"║ 3. Assign Subjects           ║"+RESET);
	    System.out.println(PURPLE+"║ 4. Remove A Subject          ║"+RESET);
	    System.out.println(PURPLE+"║ 5. Search A Teacher          ║"+RESET);
	    System.out.println(PURPLE+"║ 6. Delete A Teacher          ║"+RESET);
	    System.out.println(PURPLE+"║ 7. Restore Teacher           ║"+RESET);
	    System.out.println(PURPLE+"║ 8. Go Back                   ║"+RESET);
	    System.out.println(PURPLE+"╚══════════════════════════════╝" + RESET);

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
				System.out.println(">> Viewing all teachers...");
				teacherController.displayAllTeachers();
				break;
			case 2:
				System.out.println(">> Adding a new teacher...");
				teacherController.addTeacher();
				teacherController.displayAllTeachers();
				break;
			case 3:
				System.out.println(">> Assigning subjects to teacher...");
				System.out.println("Teachers Tables");
				teacherController.assignSubject();
				
				break;
			case 4:
				teacherController.removeSubject();
				break;
			case 5:
				System.out.println(">> Searching for a teacher...");
				teacherController.getTeacherById();
				break;
			case 6:
				System.out.println(">> Soft deleting a teacher...");
				teacherController.deleteTeacher();
				break;
			case 7:
				System.out.println(">> Restoring a teacher...");
				teacherController.restoreTeacher();
				break;
			case 8:
				System.out.println(">> Returning to main menu...");
				return;
			default:
				System.out.println(">> Invalid choice. Please select from 1 to 7.");
			}
		}
	}
}
