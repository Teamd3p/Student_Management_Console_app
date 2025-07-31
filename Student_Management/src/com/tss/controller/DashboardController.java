package com.tss.controller;

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
		    
		 // Sort the data by studentId
		    data.sort((d1, d2) -> Integer.compare(d1.getStudentId(), d2.getStudentId()));

		    String line = "+" +
		            "----+" +
		            "------------+" +
		            "----------------------+ " +
		            "------------------------+" +
		            "------------+" +
		            "--------------+" +
		            "------------+" +
		            "------------------------------+" +
		            "------------------------------+";
		    
		    String header = String.format("| %-2s | %-10s | %-20s | %-22s | %-10s | %-12s | %-10s | %-28s | %-28s |",
		            "No", "Student ID", "Student Name", "Course", "Paid Fee", "Pending Fee", "Total Fee", "Subjects", "Teachers");


		    System.out.println(line);
		    System.out.printf("|%92s|\n", "STUDENT DASHBOARD");
		    System.out.println(line);
		    System.out.println(header);
		    System.out.println(line);

		    int count = 1;
		    for (Dashboard d : data) {
		        String studentName = fixedWidth(d.getStudentName(), 20);
		        String course = fixedWidth(d.getCourseName(), 22);
		        String subjects = fixedWidth(d.getSubjects(), 28);
		        String teachers = fixedWidth(d.getTeachers(), 28);

		        System.out.printf("| %-2d | %-10d | %-20s | %-22s | %-10.2f | %-12.2f | %-10.2f | %-28s | %-28s |\n",
		                count++, d.getStudentId(), studentName, course,
		                d.getPaidFee(), d.getPendingFee(), d.getTotalFee(),
		                subjects, teachers
		        );

		       
		    }
		    System.out.println(line);
		}

	 
	 private String fixedWidth(String input, int width) {
		    if (input == null || input.trim().isEmpty()) return "null";
		    return input.length() > width ? input.substring(0, width - 3) + "..." : String.format("%-" + width + "s", input);
		}
}
