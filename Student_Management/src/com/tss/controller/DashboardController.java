package com.tss.controller;

import java.util.ArrayList;
import java.util.List;

import com.tss.model.Dashboard;
import com.tss.service.DashBoardService;

public class DashboardController {

    private DashBoardService dashboardService;

    public DashboardController() {
        this.dashboardService = new DashBoardService();
    }

    public void showDashboard() {
        List<Dashboard> data = dashboardService.getDashboardData();
        data.sort((d1, d2) -> Integer.compare(d1.getStudentId(), d2.getStudentId()));

     // ANSI Colors
        final String RESET = "\u001B[0m";
        final String LIGHT_BLUE = "\u001B[32m";
        final String LIGHT_RED = "\u001B[31m";
        final String YELLOW = "\u001B[33m";
        final String BLUE = "\u001B[34m";
        final String BLACK = "\u001B[30m";
        final String BOLD = "\u001B[1m";
        
     // Top line and title
        String topLine = "╔════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗";
        String title = BOLD + "STUDENT DASHBOARD" + RESET;
        int totalWidth = topLine.length() - 2;
        int padding = (totalWidth - "STUDENT DASHBOARD".length()) / 2;
        String titleLine = "║" + " ".repeat(padding) + title + " ".repeat(totalWidth - padding - "STUDENT DASHBOARD".length()) + "║";

        // Column headers
        String headerLine = "╠════╦════════════╦══════════════════════╦══════════════════════════╦════════════╦════════════════╦════════════╦════════════════════════════════╦════════════════════════════════╣";
        String bottomLine = "╚════╩════════════╩══════════════════════╩══════════════════════════╩════════════╩════════════════╩════════════╩════════════════════════════════╩════════════════════════════════╝";

       

        // Print header
        System.out.println(topLine);
        System.out.println(titleLine);
        System.out.println(headerLine);
        System.out.printf("║ %-2s ║ %-10s ║ %-20s ║ %-24s ║ %-10s ║ %-14s ║ %-10s ║ %-30s ║ %-30s ║\n",
                "No", "Student ID", "Student Name", "Course", "Paid Fee", "Pending Fee", "Total Fee", "Subjects", "Teachers");
        System.out.println(headerLine);

        // Totals
        int count = 1;
        double totalPaid = 0, totalPending = 0, totalCourseFee = 0;

        for (Dashboard d : data) {
            String studentName = fixedWidth(d.getStudentName(), 20);
            String course = fixedWidth(String.join(", ", d.getCourseNames()), 24);
            String subjects = fixedWidth(d.getSubjects(), 30);
            String teachers = fixedWidth(d.getTeachers(), 30);

            double paid = d.getTotalPaid();
            double pending = d.getTotalPending();
            double total = d.getTotalFee();

            totalPaid += paid;
            totalPending += pending;
            totalCourseFee += total;

            System.out.printf("║ %-2d ║" + BLUE + " %-10d" + RESET + " ║ " + BLUE + "%-18s" + RESET + " ║ " + BLUE + "%-24s" + RESET + " ║ "
                            + LIGHT_BLUE + "%-10s" + RESET + " ║ " + LIGHT_RED + "%-14s" + RESET + " ║ "
                            + YELLOW + "%-10s" + RESET + " ║ " + BLUE + "%-30s" + RESET + " ║ "
                            + BLUE + "%-30s" + RESET + " ║\n",
                    count++, d.getStudentId(), studentName, course,
                    String.format("%.2f", paid), String.format("%.2f", pending), String.format("%.2f", total),
                    subjects, teachers);
        }

        System.out.println(headerLine);
        System.out.printf("║ %-2s ║ %-10s ║ %-20s ║ %-24s ║ " + LIGHT_BLUE + "%-10s" + RESET + " ║ "
                        + LIGHT_RED + "%-14s" + RESET + " ║ " + YELLOW + "%-10s" + RESET + " ║ %-30s ║ %-30s ║\n",
                "**", "TOTAL", "", "",
                String.format("%.2f", totalPaid), String.format("%.2f", totalPending), String.format("%.2f", totalCourseFee),
                "", "");
        System.out.println(bottomLine);
    }
    
    public void showFeesBarChart() {
        List<Dashboard> data = dashboardService.getDashboardData();

        // ANSI Colors
        final String RESET = "\u001B[0m";
        final String LIGHT_BLUE = "\u001B[94m";
        final String LIGHT_RED = "\u001B[91m";
        final String BLACK = "\u001B[30m";

        int barLength = 30; // inner bar length
        int extraSpace = 4; // extra padding in the Fees Status column
        int feesStatusColWidth = barLength + extraSpace; // total width for that column
        
     // Top border
        System.out.println("╔════════════════════╦" + "═".repeat(feesStatusColWidth ) + "╦══════════════╗");

        // Header row
        System.out.printf("║ %-18s ║ %-"+(feesStatusColWidth-2)+"s ║ %-12s ║%n", 
                          "Name", "Fees Status", "Paid %");

        // Header separator
        System.out.println("╠════════════════════╬" + "═".repeat(feesStatusColWidth ) + "╬══════════════╣");


        for (Dashboard d : data) {
            double total = d.getTotalPaid() + d.getTotalPending();
            double paidPercent = (total > 0) ? (d.getTotalPaid() / total) * 100 : 0;
            int paidBars = (int) ((paidPercent / 100) * barLength);
            int pendingBars = barLength - paidBars;

            // Inner bar content
            String paidBar = LIGHT_BLUE + "█".repeat(paidBars) + RESET;
            String pendingBar = LIGHT_RED + "█".repeat(Math.max(0, pendingBars)) + RESET;
            String barContent = paidBar + pendingBar;

            // Mini box around bar
            String topBorder = BLACK + "┌" + "─".repeat(barLength) + "┐" + RESET;
            String middle = BLACK + "│" + RESET + barContent + BLACK + "│" + RESET;
            String bottomBorder = BLACK + "└" + "─".repeat(barLength) + "┘" + RESET;

            System.out.printf("║ %-18s ║ %-" + feesStatusColWidth + "s ║ %-12s ║%n",
                    d.getStudentName(), topBorder, "");
            System.out.printf("║ %-18s ║ %-" + feesStatusColWidth + "s ║ %-12.1f ║%n",
                    "", middle, paidPercent);
            System.out.printf("║ %-18s ║ %-" + feesStatusColWidth + "s ║ %-12s ║%n",
                    "", bottomBorder, "");

            // Row separator
            System.out.println("╠════════════════════╬" + "═".repeat(barLength + 4) + "╬══════════════╣");
        }

        // Table bottom border
        System.out.println("╚════════════════════╩" + "═".repeat(barLength + 4) + "╩══════════════╝");
    }



    private String fixedWidth(String input, int width) {
        if (input == null || input.trim().isEmpty()) return "null";
        return input.length() > width ? input.substring(0, width - 3) + "..." : String.format("%-" + width + "s", input);
    }
}
