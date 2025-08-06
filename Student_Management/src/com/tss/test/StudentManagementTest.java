package com.tss.test;

import java.util.Scanner;

import com.tss.app.CourseManagement;
import com.tss.app.FeesManagement;
import com.tss.app.MenuHandler;
import com.tss.app.StudentManagement;
import com.tss.app.SubjectManagement;
import com.tss.app.TeacherManagement;
import com.tss.controller.DashboardController;


public class StudentManagementTest {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int choice;
		MenuHandler option;

		while (true) {
			printMenu();
			choice = scanner.nextInt();
			scanner.nextLine();
				switch (choice) {
				case 1:
					option = new StudentManagement();
					option.chooseMenu();
					break;
				case 2:
					option = new CourseManagement();
					option.chooseMenu();
					break;
				case 3:
					option = new TeacherManagement();
					option.chooseMenu();
					break;
				case 4:
					option = new FeesManagement();
					option.chooseMenu();
					break;
				case 5:
					option = new SubjectManagement();
					option.chooseMenu();
					break;
				case 6:
					DashboardController dashController = new DashboardController();
					dashController.showDashboard();
					break;
				case 7:
					System.out.println(">> Exiting... Goodbye!");
					scanner.close();
					System.exit(0);
					break;
				default:
					System.out.println(">> Invalid choice. Please select between 1 and 6.");
				}
			}
		}

	public static void printMenu() {
		System.out.println("╔═══════════════════════════════╗");
		System.out.println("║           MAIN MENU           ║");
		System.out.println("╠═══════════════════════════════╣");
		System.out.println("║ 1. Student Management         ║");
		System.out.println("║ 2. Course Management          ║");
		System.out.println("║ 3. Teacher Management         ║");
		System.out.println("║ 4. Fees Management            ║");
		System.out.println("║ 5. Subject Management         ║");
		System.out.println("║ 6. Dashboard                  ║");
		System.out.println("║ 7. Exit                       ║");
		System.out.println("╚═══════════════════════════════╝");
		System.out.print("Enter your choice: ");

	}
}
