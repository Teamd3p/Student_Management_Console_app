package com.tss.controller;

import java.util.List;
import java.util.Scanner;

import com.tss.model.Course;
import com.tss.service.CourseService;

public class CourseController {
	private CourseService courseService;
	private Scanner scanner = new Scanner(System.in);

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
			System.out.printf("| %-10d | %-20s | %-10.2f | %-10s |\n", course.getCourseId(), course.getCourseName(),
					course.getCourseFees(), course.isActive() ? "Yes" : "No");
		}

		System.out.println("+-------------------------------------------------------------+");
	}

	public void addNewCourse() {
		System.out.print("Enter Course Name: ");
		String name = scanner.nextLine();

		System.out.print("Enter Course Fees: ");
		double fees = scanner.nextDouble();

		scanner.nextLine();

		Course course = new Course();
		course.setCourseName(name);
		course.setCourseFees(fees);

		boolean success = courseService.addCourse(course);
		if (success) {
			System.out.println("✅ Course added successfully.");
		} else {
			System.out.println("❌ Failed to add course.");
		}
	}

	public void searchCourse() {

		System.out.println("Enter course id to search the course");
		int course_id = scanner.nextInt();

		Course course = courseService.searchCourse(course_id);

		if (course != null) {
			System.out.println("\n+-------------------------------------------------------------+");
			System.out.println("|                        COURSE RECORDS                       |");
			System.out.println("+-------------------------------------------------------------+");
			System.out.printf("| %-10s | %-20s | %-10s | %-10s |\n", "Course ID", "Course Name", "Fees", "Active");
			System.out.println("+-------------------------------------------------------------+");

			System.out.printf("| %-10d | %-20s | %-10.2f | %-10s |\n", course.getCourseId(), course.getCourseName(),
					course.getCourseFees(), course.isActive() ? "Yes" : "No");

			System.out.println("+-------------------------------------------------------------+");
		} else {
			System.out.println("\nNo existing course with this ID: " + course_id);
		}
	}

	public boolean courseExistance(int course_id) {
		Course course = courseService.searchCourse(course_id);
		if (course != null)
			return true;
		return false;
	}

	public void softDeleteCourse() {

		radAllActiveCourse();

		System.out.println("Enter course id to delete the course");
		int course_id = scanner.nextInt();

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

		System.out.println("\n+-------------------------------------------------------------+");
		System.out.println("|                        COURSE RECORDS                       |");
		System.out.println("+-------------------------------------------------------------+");
		System.out.printf("| %-10s | %-20s | %-10s | %-10s |\n", "Course ID", "Course Name", "Fees", "Active");
		System.out.println("+-------------------------------------------------------------+");

		for (Course course : courses) {
			System.out.printf("| %-10d | %-20s | %-10.2f | %-10s |\n", course.getCourseId(), course.getCourseName(),
					course.getCourseFees(), course.isActive() ? "Yes" : "No");
		}

		System.out.println("+-------------------------------------------------------------+");
		return courses;
	}

}
