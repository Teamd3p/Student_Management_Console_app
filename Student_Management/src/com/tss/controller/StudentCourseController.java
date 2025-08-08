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

	public boolean AssignCourseToStudent(int studentId) {
		try {			

			if (studentController.studentExistance(studentId)) {
				courseController.radAllActiveCourse();
				System.out.print("Enter Course ID: ");
				int courseId = Integer.parseInt(scanner.nextLine().trim());

				if (courseController.courseExistance(courseId)) {
					StudentCourse studentCourse = new StudentCourse();
					studentCourse.setStudentId(studentId);
					studentCourse.setCourseId(courseId);
					studentCourse.setEnrolledAt(LocalDateTime.now());

					if(studentCourseService.AssignCourseToStudent(studentCourse))
					{

					Course course = courseService.searchCourse(courseId);

					Fees fee = new Fees(courseId, studentId, 0.0, course.getCourseFees());
					feesservice.insertNewRecord(fee);

					System.out.print("You Want To Pay Fees Now(Yes/No): ");
					String choice = scanner.nextLine();

					if (choice.equalsIgnoreCase("yes")) {
						studentController.payStudentFees(studentId);
					}
					}
					return true;
				}
				System.out.println("Course With id " + courseId + " doesn't exists Or Not Active!!");
			}
			System.out.println("Student With id " + studentId + " doesn't exists Or Not Active !!");
		} catch (NumberFormatException e) {
			System.out.println("Invalid input. Please enter numeric values for IDs.");
		} catch (Exception e) {
			System.out.println("An error occurred while assigning course.");
			e.printStackTrace();
		}
		return false;

	}

	public List<Fees> getEnrolledCoursesByStudentId(int studentId) {
		if (studentController.studentExistance(studentId)) {
			return studentCourseService.getCourseByStudentId(studentId);
		}
		return new ArrayList<>();
	}

	public void getAllCourses(int id) {
		List<Course> courses = studentCourseService.getAllCourses(id);

		if (courses == null || courses.isEmpty()) {
			System.out.println("Student With ID " + id + " Not Enrolled in Any Course !!");
			return;
		}

		String border = "+----------------------------------------------------------------------+";
		String header = "|                         Student's Enrolled Courses                   |";
		String columns = "| Course ID   | Course Name                    | Fees (₹)     | Active |";
		String separator = "|-------------|--------------------------------|--------------|--------|";

		System.out.println();
		System.out.println(border);
		System.out.println(header);
		System.out.println(border);
		System.out.println(columns);
		System.out.println(separator);

		for (Course course : courses) {
			String courseName = truncate(course.getCourseName(), 30);
			System.out.printf("| %-11d | %-30s | ₹%-11.2f | %-6s |\n",
					course.getCourseId(),
					courseName,
					course.getCourseFees(),
					course.isActive() ? "Yes" : "No");
		}

		System.out.println(border);
	}

	private String truncate(String text, int limit) {
		if (text == null) return "N/A";
		return text.length() > limit ? text.substring(0, limit - 3) + "..." : text;
	}
}
