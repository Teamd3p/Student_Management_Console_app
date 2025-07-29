package com.tss.controller;

import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;
import com.tss.model.Student;
import com.tss.service.StudentService;

public class StudentController {

    private StudentService studentService;
    private Scanner scanner = new Scanner(System.in);

    public StudentController() {
        this.studentService = new StudentService();
    }

    public void readAllRecords() {
        List<Student> students = studentService.readAllStudent();

        System.out.println("\n+----------------------------------------------------------------------------+");
        System.out.println("|                              STUDENT RECORDS                               |");
        System.out.println("+----------------------------------------------------------------------------+");
        System.out.printf("| %-10s | %-20s | %-10s | %-25s |\n", 
                          "Student ID", "Name", "Active", "Admission");
        System.out.println("+----------------------------------------------------------------------------+");

        for (Student student : students) {
            System.out.printf("| %-10d | %-20s | %-10s | %-25s |\n", 
                              student.getStudentId(),
                              student.getStudentName(),
                              student.isActive() ? "Yes" : "No",
                              student.getAdmission());
        }

        System.out.println("+----------------------------------------------------------------------------+");
    }

    public void insertStudent() {
        try {
            System.out.print("\nEnter Student Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Is Student Active? (true/false): ");
            String activeInput = scanner.nextLine().trim();
            Boolean isActive = Boolean.parseBoolean(activeInput);

            // Optional: Ask for admission date
            System.out.print("Enter Admission Date (yyyy-MM-dd HH:mm) or press Enter for now: ");
            String dateInput = scanner.nextLine().trim();
            LocalDateTime admission = dateInput.isEmpty()
                    ? LocalDateTime.now()
                    : LocalDateTime.parse(dateInput.replace(" ", "T"));

            Student student = new Student(name, isActive, admission);
            boolean success = studentService.insertStudent(student);

            if (success) {
                System.out.println("Student added successfully.");
                readAllRecords();
            } else {
                System.out.println("Failed to add student.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}

