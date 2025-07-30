package com.tss.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import com.tss.model.Teacher;
import com.tss.service.TeacherService;

public class TeacherController {
	Scanner scanner = new Scanner(System.in);
	private TeacherService teacherService = new TeacherService();
	SubjectController subjectController = new SubjectController();

	public void displayAllTeachers() {
		List<Teacher> teachers = teacherService.getAllTeachers();
		if (teachers.isEmpty()) {
			System.out.println("No Teacher Found.");
		} else {
			teachers.forEach(System.out::println);
		}
	}

	public void addTeacher() {
		System.out.print("Enter Teacher name: ");
		String name = scanner.nextLine();

		boolean isActive = true;

		System.out.print("Enter Joining Date (yyyy-MM-dd HH:mm) or press Enter for now: ");
		String joiningDate = scanner.nextLine().trim();

		LocalDateTime admission = joiningDate.isEmpty() ? LocalDateTime.now()
				: LocalDateTime.parse(joiningDate.replace(" ", "T"));

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedDate = admission.format(formatter);

		boolean success = teacherService.addTeacher(new Teacher(0, name, isActive, formattedDate));
		if (success) {
			System.out.println("Teacher added successfully!");
		} else {
			System.out.println("Failed to add Teacher.");
		}
	}

	public void getTeacherById() {
		System.out.print("Enter Teacher ID: ");
		int id = scanner.nextInt();
		Teacher teacher = teacherService.getByIdTeacher(id);
		if (teacher != null) {
			System.out.println(teacher);
		} else {
			System.out.println("Teacher not found.");
		}
	}

	public boolean deleteTeacher() {
		System.out.print("Enter Teacher ID to Delete: ");
		int deleteId = scanner.nextInt();
		boolean updated = teacherService.deleteTeacher(deleteId);
		if (updated) {
			System.out.println("Teacher delete successfully!");
		} else {
			System.out.println("Delete failed.");
		}
		return updated;
	}

	public boolean assignSubject() {
		System.out.print("Enter Teacher ID: ");
		int teacherId = scanner.nextInt();
		
		System.out.println("Subjects Tables");
		subjectController.readAllSubjects();
		
		System.out.print("Enter Subject ID: ");
		int subjectId = scanner.nextInt();
		boolean assigned = teacherService.assignSubject(teacherId, subjectId);
		if (assigned) {
			System.out.println("Subject Assign to Teacher successfully!");
		} else {
			System.out.println("Assignation failed.");
		}
		return assigned;

	}

	public boolean removeSubject() {
		System.out.print("Enter Teacher ID: ");
		int teacherId = scanner.nextInt();
		
		System.out.println("Subjects Tables");
		subjectController.readAllSubjects();
		
		System.out.print("Enter Subject ID: ");
		int subjectId = scanner.nextInt();
		boolean removed = teacherService.removeSubject(teacherId, subjectId);
		if (removed) {
			System.out.println("Subject removed From Teacher successfully!");
		} else {
			System.out.println("Remove Operation failed.");
		}
		return removed;
		
	}
}
