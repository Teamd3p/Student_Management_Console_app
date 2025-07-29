package com.tss.model;

public class Course {

	private int courseId;
	private String courseName;
	private double courseFees;
	private boolean isActive;

	// Constructors
	public Course() {
		this.isActive = true;
	}

	public Course(int courseId, String courseName, double courseFees, boolean isActive) {
		this.courseId = courseId;
		this.courseName = courseName;
		this.courseFees = courseFees;
		this.isActive = isActive;
	}

	public int getCourseId() {
		return courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		if (courseName == null || courseName.trim().isEmpty()) {
			throw new IllegalArgumentException("Course name cannot be null or empty");
		}
		this.courseName = courseName;
	}

	public double getCourseFees() {
		return courseFees;
	}

	public void setCourseFees(double courseFees) {
		if (courseFees < 0) {
			throw new IllegalArgumentException("Course fees cannot be negative");
		}
		this.courseFees = courseFees;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		this.isActive = active;
	}

	
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	@Override
	public String toString() {
		return "Course [ID=" + courseId + ", Name=" + courseName + ", Fees=" + courseFees + ", Active=" + isActive
				+ "]";
	}
}
