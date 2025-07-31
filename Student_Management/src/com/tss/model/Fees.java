package com.tss.model;

public class Fees {
	private int feeId;
	private int courseId;
	private int studentId;
	private double amountPaid;
	private double amountPending;
	private String courseName;
	private String studentName;

	public Fees(int feeId, int courseId, int studentId, double amountPaid, double amountPending, String courseName,
			String studentName) {
		this.feeId = feeId;
		this.courseId = courseId;
		this.studentId = studentId;
		this.amountPaid = amountPaid;
		this.amountPending = amountPending;
		this.courseName = courseName;

		this.studentName = studentName;
	}

	public Fees(int courseId, String courseName, double amountPaid) {
		this.courseId = courseId;
		this.courseName = courseName;
		this.amountPaid = amountPaid;

	}
	
	
	public int getFeeId() {
		return feeId;
	}

	public int getCourseId() {
		return courseId;
	}

	public int getStudentId() {
		return studentId;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public double getAmountPending() {
		return amountPending;
	}

	public String getCourseName() {
		return courseName;
	}

	public String getStudentName() {
		return studentName;
	}
}
