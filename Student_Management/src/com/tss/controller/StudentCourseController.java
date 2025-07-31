package com.tss.controller;

import java.time.LocalDateTime;
import java.util.Scanner;

import com.tss.model.StudentCourse;
import com.tss.service.StudentCourseService;

public class StudentCourseController {

	private StudentCourseService studentCourseService;
	private Scanner scanner = new Scanner(System.in);

	public StudentCourseController() {
		this.studentCourseService = new StudentCourseService();
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

					studentCourseService = new StudentCourseService();
					studentCourseService.AssignCourseToStudent(studentCourse);
					return;
				}
				System.out.println("Course With id " + courseId + " doesn't exists Or Not Active!!");
				return;
			}
			System.out.println("Student With id " + studentId + " doesn't exists Or Not Active!!");
		} catch (NumberFormatException e) {
			System.out.println("Invalid input. Please enter numeric values for IDs.");
		} catch (Exception e) {
			System.out.println("An error occurred while assigning course.");
			e.printStackTrace();
		}
	}

	public void deleteCourseFromStudent(int student_id) {
		studentCourseService.deleteStudentCourse(student_id);
	}

}
