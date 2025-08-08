package com.tss.app;

import java.util.Scanner;

import com.tss.controller.CourseController;
import com.tss.controller.SubjectCourseController;
import com.tss.exception.ValidationException;
import com.tss.util.InputValidator;

public class CourseManagement implements MenuHandler {

    Scanner scanner = new Scanner(System.in);

    @Override
    public void showMenu() {
    	final String PURPLE = "\u001B[35m";  // Purple ANSI
    	final String RESET = "\u001B[0m";    // Reset to default
    	final String BOLD = "\033[1m";

    	System.out.println(PURPLE + BOLD+ "╔══════════════════════════════╗" + RESET);
    	System.out.println(PURPLE + BOLD+ "║       COURSE MANAGEMENT      ║" + RESET);
    	System.out.println(PURPLE +  BOLD+"╠══════════════════════════════╣" + RESET);
    	System.out.println(PURPLE + "║ 1. View All Courses          ║" + RESET);
    	System.out.println(PURPLE + "║ 2. Add New Course            ║" + RESET);
    	System.out.println(PURPLE + "║ 3. Add Subjects in a Course  ║" + RESET);
    	System.out.println(PURPLE + "║ 4. View Subjects of a Course ║" + RESET);
    	System.out.println(PURPLE + "║ 5. Search a Course           ║" + RESET);
    	System.out.println(PURPLE + "║ 6. Delete a Course           ║" + RESET);
    	System.out.println(PURPLE + "║ 7. Restore Course            ║" + RESET);
    	System.out.println(PURPLE + "║ 8. Go Back                   ║" + RESET);
    	System.out.println(PURPLE + "╚══════════════════════════════╝" + RESET);

    }

    @Override
    public void chooseMenu() {
        int choice = -1;

        CourseController controller = new CourseController();
        SubjectCourseController subjectCourseController = new SubjectCourseController();
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
                	controller.readAllCourseRecords();
                    break;
                case 2:
                    controller.addNewCourse();
                    break;
                case 3:
                    subjectCourseController.addSubjectsToCourse();
                    break;
                case 4:
                	subjectCourseController.viewSubjectsOfCourse();
                    break;
                case 5:
                	controller.searchCourse();
                    break;
                case 6:
                    controller.softDeleteCourse();
                    break;
                case 7:
                	controller.restoreCourse();
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
