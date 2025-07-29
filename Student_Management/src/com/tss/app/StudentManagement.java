package com.tss.app;

import java.util.Scanner;

public class StudentManagement implements MenuHandler {

	private Scanner scanner = new Scanner(System.in);

	@Override
	public void showMenu() {
		System.out.println("\n+------------------------------+");
		System.out.println("|      STUDENT MANAGEMENT      |");
		System.out.println("+------------------------------+");
		System.out.println("| 1. View All Students         |");
		System.out.println("| 2. Add New Student           |");
		System.out.println("| 3. Assign A Course           |");
		System.out.println("| 4. View All Courses          |");
		System.out.println("| 5. Search A Student          |");
		System.out.println("| 6. Delete A Student          |");
		System.out.println("| 7. Go Back                   |");
		System.out.println("+------------------------------+");
		System.out.print("Enter your choice: ");
	}

	@Override
	public void chooseMenu() {
		int choice;

		while (true) {
			showMenu();
			choice = scanner.nextInt();
			scanner.nextLine(); // consume newline

			switch (choice) {
			case 1:
				System.out.println(">> Viewing all students...");
				// TODO: implement viewAllStudents()
				break;
			case 2:
				System.out.println(">> Adding a new student...");
				// TODO: implement addNewStudent()
				break;
			case 3:
				System.out.println(">> Assigning a course to a student...");
				// TODO: implement assignCourseToStudent()
				break;
			case 4:
				System.out.println(">> Viewing all courses...");
				// TODO: implement viewAllCourses()
				break;
			case 5:
				System.out.println(">> Searching for a student...");
				// TODO: implement searchStudent()
				break;
			case 6:
				System.out.println(">> Soft deleting a student...");
				// TODO: implement softDeleteStudent()
				break;
			case 7:
				System.out.println(">> Returning to main menu...");
				return; // Go back to main menu
			default:
				System.out.println(">> Invalid choice. Please select from 1 to 7.");
			}
		}
	}
}
