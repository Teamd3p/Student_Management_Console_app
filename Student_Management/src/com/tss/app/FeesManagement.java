package com.tss.app;

import java.util.Scanner;

import com.tss.controller.CourseController;
import com.tss.controller.FeeController;
import com.tss.controller.StudentController;

public class FeesManagement implements MenuHandler {

    private Scanner scanner = new Scanner(System.in);
    FeeController feeController = new FeeController();
    StudentController studentController = new StudentController();
    CourseController courseController = new CourseController();

    public void showMenu() {
        System.out.println("\n+-------------------------------+");
        System.out.println("|        FEES MANAGEMENT        |");
        System.out.println("+-------------------------------+");
        System.out.println("| 1. View Total Paid Fees       |");
        System.out.println("| 2. View Total Pending Fees    |");
        System.out.println("| 3. View Fees By Student       |");
        System.out.println("| 4. View Fees By Course        |");
        System.out.println("| 5. Update Fees Of A Course    |");
        System.out.println("| 6. Total Earning              |");
        System.out.println("| 7. Go Back                    |");
        System.out.println("+-------------------------------+");
        System.out.print("Enter your choice: ");
    }

    public void chooseMenu() {
        int choice;

        while (true) {
            showMenu();
            choice = scanner.nextInt();
            scanner.nextLine();
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
                    courseController.readAllCourseRecords();
//                    feeController.getCourseFees();
                    break;
                case 5:
                    System.out.println(">> Updating fees of a course...");
                    courseController.readAllCourseRecords();
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
