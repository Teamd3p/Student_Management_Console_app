package com.tss.app;

import java.util.Scanner;

import com.tss.controller.CourseController;

public class CourseManagement implements MenuHandler {

    Scanner scanner = new Scanner(System.in);

    @Override
    public void showMenu() {
        System.out.println("\n+------------------------------+");
        System.out.println("|       COURSE MANAGEMENT      |");
        System.out.println("+------------------------------+");
        System.out.println("| 1. View All Courses          |");
        System.out.println("| 2. Add New Course            |");
        System.out.println("| 3. Add Subjects in a Course  |");
        System.out.println("| 4. View Subjects of a Course |");
        System.out.println("| 5. Search a Course           |");
        System.out.println("| 6. Delete a Course           |");
        System.out.println("| 7. Go Back                   |");
        System.out.println("+------------------------------+");
        System.out.print("Enter your choice: ");
    }

    @Override
    public void chooseMenu() {
        int choice;

        CourseController controller = new CourseController();
        while (true) {
            showMenu();
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                	controller.readAllCourseRecords();
                    break;
                case 2:
                    System.out.println(">> Adding a new course...");
                    // TODO: implement addNewCourse()
                    break;
                case 3:
                    System.out.println(">> Adding subjects to a course...");
                    // TODO: implement addSubjectsToCourse()
                    break;
                case 4:
                    System.out.println(">> Viewing subjects of a course...");
                    // TODO: implement viewSubjectsOfCourse()
                    break;
                case 5:
                    System.out.println(">> Searching for a course...");
                    // TODO: implement searchCourse()
                    break;
                case 6:
                    System.out.println(">> Soft deleting a course...");
                    // TODO: implement softDeleteCourse()
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
