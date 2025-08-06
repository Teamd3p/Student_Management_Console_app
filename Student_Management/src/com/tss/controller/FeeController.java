package com.tss.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.tss.model.Fees;
import com.tss.service.FeeService;

public class FeeController {

	private FeeService feeService = new FeeService();
	private Scanner scanner = new Scanner(System.in);

	public void getTotalPaidFees() {
		try {
			double totalPaid = feeService.getTotalPaidFees();

			String border = "+-------------------------------------------+";
			String title  = "|               TOTAL FEES PAID             |";
			String header = "| Description              | Amount (₹)     |";
			String separator = "|--------------------------|----------------|";

			System.out.println();
			System.out.println(border);
			System.out.println(title);
			System.out.println(border);
			System.out.println(header);
			System.out.println(separator);
			System.out.printf("| %-24s | ₹%-14.2f|\n", "All Students Combined", totalPaid);
			System.out.println(border);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void getTotalPendingFees() {
		try {
			double totalPending = feeService.getTotalPendingFees();

			String border    = "+-------------------------------------------+";
			String title     = "|              TOTAL FEES PENDING           |";
			String header    = "| Description              | Amount (₹)     |";
			String separator = "|--------------------------|----------------|";

			System.out.println();
			System.out.println(border);
			System.out.println(title);
			System.out.println(border);
			System.out.println(header);
			System.out.println(separator);
			System.out.printf("| %-24s | ₹%-14.2f|\n", "All Students Combined", totalPending);
			System.out.println(border);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void getStudentsFees() {
		System.out.print("Enter Student ID: ");
		int studentId = scanner.nextInt();
		List<Fees> fee;
		try {
			List<Fees> feeList = feeService.getFeesByStudent(studentId);
			
			if (feeList == null || feeList.isEmpty()) {
				System.out.println("No fee records found for this student.");
				return;
			}

			String border    = "+-------------------------------------------------------------------------------------+";
			String title     = "|                                STUDENT FEES DETAILS                                 |";
			String header    = "| Fee ID | Course Name        | Amount Paid (₹)  | Amount Pending (₹) | Student Name  |";
			String separator = "|--------|--------------------|------------------|--------------------|---------------|";

			System.out.println();
			System.out.println(border);
			System.out.println(title);
			System.out.println(border);
			System.out.println(header);
			System.out.println(separator);

			for (Fees f : feeList) {
				System.out.printf("| %-6d | %-18s | ₹%-14.2f  | ₹%-16.2f  | %-14s|\n",
						f.getFeeId(),truncate(f.getCourseName(), 18),f.getAmountPaid(),f.getAmountPending(),
						truncate(f.getStudentName(), 14)
				);
			}
			System.out.println(border);		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String truncate(String value, int limit) {
		if (value == null) return "N/A";
		return value.length() > limit ? value.substring(0, limit - 3) + "..." : value;
	}
	
//	public void getCourseFees() {
//		System.out.print("Enter Course ID: ");
//		int courseId = scanner.nextInt();
//		try {
//			List<Fees> fee = feeService.getFeesByCourse(courseId);
//			if (fee != null) {
//				System.out.println(fee);
//			} else {
//				System.out.println("Course not found.");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//	}

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

	public void updateCourseFee() {
		System.out.print("Enter Course ID: ");
		int id = scanner.nextInt();
		System.out.print("Enter New Paid Amount: ");
		double paid = scanner.nextDouble();
		try {
			System.out.println(feeService.updateCourseFees(id, paid) ? "Updated." : "Failed.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void getTotalEarning() {
		try {
			double totalEarning = feeService.getTotalEarning();

			String border    = "+-------------------------------------------+";
			String title     = "|              TOTAL EARNINGS               |";
			String header    = "| Description              | Amount (₹)     |";
			String separator = "|--------------------------|----------------|";

			System.out.println();
			System.out.println(border);
			System.out.println(title);
			System.out.println(border);
			System.out.println(header);
			System.out.println(separator);
			System.out.printf("| %-24s | ₹%-14.2f|\n", "Fees Collected (All)", totalEarning);
			System.out.println(border);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Fees getFeeByStudentAndCourse(int id, int courseId) {
		return feeService.getFeeByStudentAndCourse(id, courseId);
	}

	public boolean processFeePayment(int studentId , int courseId, double amountToPay, String paymentType) {
		return feeService.processFeePayment(studentId, courseId, amountToPay, paymentType);
	}

}
