package com.tss.controller;

import java.util.List;
import java.util.Scanner;

import com.tss.model.Teacher;
import com.tss.service.TeacherService;

public class TeacherController {
	Scanner scanner  = new Scanner(System.in);
	private TeacherService  teacherService = new TeacherService();

	public void displayAllTeachers() {
		List<Teacher> teachers = teacherService.getAllTeachers();
		if(teachers.isEmpty()) {
			System.out.println("No Teacher Found.");
		}
		else {
			teachers.forEach(System.out::println);
		}
	}
}
