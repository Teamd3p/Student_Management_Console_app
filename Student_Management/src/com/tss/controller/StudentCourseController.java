package com.tss.controller;

import java.time.LocalDateTime;
import java.util.Scanner;

import com.tss.model.StudentCourse;
import com.tss.service.StudentCourseService;

public class StudentCourseController {
	
	private StudentCourseService subjectCourseService;
	private Scanner scanner = new Scanner(System.in);

	public StudentCourseController()
	{
		this.subjectCourseService = new StudentCourseService();
	}
	
	public void AssignCourseToStudent(StudentController controller,CourseController courseController) {
	    try {
	    	controller.readAllRecords();
	        System.out.print("Enter Student ID: ");
	        int studentId = Integer.parseInt(scanner.nextLine().trim());

	        courseController.readAllCourseRecords();
	        System.out.print("Enter Course ID: ");
	        int courseId = Integer.parseInt(scanner.nextLine().trim());

	        StudentCourse studentCourse = new StudentCourse();
	        studentCourse.setStudentId(studentId);
	        studentCourse.setCourseId(courseId);
	        studentCourse.setEnrolledAt(LocalDateTime.now());

	        StudentCourseService courseService = new StudentCourseService();
	        courseService.AssignCourseToStudent(studentCourse);

	    } catch (NumberFormatException e) {
	        System.out.println("Invalid input. Please enter numeric values for IDs.");
	    } catch (Exception e) {
	        System.out.println("An error occurred while assigning course.");
	        e.printStackTrace();
	    }
	}
}
