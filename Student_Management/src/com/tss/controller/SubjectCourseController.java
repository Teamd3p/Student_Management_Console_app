package com.tss.controller;

import java.util.Scanner;
import com.tss.service.SubjectCourseService;

public class SubjectCourseController {

    private Scanner scanner = new Scanner(System.in);
    private SubjectCourseService subjectCourseService;
    private CourseController courseController;
    private SubjectController subjectController;

    public SubjectCourseController() {
        this.subjectCourseService = new SubjectCourseService();
        this.courseController = new CourseController();
        this.subjectController = new SubjectController();
    }

    public void addSubjectsToCourse() {
        System.out.println(">> Available Courses:");
        courseController.readAllCourseRecords(); // Print all courses

        System.out.print("Enter Course ID: ");
        int courseId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        subjectController.readAllSubjects();
        System.out.print("Enter number of subjects to add: ");
        int count = scanner.nextInt();
        scanner.nextLine(); // consume newline

        for (int i = 0; i < count; i++) {
            System.out.print("Enter Subject ID " + (i + 1) + ": ");
            int subjectId = scanner.nextInt();
            scanner.nextLine(); // consume newline

            boolean success = subjectCourseService.addSubjectToCourse(courseId, subjectId);
            if (success) {
                System.out.println("✅ Subject " + subjectId + " added to Course " + courseId);
            } else {
                System.out.println("❌ Failed to add Subject " + subjectId);
            }
        }

        System.out.println("➡ Finished adding subjects to course.");
    }
}
