package com.tss.model;

import java.time.LocalDateTime;

public class StudentCourse {
    private int studentCourseId; 
    private int studentId;
    private int courseId;
    private LocalDateTime enrolledAt;

    // Constructors
    public StudentCourse() {
        this.enrolledAt = LocalDateTime.now(); 
    }

    public StudentCourse(int studentCourseId, int studentId, int courseId, LocalDateTime enrolledAt) {
        this.studentCourseId = studentCourseId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrolledAt = (enrolledAt != null) ? enrolledAt : LocalDateTime.now();
    }

    public StudentCourse(int studentId, int courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrolledAt = LocalDateTime.now();
    }

    public int getStudentCourseId() {
        return studentCourseId;
    }

    public void setStudentCourseId(int studentCourseId) {
        this.studentCourseId = studentCourseId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public LocalDateTime getEnrolledAt() {
        return enrolledAt;
    }

    public void setEnrolledAt(LocalDateTime enrolledAt) {
        this.enrolledAt = (enrolledAt != null) ? enrolledAt : LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "StudentCourse [ID=" + studentCourseId + ", Student ID=" + studentId + 
               ", Course ID=" + courseId + ", Enrolled At=" + enrolledAt + "]";
    }
}
