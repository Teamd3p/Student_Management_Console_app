package com.tss.controller;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import com.tss.model.Fees;
import com.tss.service.NotificationService;

public class PaymentController {

    private FeeController feesController;
    private NotificationService notificationService;
    private Scanner scanner = new Scanner(System.in);

    public PaymentController() {
        this.feesController = new FeeController();
        this.notificationService = new NotificationService();
    }

    public void handleStudentFeePayment(int studentId, List<Fees> enrolledCourses) {
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
        System.out.println("\n+--------------------------------------------------------------+");
        System.out.println("|                  Enrolled Courses with Pending Fees          |");
        System.out.println("+--------------------------------------------------------------+");
        System.out.printf("| %-10s | %-25s | %-10s | %-10s |\n", "Course ID", "Course Name", "Paid (₹)", "Pending (₹)");
        System.out.println("+--------------------------------------------------------------+");

        for (Fees fee : enrolledCourses) {
            if (fee.getAmountPending() > 0) {
                System.out.printf("| %-10d | %-25s | %-10.2f | %-11.2f |\n",
                        fee.getCourseId(), fee.getCourseName(), fee.getAmountPaid(), fee.getAmountPending());
            }
        }
        System.out.println("+--------------------------------------------------------------+\n");
    }

    private String choosePaymentMethod() {
        while (true) {
            System.out.println("\nChoose Payment Method:");
            System.out.println("1. Cash\n2. UPI\n3. Card\n4. Cancel");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": return "Cash";
                case "2":
                    System.out.print("Enter UPI ID: ");
                    String upi = scanner.nextLine().trim();
                    if (!upi.matches("^[\\w.-]+@[a-zA-Z]+$")) {
                        System.out.println("Invalid UPI ID.");
                        continue;
                    }
                    return "UPI";
                case "3":
                    if (!validateCard()) return null;
                    return "Card";
                case "4":
                    return null;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private boolean validateCard() {
        System.out.print("Enter 16-digit Card Number: ");
        if (!scanner.nextLine().trim().matches("\\d{16}")) return false;
        System.out.print("Enter 3-digit CVV: ");
        if (!scanner.nextLine().trim().matches("\\d{3}")) return false;
        System.out.print("Enter Expiry Date (MM/YY): ");
        String expiryInput = scanner.nextLine().trim();
        if (!expiryInput.matches("^(0[1-9]|1[0-2])/\\d{2}$") ||
            YearMonth.parse("20" + expiryInput.substring(3), DateTimeFormatter.ofPattern("yyyy"))
                     .withMonth(Integer.parseInt(expiryInput.substring(0, 2)))
                     .isBefore(YearMonth.now())) {
            System.out.println("Invalid or expired card.");
            return false;
        }

        return true;
    }

    private void sendReceipt(int studentId, Fees fee, double amount, String paymentType) {
        String preference = notificationService.getNotificationPreference(studentId);

        if ("SMS".equalsIgnoreCase(preference) || "Both".equalsIgnoreCase(preference)) {
            printReceipt("SMS", studentId, amount, fee.getCourseName(), paymentType);
        }
        if ("Email".equalsIgnoreCase(preference) || "Both".equalsIgnoreCase(preference)) {
            printReceipt("Email", studentId, amount, fee.getCourseName(), paymentType);
        }
        if ("None".equalsIgnoreCase(preference)) {
            System.out.println("No notifications sent.");
        }
    }

	private void printReceipt(String method, int studentId, double amountToPay, String courseName, String paymentType) {
		System.out.println("\n====================================================");
		System.out.println("                   PAYMENT RECEIPT                  ");
		System.out.println("====================================================");
		System.out.printf("Receipt No.    : %05d\n", (int) (Math.random() * 100000));
		System.out.printf("Date & Time    : %s\n", java.time.LocalDateTime.now());
		System.out.printf("Student ID     : %d\n", studentId);
		System.out.printf("Course Name    : %s\n", courseName);
		System.out.printf("Amount Paid    : ₹%.2f\n", amountToPay);
		System.out.printf("Payment Method : %s\n", paymentType);
		System.out.println("----------------------------------------------------");
		System.out.println("          Thank you for your payment!               ");
		System.out.println("      An official receipt has been sent via         ");
		System.out.println("              " + method.toUpperCase() + " NOTIFICATION             ");
		System.out.println("====================================================\n");
	}

}
