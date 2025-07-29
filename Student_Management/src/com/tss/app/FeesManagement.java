package com.tss.app;

import java.util.Scanner;

public class FeesManagement implements MenuHandler {

    private Scanner scanner = new Scanner(System.in);

    @Override
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

    @Override
    public void chooseMenu() {
        int choice;

        while (true) {
            showMenu();
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.println(">> Viewing total paid fees...");
                    // TODO: implement viewTotalPaidFees()
                    break;
                case 2:
                    System.out.println(">> Viewing total pending fees...");
                    // TODO: implement viewTotalPendingFees()
                    break;
                case 3:
                    System.out.println(">> Viewing fees by student...");
                    // TODO: implement viewFeesByStudent()
                    break;
                case 4:
                    System.out.println(">> Viewing fees by course...");
                    // TODO: implement viewFeesByCourse()
                    break;
                case 5:
                    System.out.println(">> Updating fees of a course...");
                    // TODO: implement updateCourseFees()
                    break;
                case 6:
                    System.out.println(">> Calculating total earning...");
                    // TODO: implement calculateTotalEarning()
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
