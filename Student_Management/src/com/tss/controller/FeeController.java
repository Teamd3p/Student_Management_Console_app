package com.tss.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.tss.model.Fees;
import com.tss.service.FeeService;

public class FeeController {

	private FeeService feeService = new FeeService();
	private StudentController studentController = new StudentController();
	private Scanner scanner = new Scanner(System.in);

	public void getTotalPaidFees() {
		try {
			System.out.println("Total Paid: ₹" + feeService.getTotalPaidFees());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void getTotalPendingFees() {
		try {
			System.out.println("Total Pending: ₹" + feeService.getTotalPendingFees());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void getStudentsFees() {
		System.out.print("Enter Student ID: ");
		int studentId = scanner.nextInt();
		scanner.nextLine();

		try {
<<<<<<< HEAD
			fee = feeService.getFeesByStudent(studentId);
			if (fee == null) {
				System.out.println("No Course");
			} else {
				System.out.println(fee);
=======
			List<Fees> fee = feeService.getFeesByStudent(studentId);

			if (fee == null || fee.isEmpty()) {
				System.out.println("No courses assigned to student. No fees data available.");
				return;
			}

			System.out.println(fee); // or loop and format nicely
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean checkPendingFees(int student_id) {
		try {
			List<Fees> fees = feeService.getFeesByStudent(student_id);

			if (fees == null || fees.isEmpty()) {
				return false;  // ✅ No records = no pending fees
			}

			for (Fees fee : fees) {
				if (fee.getAmountPending() > 0.0) {
					return true;  // ✅ Fee is pending
				}
>>>>>>> 0d5075740ac7df5b5ee7987624c80f7c381a0c89
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;  // ✅ No pending fees found
	}


	public void getCourseFees() {
		System.out.print("Enter Course ID: ");
		int courseId = scanner.nextInt();
		try {
			List<Fees> fee = feeService.getFeesByCourse(courseId);
			if (fee != null) {
				System.out.println(fee);
			} else {
				System.out.println("Course not found.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

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

	public void updateCourseFee() {
		System.out.print("Enter Course ID: ");
		int id = scanner.nextInt();
		System.out.print("Enter New Paid Amount: ");
		double paid = scanner.nextDouble();
		try {
			System.out.println(feeService.updateCourseFees(id, paid) ? "Updated." : "Failed.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void getTotalEarning() {
		try {
			System.out.println("Total Earning: ₹" + feeService.getTotalEarning());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void deleteStudent(int id) {
		feeService.deleteStudent(id);
	}

}
