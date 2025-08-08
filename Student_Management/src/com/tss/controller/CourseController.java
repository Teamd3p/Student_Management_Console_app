package com.tss.controller;

import java.util.List;
import java.util.Scanner;

import com.tss.exception.ValidationException;
import com.tss.model.Course;
import com.tss.service.CourseService;
import com.tss.util.InputValidator;

public class CourseController {
	private CourseService courseService;
	private Scanner scanner = new Scanner(System.in);
	private SubjectCourseController subjectCourseController;

	public CourseController() {
		this.courseService = new CourseService();
	}

	public void readAllCourseRecords() {
		List<Course> courses = courseService.readAllCourses();

		System.out.println("\n+-------------------------------------------------------------+");
		System.out.println("|                        COURSE RECORDS                       |");
		System.out.println("+-------------------------------------------------------------+");
		System.out.printf("| %-10s | %-20s | %-10s | %-10s |\n", "Course ID", "Course Name", "Fees", "Active");
		System.out.println("+-------------------------------------------------------------+");

		for (Course course : courses) {
			String name = truncate(course.getCourseName(), 20);
			System.out.printf("| %-10d | %-20s | %-10.2f | %-10s |\n",
					course.getCourseId(),
					name,
					course.getCourseFees(),
					course.isActive() ? "Yes" : "No");
		}

		System.out.println("+-------------------------------------------------------------+");
	}
	
	
	public void addNewCourse() {
		String name;
		double fees = -1;

		subjectCourseController = new SubjectCourseController();
		// Validate course name (non-empty + contains alphabet)
		while (true) {
			System.out.print("Enter Course Name: ");
			name = scanner.nextLine().trim();

			if (name.isEmpty()) {
				System.out.println(">> Course name cannot be empty. Please try again.");
			} else if (!name.matches("[a-zA-Z ]+")) {
			    System.out.println(">> Course name must contain only alphabets and spaces.");
			} else {
				break;
			}
		}

		while (true) {
			System.out.print("Enter Course Fees: ");
			String input = scanner.nextLine().trim();

			try {
				fees = Double.parseDouble(input);
				if (fees < 0) {
					System.out.println(">> Course fees cannot be negative. Please enter a positive amount.");
				} else {
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println(">> Invalid input. Please enter a valid number for fees.");
			}
		}

		// Create and save course
		Course course = new Course();
		course.setCourseName(name);
		course.setCourseFees(fees);

		Course courses = courseService.addCourse(course);
		if (courses!=null) {
			System.out.println(">> Course added successfully.");
			return;
		} else {
			System.out.println(">> Failed to add course.");
		}
	}

	public void searchCourse() {

		int course_id = InputValidator.readId("Enter course id to search the course");

		Course course = courseService.searchCourse(course_id);

		if (course != null) {
			String border = "+------------+-------------------------------+--------------+-------+";
			String header = "| Course ID  | Course Name                   | Fees (₹)     | Active|";

			System.out.println();
			System.out.println(border);
			System.out.println("|                      COURSE RECORDS                               |");
			System.out.println(border);
			System.out.println(header);
			System.out.println(border);

			String courseName = truncate(course.getCourseName(), 30);

			System.out.printf("| %-10d | %-30s|₹%10.2f   | %-6s|\n",
					course.getCourseId(),courseName,course.getCourseFees(),course.isActive() ? "Yes" : "No");
			System.out.println(border);
		} else {
			System.out.println("\nNo existing course with this ID: " + course_id);
		}
	}
	
	public boolean courseExistance(int course_id) {
		Course course = courseService.searchCourse(course_id);
		return course != null && course.isActive();

	}

	public void softDeleteCourse() {

		radAllActiveCourse();

		int course_id = InputValidator.readId("Enter course id to delete the course");

		Course course = courseService.softDeleteCourse(course_id);

		if (course != null) {
			System.out.println("\n+-------------------------------------------------------------+");
			System.out.println("|                        COURSE RECORDS                       |");
			System.out.println("+-------------------------------------------------------------+");
			System.out.printf("| %-10s | %-20s | %-10s | %-10s |\n", "Course ID", "Course Name", "Fees", "Active");
			System.out.println("+-------------------------------------------------------------+");

			System.out.printf("| %-10d | %-20s | %-10.2f | %-10s |\n", course.getCourseId(), course.getCourseName(),
					course.getCourseFees(), course.isActive() ? "Yes" : "No");

			System.out.println("+-------------------------------------------------------------+");
			System.out.println("\nCourse was successfully marked as inactive.");
		} else {
			System.out.println("\nNo active course found with ID: " + course_id + " or it may already be inactive.");
		}
	}

	public List<Course> radAllActiveCourse() {
		List<Course> courses = courseService.readAllActiveCourses();		
		return courses;
	}
	public void printAllActiveCourse()
	{
		List<Course> courses = courseService.readAllActiveCourses();

		System.out.println("\n+-------------------------------------------------------------+");
		System.out.println("|                        COURSE RECORDS                       |");
		System.out.println("+-------------------------------------------------------------+");
		System.out.printf("| %-10s | %-20s | %-10s | %-10s |\n", "Course ID", "Course Name", "Fees", "Active");
		System.out.println("+-------------------------------------------------------------+");

		for (Course course : courses) {
			String name = truncate(course.getCourseName(), 20);
			System.out.printf("| %-10d | %-20s | %-10.2f | %-10s |\n",
					course.getCourseId(),
					name,
					course.getCourseFees(),
					course.isActive() ? "Yes" : "No");
		}

		System.out.println("+-------------------------------------------------------------+");
	}
	
	
	private String truncate(String value, int limit) {
		if (value == null) return "N/A";
		return value.length() > limit ? value.substring(0, limit - 3) + "..." : value;
	}

	public void restoreCourse() {
		List<Course> courses = courseService.readAlldeActiveCourses();
		
		if(courses.isEmpty())
		{
			System.out.println("Course Is Empty !!");
			return;
		}
		System.out.println("\n+-------------------------------------------------------------+");
		System.out.println("|                DEACTIVATED COURSE RECORDS                   |");
		System.out.println("+-------------------------------------------------------------+");
		System.out.printf("| %-10s | %-20s | %-10s | %-10s |\n", "Course ID", "Course Name", "Fees", "Active");
		System.out.println("+-------------------------------------------------------------+");

		for (Course course : courses) {
			String name = truncate(course.getCourseName(), 20);
			System.out.printf("| %-10d | %-20s | %-10.2f | %-10s |\n",
					course.getCourseId(),
					name,
					course.getCourseFees(),
					course.isActive() ? "Yes" : "No");
		}

		System.out.println("+-------------------------------------------------------------+");
		
	
	
	try {
		int courseId = InputValidator.readId("Enter Course ID: ");
		Course course = courseService.searchCourse(courseId);
		if (course == null || course.isActive()) {
			System.out.println("Course Is Already Active Or Not Found !!");
			return;
		}

		boolean restored = courseService.restoreCourse(courseId);
		System.out.println(restored ? "Restored Successfully !!" : "Restore Failed !!");

	} catch (ValidationException e) {
		System.out.println("Error: " + e.getMessage());
	}

}
}