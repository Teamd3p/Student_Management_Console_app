package com.tss.controller;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import com.tss.exception.ValidationException;
import com.tss.model.Fees;
import com.tss.service.NotificationService;
import com.tss.util.InputValidator;

public class PaymentController {

	private FeeController feesController;
	private NotificationService notificationService;
	private Scanner scanner = new Scanner(System.in);

	public PaymentController() {
		this.feesController = new FeeController();
		this.notificationService = new NotificationService();
	}

	public void handleStudentFeePayment(int studentId, List<Fees> enrolledCourses) throws ValidationException {
		boolean hasPending = enrolledCourses.stream().anyMatch(f -> f.getAmountPending() > 0);

		if (!hasPending) {
			System.out.println("Fees already paid for all enrolled courses.");
			return;
		}

		printPendingCourses(enrolledCourses);

		System.out.print("Enter Course ID to pay fee for: ");
		int courseId = Integer.parseInt(scanner.nextLine().trim());

		Fees selectedFee = feesController.getFeeByStudentAndCourse(studentId, courseId);

		if (selectedFee == null || selectedFee.getAmountPending() == 0) {
			System.out.println("Invalid course or no pending fee for selected course.");
			return;
		}

		String paymentType = choosePaymentMethod();
		if (paymentType == null) {
			System.out.println("Payment canceled.");
			return;
		}

		System.out.print("Enter amount to pay: ₹");
		double amount = Double.parseDouble(scanner.nextLine().trim());

		if (amount <= 0 || amount > selectedFee.getAmountPending()) {
			System.out.println("Invalid amount.");
			return;
		}

		boolean success = feesController.processFeePayment(studentId, courseId, amount, paymentType);
		if (success) {
			System.out.println("Payment successful via " + paymentType + ". Amount Paid: ₹" + amount);
			sendReceipt(studentId, selectedFee, amount, paymentType);
		} else {
			System.out.println("Payment failed.");
		}
	}

	private void printPendingCourses(List<Fees> enrolledCourses) {
	    // Column widths
	    int col1 = 10; // Course ID
	    int col2 = 25; // Course Name
	    int col3 = 12; // Paid (₹)
	    int col4 = 12; // Pending (₹)

	    int tableWidth = col1 + col2 + col3 + col4 + 13; // extra for separators and spaces

	    String horizontalLine = "+" + "-".repeat(tableWidth - 2) + "+";

	    System.out.println("\n" + horizontalLine);
	    System.out.printf("| %-" + (tableWidth - 4) + "s |\n", "Enrolled Courses with Pending Fees");
	    System.out.println(horizontalLine);
	    System.out.printf("| %-"+col1+"s | %-"+col2+"s | %"+col3+"s | %"+col4+"s |\n",
	            "Course ID", "Course Name", "Paid (₹)", "Pending (₹)");
	    System.out.println(horizontalLine);

	    for (Fees fee : enrolledCourses) {
	        if (fee.getAmountPending() > 0) {
	            System.out.printf("| %-"+col1+"d | %-"+col2+"s | %"+col3+".2f | %"+col4+".2f |\n",
	                    fee.getCourseId(), fee.getCourseName(),
	                    fee.getAmountPaid(), fee.getAmountPending());
	        }
	    }
	    System.out.println(horizontalLine + "\n");
	}


	private String choosePaymentMethod() throws ValidationException {
		System.out.println("╔════════════════════════════════╗");
		System.out.println("║       CHOOSE PAYMENT METHOD    ║");
		System.out.println("╠════════════════════════════════╣");
		System.out.println("║ 1. Cash                        ║");
		System.out.println("║ 2. UPI                         ║");
		System.out.println("║ 3. Card                        ║");
		System.out.println("║ 4. Exit                        ║");
		System.out.println("╚════════════════════════════════╝");

		int choice = InputValidator.readChoice("Enter your choice: ");
		while (true) {
			switch (choice) {
			case 1:
				return "Cash";
			case 2:
				System.out.print("Enter UPI ID: ");
				String upi = scanner.nextLine().trim();
				if (!upi.matches("^[\\w.-]+@[a-zA-Z]+$")) {
					System.out.println("Invalid UPI ID.");
					continue;
				}
				return "UPI";
			case 3:
				if (!validateCard())
					return null;
				return "Card";
			case 4:
				return null;
			default:
				System.out.println("Invalid choice.");
			}
		}
	}

	private boolean validateCard() {
		while (true) {
			System.out.print("Enter 16-digit Card Number: ");
			String cardNumber = scanner.nextLine().trim();
			if (!cardNumber.matches("\\d{16}")) {
				System.out.println("Invalid Card Number. Please enter exactly 16 digits.");
				continue;
			}

			System.out.print("Enter 3-digit CVV: ");
			String cvv = scanner.nextLine().trim();
			if (!cvv.matches("\\d{3}")) {
				System.out.println("Invalid CVV. Please enter exactly 3 digits.");
				continue;
			}

			System.out.print("Enter Expiry Date (MM/YY): ");
			String expiryInput = scanner.nextLine().trim();
			try {
				if (!expiryInput.matches("^(0[1-9]|1[0-2])/\\d{2}$")) {
					System.out.println("Invalid expiry format. Please use MM/YY.");
					continue;
				}

				YearMonth expiry = YearMonth.parse("20" + expiryInput.substring(3), DateTimeFormatter.ofPattern("yyyy"))
						.withMonth(Integer.parseInt(expiryInput.substring(0, 2)));

				if (expiry.isBefore(YearMonth.now())) {
					System.out.println("Card has expired. Please enter a valid date.");
					continue;
				}
			} catch (Exception e) {
				System.out.println("Invalid expiry date format.");
				continue;
			}

			// If all checks pass
			return true;
		}
	}

	private void sendReceipt(int studentId, Fees fee, double amount, String paymentType) {
	    String preference = notificationService.getNotificationPreference(studentId);

	    if ("None".equalsIgnoreCase(preference)) {
	        System.out.println("No notifications sent.");
	        return;
	    }

	    String sendMethod;
	    switch (preference.toLowerCase()) {
	        case "sms":
	            sendMethod = "SMS";
	            break;
	        case "email":
	            sendMethod = "Email";
	            break;
	        case "both":
	            sendMethod = "SMS & Email";
	            break;
	        default:
	            sendMethod = "Unknown";
	    }

	    // Print only one receipt with combined delivery method if needed
	    printReceipt(sendMethod, studentId, amount, fee.getCourseName(), paymentType);
	}


	private static final String RESET = "\u001B[0m";
	private static final String BOLD = "\u001B[1m";
	private static final String BLUE = "\u001B[34m";
	private static final String GREEN_DARK = "\u001B[32;1m";
	private static final String PURPLE_DARK = "\u001B[35;1m";

	private void printReceipt(String method, int studentId, double amountToPay, String courseName, String paymentType) {
		// Layout constants
		final int innerWidth = 52;
		final int labelWidth = 15;
		final int leading = 1;
		final int sepWidth = 3;

		String horiz = repeat('─', innerWidth);
		String topBorder = "┌" + horiz + "┐";
		String midBorder = "├" + horiz + "┤";
		String bottomBorder = "└" + horiz + "┘";

		System.out.println(topBorder);

		String title = "PAYMENT RECEIPT";
		printCenteredLine(title, BOLD + PURPLE_DARK, innerWidth);

		System.out.println(midBorder);

		printRow("Receipt No.", String.format("%05d", (int) (Math.random() * 100000)), BLUE, innerWidth, labelWidth,
				leading, sepWidth);
		String dateTime = java.time.LocalDateTime.now()
				.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		printRow("Date & Time", dateTime, BLUE, innerWidth, labelWidth, leading, sepWidth);
		printRow("Student ID", String.valueOf(studentId), BLUE, innerWidth, labelWidth, leading, sepWidth);
		printRow("Course Name", courseName, BLUE, innerWidth, labelWidth, leading, sepWidth);
		printRow("Amount Paid", "₹" + String.format("%.2f", amountToPay), BOLD + GREEN_DARK, innerWidth, labelWidth,
				leading, sepWidth);
		printRow("Payment Method", paymentType, BLUE, innerWidth, labelWidth, leading, sepWidth);

		System.out.println(midBorder);

		printCenteredLine("Thank you for your payment!", BOLD + GREEN_DARK, innerWidth);
		printCenteredLine("An official receipt has been sent via", BLUE, innerWidth);
		printCenteredLine(method.toUpperCase() + " NOTIFICATION", BOLD + BLUE, innerWidth);

		System.out.println(bottomBorder);
	}

	private void printRow(String label, String valueRaw, String valueColorAnsi, int innerWidth, int labelWidth,
			int leading, int sepWidth) {

		int available = innerWidth - (leading + labelWidth + sepWidth);

		String labelPadded = String.format("%-" + labelWidth + "s", label);

		if (valueRaw.length() <= available) {
			int padRight = innerWidth - (leading + labelWidth + sepWidth + valueRaw.length());
			String content = spaces(leading) + labelPadded + " : " + valueColorAnsi + valueRaw + RESET
					+ spaces(padRight);
			System.out.println("│" + content + "│");
			return;
		}

		String first = valueRaw.substring(0, available);
		int padFirst = innerWidth - (leading + labelWidth + sepWidth + first.length());
		String contentFirst = spaces(leading) + labelPadded + " : " + valueColorAnsi + first + RESET + spaces(padFirst);
		System.out.println("│" + contentFirst + "│");

		int indent = leading + labelWidth + sepWidth;
		int perLine = innerWidth - indent;
		int pos = available;
		while (pos < valueRaw.length()) {
			int end = Math.min(valueRaw.length(), pos + perLine);
			String part = valueRaw.substring(pos, end);
			int pad = innerWidth - (indent + part.length());
			String cont = spaces(indent) + valueColorAnsi + part + RESET + spaces(pad);
			System.out.println("│" + cont + "│");
			pos = end;
		}
	}

	private void printCenteredLine(String rawText, String colorAnsi, int innerWidth) {
		int leftPad = (innerWidth - rawText.length()) / 2;
		int rightPad = innerWidth - leftPad - rawText.length();
		String content = spaces(leftPad) + colorAnsi + rawText + RESET + spaces(rightPad);
		System.out.println("│" + content + "│");
	}

	private static String repeat(char ch, int count) {
		if (count <= 0)
			return "";
		char[] arr = new char[count];
		java.util.Arrays.fill(arr, ch);
		return new String(arr);
	}

	private static String spaces(int n) {
		if (n <= 0)
			return "";
		return repeat(' ', n);
	}

}
