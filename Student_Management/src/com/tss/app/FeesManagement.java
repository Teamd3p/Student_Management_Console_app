package com.tss.app;

import java.util.Scanner;

import com.tss.controller.CourseController;
import com.tss.controller.FeeController;
import com.tss.controller.StudentController;
import com.tss.exception.ValidationException;
import com.tss.util.InputValidator;

public class FeesManagement implements MenuHandler {

    private Scanner scanner = new Scanner(System.in);
    FeeController feeController = new FeeController();
    StudentController studentController = new StudentController();
    CourseController courseController = new CourseController();

    public void showMenu() {
    	 final String RESET = "\u001B[0m";
  	   final String PURPLE = "\u001B[35m";
  	    final String BOLD = "\u001B[1m";
  	
  	System.out.println(PURPLE + BOLD+"╔═══════════════════════════════╗"+RESET);
  	System.out.println(PURPLE +BOLD +"║        FEES MANAGEMENT        ║"+RESET);
  	System.out.println(PURPLE+BOLD+"╠═══════════════════════════════╣"+RESET);
  	System.out.println(PURPLE+"║ 1. View Total Paid Fees       ║"+RESET);
  	System.out.println(PURPLE+"║ 2. View Total Pending Fees    ║"+RESET);
  	System.out.println(PURPLE+"║ 3. View Fees By Student       ║"+RESET);
  	System.out.println(PURPLE+"║ 4. View Fees By Course        ║"+RESET);
  	System.out.println(PURPLE+"║ 5. Update Fees Of A Course    ║"+RESET);
  	System.out.println(PURPLE+"║ 6. Total Earning              ║"+RESET);
  	System.out.println(PURPLE+"║ 7. Go Back                    ║"+RESET);
  	System.out.println(PURPLE+"╚═══════════════════════════════╝"+RESET);
    }

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
                    System.out.println(">> Viewing total paid fees...");
                    feeController.getTotalPaidFees();
                    break;
                case 2:
                    System.out.println(">> Viewing total pending fees...");
                    feeController.getTotalPendingFees();
                    break;
                case 3:
                    System.out.println(">> Viewing fees by student...");
                    studentController.readAllRecords();
                    feeController.getStudentsFees();
                    break;
                case 4:
                    System.out.println(">> Viewing fees by course...");
                    courseController.printAllActiveCourse();
                    break;
                case 5:
                    System.out.println(">> Updating fees of a course...");
                    feeController.updateCourseFee();
                    break;
                case 6:
                    System.out.println(">> Calculating total earning...");
                    feeController.getTotalEarning();
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
