package com.tss.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.tss.model.Course;
import com.tss.model.Fees;
import com.tss.model.StudentCourse;
import com.tss.service.CourseService;
import com.tss.service.FeeService;
import com.tss.service.StudentCourseService;

public class StudentCourseController {

	private final StudentCourseService studentCourseService;
	private final Scanner scanner;
	private final StudentController studentController;
	private final CourseController courseController;
	private final FeeController feesController;
	private final FeeService feesservice;
	private final CourseService courseService;
	
	

	public StudentCourseController() {
		this.studentCourseService = new StudentCourseService();
		this.studentController = new StudentController();
		this.courseController = new CourseController();
		this.feesController = new FeeController();
		this.scanner = new Scanner(System.in);
		this.feesservice = new FeeService();
		this.courseService = new CourseService();
	}

	public void AssignCourseToStudent(StudentController studentController, CourseController courseController) {
		try {
			studentController.readAllRecords();

			System.out.print("Enter Student ID: ");
			int studentId = Integer.parseInt(scanner.nextLine().trim());

			if (studentController.studentExistance(studentId)) {
				courseController.radAllActiveCourse();
				System.out.print("Enter Course ID: ");
				int courseId = Integer.parseInt(scanner.nextLine().trim());

				if (courseController.courseExistance(courseId)) {
					StudentCourse studentCourse = new StudentCourse();
					studentCourse.setStudentId(studentId);
					studentCourse.setCourseId(courseId);
					studentCourse.setEnrolledAt(LocalDateTime.now());
					
					studentCourseService.AssignCourseToStudent(studentCourse);
					
					Course course = courseService.searchCourse(courseId);
					
					
					Fees fee = new Fees(courseId,studentId,0.0,course.getCourseFees());
					feesservice.insertNewRecord(fee);
					
					return;
				}
				System.out.println("Course With id " + courseId + " doesn't exists Or Not Active!!");
				return;
			}
			System.out.println("Student With id " + studentId + " doesn't exists !!");
		} catch (NumberFormatException e) {
			System.out.println("Invalid input. Please enter numeric values for IDs.");
		} catch (Exception e) {
			System.out.println("An error occurred while assigning course.");
			e.printStackTrace();
		}
	}

//	public List<Fees> viewCourseByStudentId(int id) {
//		if (studentController.studentExistance(id)) {
//			List<Fees> courses = studentCourseService.getCourseByStudentId(id); 
//
//			if (courses == null || courses.isEmpty()) {
//				System.out.println("No courses found for student ID: " + id);
//				return null;
//			}
//
//			System.out.println("\n+-------------------------------------------------------------+");
//			System.out.println("|                    Enrolled Courses                        |");
//			System.out.println("+-------------------------------------------------------------+");
//			System.out.printf("| %-10s | %-25s | %-10s |\n", "Course ID", "Course Name", "Course Fee");
//			System.out.println("+-------------------------------------------------------------+");
//
//			for (Fees course : courses) {
//				System.out.printf("| %-10d | %-25s | ₹%-9.2f |\n", course.getCourseId(), course.getCourseName(),
//						course.getAmountPaid(), course.getAmountPending());
//			}
//
//			System.out.println("+-------------------------------------------------------------+");
//		}
//		System.out.print("Enter Course ID: ");
//		int courseId = Integer.parseInt(scanner.nextLine().trim());
//		if (courseController.courseExistance(courseId)) {
//			Fees fee = feesController.getFeeByStudentAndCourse(id, courseId);
//			if (fee != null) {
//				System.out.println("\n------------------------------------");
//				System.out.println("           Fee Details              ");
//				System.out.println("------------------------------------");
//				System.out.println("Course Id     : " + fee.getCourseId());
//				System.out.println("Course Name     : " + fee.getCourseName());
//				System.out.println("Amount Paid     : ₹" + fee.getAmountPaid());
//				System.out.println("Amount Pending  : ₹" + fee.getAmountPending());
//				System.out.println("------------------------------------\n");
//			} else {
//				System.out.println("No fee record found for the given student and course.");
//			}
//		}
//		return fee;
//
//	}

	public List<Fees> getEnrolledCoursesByStudentId(int studentId) {
	    if (studentController.studentExistance(studentId)) {
	        return studentCourseService.getCourseByStudentId(studentId);
	    }
	    return new ArrayList<>();
	}

	public void getAllCourses(int id) {
		List<Course> courses = studentCourseService.getAllCourses(id);

		if (courses.isEmpty() || courses == null) {
			System.out.println("Student With ID " + id + " Not Enrolled in Any Course !!");
			return;
		}
		String border = "+-------------+--------------------------+-----------+--------+";
		String title = "|                 Student's Enrolled Courses                  |";

		System.out.println("+-------------------------------------------------------------+");
		System.out.println(title);
		System.out.println(border);
		System.out.printf("| %-11s | %-24s | %-9s | %-6s |\n", "Course ID", "Course Name", "Fees (₹)", "Active");
		System.out.println(border);

		for (Course course : courses) {
			System.out.printf("| %-11d | %-24s | ₹%-8.2f | %-6s |\n", course.getCourseId(), course.getCourseName(),
					course.getCourseFees(), course.isActive() ? "Yes" : "No");
		}

		System.out.println(border);		
	}

}
