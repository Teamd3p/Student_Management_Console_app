package com.tss.model;

public class Fees {
	private int feeId;
	private int courseId;
	private int studentId;
	private double amountPaid;
	private double amountPending;
	private String courseName;
	private String studentName;
	private String paymentType;

	public Fees() {

	}

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

	public Fees(int courseId2, int studentId2, double amountPaid, double courseFees) {
		this.courseId = courseId2;
		this.studentId = studentId2;
		this.amountPaid = amountPaid;
		this.amountPending = courseFees;

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
	
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public void setAmountPending(double amountPending) {
		this.amountPending = amountPending;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	@Override
	public String toString() {
		return "Fees [feeId=" + feeId + ", courseId=" + courseId + ", studentId=" + studentId + ", amountPaid="
				+ amountPaid + ", amountPending=" + amountPending + ", courseName=" + courseName + ", studentName="
				+ studentName + "]";
	}
	public void setFeeId(int feeId) {
		this.feeId = feeId;
	}



}
