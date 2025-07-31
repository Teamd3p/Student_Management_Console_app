package com.tss.app;

import java.util.Scanner;

import com.tss.controller.CourseController;
import com.tss.controller.StudentController;
import com.tss.controller.StudentCourseController;
import com.tss.exception.ValidationException;

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
		System.out.println("| 7. View All Course Of Student|");
		System.out.println("| 8. Go Back                   |");
		System.out.println("+------------------------------+");
		System.out.print("Enter your choice: ");
	}

	@Override
	public void chooseMenu() {
		int choice;
		StudentController controller = new StudentController();
		StudentCourseController SCController = new StudentCourseController();
		CourseController courseController = new CourseController();

		while (true) {
			showMenu();
			choice = scanner.nextInt();
			scanner.nextLine(); // consume newline

			switch (choice) {
			case 1:
				controller.readAllRecords();
				break;
			case 2:
				try {
					controller.insertStudent();
				} catch (ValidationException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 3:
				SCController.AssignCourseToStudent(controller,courseController);
				break;
			case 4:
				courseController.readAllCourseRecords();
				break;
			case 5:
				controller.searchStudentById();
				break;
			case 6:
				controller.deleteStudentById();
				break;
			case 7:
				try {
					controller.showAllCoursesById();
				} catch (ValidationException e) {
					System.out.println(e.getMessage());
				}
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
