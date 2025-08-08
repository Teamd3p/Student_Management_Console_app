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

 // ANSI color constants (place at class level or above these methods)
    private static final String RESET      = "\u001B[0m";
    private static final String BOLD       = "\u001B[1m";
    private static final String BLUE       = "\u001B[34m";
    private static final String GREEN_DARK = "\u001B[32;1m";
    private static final String PURPLE_DARK= "\u001B[35;1m";

    // Main print method (paste into your class)
    private void printReceipt(String method, int studentId, double amountToPay, String courseName, String paymentType) {
        // Layout constants
        final int innerWidth = 52;   // number of characters BETWEEN the vertical bars
        final int labelWidth = 15;   // width for the label (left column)
        final int leading = 1;       // 1 space after the left border
        final int sepWidth = 3;      // " : "

        // Build borders dynamically so they always match innerWidth
        String horiz = repeat('─', innerWidth);
        String topBorder = "┌" + horiz + "┐";
        String midBorder = "├" + horiz + "┤";
        String bottomBorder = "└" + horiz + "┘";

        System.out.println(topBorder);

        // Title (centered)
        String title = "PAYMENT RECEIPT";
        printCenteredLine(title, BOLD + PURPLE_DARK, innerWidth);

        System.out.println(midBorder);

        // Rows (we pass raw value and color separately so wrapping & length computation are correct)
        printRow("Receipt No.", String.format("%05d", (int) (Math.random() * 100000)), BLUE, innerWidth, labelWidth, leading, sepWidth);
        // short date format so it fits better
        String dateTime = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        printRow("Date & Time", dateTime, BLUE, innerWidth, labelWidth, leading, sepWidth);
        printRow("Student ID", String.valueOf(studentId), BLUE, innerWidth, labelWidth, leading, sepWidth);
        printRow("Course Name", courseName, BLUE, innerWidth, labelWidth, leading, sepWidth);
        printRow("Amount Paid", "₹" + String.format("%.2f", amountToPay), BOLD + GREEN_DARK, innerWidth, labelWidth, leading, sepWidth);
        printRow("Payment Method", paymentType, BLUE, innerWidth, labelWidth, leading, sepWidth);

        System.out.println(midBorder);

        // Footer — centered lines (with requested colors)
        printCenteredLine("Thank you for your payment!", BOLD + GREEN_DARK, innerWidth);
        printCenteredLine("An official receipt has been sent via", BLUE, innerWidth);
        printCenteredLine(method.toUpperCase() + " NOTIFICATION", BOLD + BLUE, innerWidth);

        System.out.println(bottomBorder);
    }

    // Helper to print a row with wrapping and ANSI-safe alignment
    private void printRow(String label, String valueRaw, String valueColorAnsi,
                          int innerWidth, int labelWidth, int leading, int sepWidth) {

        // available characters for value on first line:
        int available = innerWidth - (leading + labelWidth + sepWidth);

        // prepare label padded
        String labelPadded = String.format("%-" + labelWidth + "s", label);

        // if fits on single line:
        if (valueRaw.length() <= available) {
            int padRight = innerWidth - (leading + labelWidth + sepWidth + valueRaw.length());
            String content = spaces(leading) + labelPadded + " : " + valueColorAnsi + valueRaw + RESET + spaces(padRight);
            System.out.println("│" + content + "│");
            return;
        }

        // first line (label + first part of value)
        String first = valueRaw.substring(0, available);
        int padFirst = innerWidth - (leading + labelWidth + sepWidth + first.length());
        String contentFirst = spaces(leading) + labelPadded + " : " + valueColorAnsi + first + RESET + spaces(padFirst);
        System.out.println("│" + contentFirst + "│");

        // subsequent continuation lines (value column only)
        int indent = leading + labelWidth + sepWidth;          // spaces to align under the value column
        int perLine = innerWidth - indent;                     // width available per continuation line
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

    // Helper to print centered line (uses raw text length for centering and prints colored text)
    private void printCenteredLine(String rawText, String colorAnsi, int innerWidth) {
        int leftPad = (innerWidth - rawText.length()) / 2;
        int rightPad = innerWidth - leftPad - rawText.length();
        String content = spaces(leftPad) + colorAnsi + rawText + RESET + spaces(rightPad);
        System.out.println("│" + content + "│");
    }

    // small utility: repeat a char n times
    private static String repeat(char ch, int count) {
        if (count <= 0) return "";
        char[] arr = new char[count];
        java.util.Arrays.fill(arr, ch);
        return new String(arr);
    }

    // small utility: spaces
    private static String spaces(int n) {
        if (n <= 0) return "";
        return repeat(' ', n);
    }
}
