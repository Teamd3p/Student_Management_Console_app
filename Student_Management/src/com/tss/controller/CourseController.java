package com.tss.controller;

import java.util.InputMismatchException;
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
		String name;
		double fees;

		while (true) {
			System.out.print("Enter Course Name: ");
			name = scanner.nextLine().trim();

			if (!name.isEmpty() && name.matches(".*[a-zA-Z]+.*")) {
				break;
			} else {
				System.out.println("Invalid course name. It must contain at least one alphabet character.");
			}
		}

		while (true) {
			System.out.print("Enter Course Fees: ");
			try {
				fees = scanner.nextDouble();
				scanner.nextLine();

				if (fees >= 0) {
					break;
				} else {
					System.out.println("Course fees cannot be negative. Try again.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Please enter a valid numeric value for fees.");
				scanner.nextLine();
			}
		}

		Course existingCourse = courseService.searchCourseByName(name);

		if (existingCourse != null) {
			if (existingCourse.isActive()) {
				System.out.println("Course already exists and is active.");
				return;
			} else {
				existingCourse.setCourseFees(fees);
				existingCourse.setActive(true);

				boolean updated = courseService.updateCourse(existingCourse);
				if (updated) {
					System.out.println("Course reactivated and updated successfully.");
				} else {
					System.out.println("Failed to reactivate the course.");
				}
				return;
			}
		}

		// Create and add a new course
		Course newCourse = new Course();
		newCourse.setCourseName(name);
		newCourse.setCourseFees(fees);
		newCourse.setActive(true); // Default true for new course

		boolean success = courseService.addCourse(newCourse);
		if (success) {
			System.out.println("✅ Course added successfully.");
		} else {
			System.out.println("❌ Failed to add the course.");
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
		{
		if(course.isActive())
			return true;
		}
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
