package com.tss.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.tss.model.Fees;
import com.tss.service.FeeService;

public class FeesController {

	private FeeService feeService;
	private Scanner scanner = new Scanner(System.in);

	public FeesController() {
		this.feeService = new FeeService();
	}

	public void menu() {
		int choice;
		do {
			System.out.println("\n4 Fees Management");
			System.out.println("1. View Total Paid Fees");
			System.out.println("2. View Total Pending Fees");
			System.out.println("3. View Fees By Student");
			System.out.println("4. View Fees By Course");
			System.out.println("5. Update Fees Of A Course");
			System.out.println("6. Total Earning");
			System.out.println("7. Exit");
			System.out.print("Enter your choice: ");
			choice = scanner.nextInt();

			try {
				switch (choice) {
				case 1:
					System.out.println("Total Paid: ₹" + feeService.getTotalPaidFees());
					break;
				case 2:
					System.out.println("Total Pending: ₹" + feeService.getTotalPendingFees());
					break;
				case 3:
					List<Fees> feeList = feeService.getFeesByStudent(getStudentId());
					printFeeForStudent(feeList);
					break;
				case 4:

					printFeeCourses();
					break;
				case 5:
					System.out.print("Enter Course ID: ");
					int id = scanner.nextInt();
					System.out.print("Enter New Paid Amount: ");
					double paid = scanner.nextDouble();
					System.out.println(feeService.updateCourseFees(id, paid) ? "Updated." : "Failed.");
					break;
				case 6:
					System.out.println("Total Earning: ₹" + feeService.getTotalEarning());
					break;
				}
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		} while (choice != 7);
	}

	private int getStudentId() {
		System.out.print("Enter Student ID: ");
		return scanner.nextInt();
	}

	public void printFeeForStudent(List<Fees> feeList) {
		System.out.println(
				"+------------------------------------------------------------------------------------------+");
		System.out.println(
				"|                               STUDENT FEES RECORD                                        |");
		System.out.println(
				"+------------------------------------------------------------------------------------------+");
		System.out.printf("| %-8s | %-12s | %-20s | %-10s | %-10s | %-10s |\n", "Fee ID", "Student ID", "Student Name",
				"Total Fee", "Paid", "Pending");
		System.out.println(
				"+------------------------------------------------------------------------------------------+");

		for (Fees fee : feeList) {
			double totalFee = fee.getAmountPaid() + fee.getAmountPending();
			System.out.printf("| %-8d | %-12d | %-20s | %-10.2f | %-10.2f | %-10.2f |\n", fee.getFeeId(),
					fee.getStudentId(), fee.getStudentName(), totalFee, fee.getAmountPaid(), fee.getAmountPending());
		}

		System.out.println(
				"+------------------------------------------------------------------------------------------+");
	}

	private void printFeeCourses() {
		System.out.println("Enter Course ID: ");
		int courseId = scanner.nextInt();

		try {
			List<Fees> feeList = feeService.getCourseFeesSummary(courseId); // service layer method
			if (feeList.isEmpty()) {
				System.out.println("No data found for this course.");
				return;
			}

			System.out.println("+------------------------------------------------------+");
			System.out.println("|                  COURSE FEE SUMMARY                  |");
			System.out.println("+------------------------------------------------------+");
			System.out.printf("| %-10s | %-20s | %-12s |\n", "Course ID", "Course Name", "Total Fee");
			System.out.println("+------------------------------------------------------+");

			for (Fees fee : feeList) {
				double totalFee = fee.getAmountPaid() + fee.getAmountPending();
				System.out.printf("| %-10d | %-20s | %-12.2f |\n", fee.getCourseId(), fee.getCourseName(), totalFee);
			}

			System.out.println("+------------------------------------------------------+");

		} catch (SQLException e) {
			System.out.println("Error fetching course fee summary: " + e.getMessage());
		}

	}

}
