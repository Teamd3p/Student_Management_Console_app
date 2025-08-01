package com.tss.controller;

import java.util.List;
import java.util.Scanner;

import com.tss.model.Course;
import com.tss.model.Subject;
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
        List<Course> activeCourses = courseController.radAllActiveCourse();
        
        System.out.print("Enter Course ID: ");
        int courseId = scanner.nextInt();
        scanner.nextLine(); 

        boolean courseExists = false;
        for (Course course : activeCourses) {
            if (course.getCourseId() == courseId) {
                courseExists = true;
                break;
            }
        }

        if (!courseExists) {
            System.out.println("Invalid Course ID. Operation cancelled.");
            return;
        }

        subjectController.readAllSubjects();

        System.out.print("Enter number of subjects to add: ");
        int count = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < count; i++) {
            System.out.print("Enter Subject ID " + (i + 1) + ": ");
            int subjectId = scanner.nextInt();
            scanner.nextLine();

            boolean success = subjectCourseService.addSubjectToCourse(courseId, subjectId);
            if (success) {
                System.out.println("✅ Subject " + subjectId + " added to Course " + courseId);
            } else {
                System.out.println("❌ Failed to add Subject " + subjectId);
            }
        }

        System.out.println("✔ Finished adding subjects to course.");
    }


    public void viewSubjectsOfCourse() {
        System.out.println(">> Available Courses:");
        courseController.readAllCourseRecords(); // show all courses

        System.out.print("Enter Course ID to view its subjects: ");
        int courseId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        List<Subject> subjects = subjectCourseService.viewSubjectsOfCourse(courseId); // call service

        if (subjects.isEmpty()) {
            System.out.println("No subjects found for this course.");
            return;
        }

        System.out.println("\n+--------------------------------------------------------------+");
        System.out.println("|                  SUBJECTS UNDER THIS COURSE                  |");
        System.out.println("+--------------------------------------------------------------+");
        System.out.printf("| %-10s | %-25s | %-20s |\n", "Subject ID", "Subject Name", "Description");
        System.out.println("+--------------------------------------------------------------+");

        for (Subject subject : subjects) {
            System.out.printf("| %-10d | %-25s | %-20s |\n",
                    subject.getSubjectId(),
                    subject.getSubjectName(),
                    subject.getSubjectDescription());
        }

        System.out.println("+--------------------------------------------------------------+");
    }


}
