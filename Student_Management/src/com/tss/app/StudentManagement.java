package com.tss.app;

import com.tss.controller.CourseController;
import com.tss.controller.StudentController;
import com.tss.controller.StudentCourseController;
import com.tss.exception.ValidationException;
import com.tss.util.InputValidator;

public class StudentManagement implements MenuHandler {

	@Override
	public void showMenu() {
		final String PURPLE = "\u001B[35m"; 
	    final String BOLD = "\033[1m";
	    final String RESET = "\033[0m";
	
	    System.out.println(PURPLE + BOLD + "╔══════════════════════════════╗" + RESET);
	    System.out.println(PURPLE + BOLD + "║      STUDENT MANAGEMENT      ║" + RESET);
	    System.out.println(PURPLE + BOLD + "╠══════════════════════════════╣" + RESET);
	    System.out.println(PURPLE + "║ 1. View All Students         ║" + RESET);
	    System.out.println(PURPLE + "║ 2. Add New Student           ║" + RESET);
	    System.out.println(PURPLE + "║ 3. Assign A Course           ║" + RESET);
	    System.out.println(PURPLE + "║ 4. View All Courses          ║" + RESET);
	    System.out.println(PURPLE + "║ 5. Search A Student          ║" + RESET);
	    System.out.println(PURPLE + "║ 6. Delete A Student          ║" + RESET);
	    System.out.println(PURPLE + "║ 7. Pay Student Fees          ║" + RESET);
	    System.out.println(PURPLE + "║ 8. View All Course Of Student║" + RESET);
	    System.out.println(PURPLE + "║ 9. Manage Notification       ║" + RESET);
	    System.out.println(PURPLE + "║ 10. Restore Student          ║" + RESET);
	    System.out.println(PURPLE + "║ 11. Go Back                  ║" + RESET);
	    System.out.println(PURPLE + "╚══════════════════════════════╝" + RESET);

	}

	@Override
	public void chooseMenu() {
		int choice = -1;
		StudentController controller = new StudentController();
		StudentCourseController SCController = new StudentCourseController();
		CourseController courseController = new CourseController();
		int studentId = 0;

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
				try {
					controller.displayAllActiveStudents();
					studentId = InputValidator.readId("Enter Student ID: ");
				} catch (ValidationException e) {
					System.out.println(e.getMessage());
				}
				SCController.AssignCourseToStudent(studentId);
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
				controller.displayAllActiveStudents();
				try {
					studentId = InputValidator.readId("Enter Student ID: ");
				} catch (ValidationException e) {
					System.out.println(e.getMessage());
				}
				controller.payStudentFees(studentId);
				break;
			case 8:
				controller.showAllCoursesById();
				break;
			case 9:
				controller.manageNotification();
				break;
			case 10:
				controller.restoreStudent();
				break;
			case 11:
				System.out.println(">> Returning to main menu...");
				return;
			default:
				System.out.println("enter choice from 1 to 10.");
			}
		}
	}
}
