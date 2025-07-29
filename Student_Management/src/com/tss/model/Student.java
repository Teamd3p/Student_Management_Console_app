package com.tss.model;

import java.time.LocalDateTime;

public class Student {
    private int studentId;  // still present for reading from DB
    private String studentName;
    private boolean isActive;
    private LocalDateTime admission;

    // Default constructor
    public Student() {
        this.admission = LocalDateTime.now();
        this.isActive = false;
    }

    // Constructor without studentId
    public Student(String studentName, Boolean isActive, LocalDateTime admission) {
        this.setStudentName(studentName);
        this.setActive(isActive);
        this.setAdmission(admission);
    }

    // Getter only for ID (no setter since it's auto-generated)
    public int getStudentId() {
        return studentId;
    }

    // Allow setting ID only internally (e.g., from DAO)
    public void setStudentId(int studentId) {
        if (studentId < 0) {
            throw new IllegalArgumentException("Student ID cannot be negative.");
        }
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        if (studentName == null || studentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be null or empty.");
        }
        if (studentName.length() > 100) {
            throw new IllegalArgumentException("Student name cannot exceed 100 characters.");
        }
        this.studentName = studentName.trim();
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean isActive) {
        this.isActive = (isActive != null) ? isActive : false;
    }

    public LocalDateTime getAdmission() {
        return admission;
    }

    public void setAdmission(LocalDateTime admission) {
        this.admission = (admission != null) ? admission : LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Student [ID=" + studentId + ", Name=" + studentName +
               ", Active=" + isActive + ", Admission=" + admission + "]";
    }
}
