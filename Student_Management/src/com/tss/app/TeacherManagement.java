package com.tss.app;

import java.util.Scanner;

import com.tss.controller.TeacherController;
import com.tss.util.InputUtil;

public class TeacherManagement implements MenuHandler {
	TeacherController teacherController = new TeacherController();

	private Scanner scanner = new Scanner(System.in);

	@Override
	public void showMenu() {
		System.out.println("\n+------------------------------+");
		System.out.println("|       TEACHER MANAGEMENT     |");
		System.out.println("+------------------------------+");
		System.out.println("| 1. View All Teachers         |");
		System.out.println("| 2. Add New Teacher           |");
		System.out.println("| 3. Assign Subjects           |");
		System.out.println("| 4. Remove A Subject          |");
		System.out.println("| 5. Search A Teacher          |");
		System.out.println("| 6. Delete A Teacher          |");
		System.out.println("| 7. Go Back                   |");
		System.out.println("+------------------------------+");
		System.out.print("Enter your choice: ");
	}

	@Override
	public void chooseMenu() {
		int choice = -1;

		while (true) {
			showMenu();
			choice = InputUtil.readInt("Enter your choice: ");


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
				teacherController.displayAllActiveTeachers();
				teacherController.assignSubject();
				
				break;
			case 4:
				System.out.println(">> Removing a subject from teacher...");
				System.out.println("Teachers Tables");
				teacherController.displayAllTeachers();
				teacherController.removeSubject();
				break;
			case 5:
				System.out.println(">> Searching for a teacher...");
				teacherController.getTeacherById();
				break;
			case 6:
				System.out.println(">> Soft deleting a teacher...");
				teacherController.deleteTeacher();
				teacherController.displayAllTeachers();
				break;
			case 7:
				System.out.println(">> Returning to main menu...");
				return;
			default:
				System.out.println(">> Invalid choice. Please select from 1 to 7.");
			}
		}
	}
}
