package com.tss.model;

public class Dashboard {

	private int srNo;
    private int studentId;
    private String studentName;
    private String courseName;
    private double paidFee;
    private double pendingFee;
    private double totalFee;
    private String subjects;
    private String teachers;
    
    
	public Dashboard(int srNo, int studentId, String studentName, String courseName, double paidFee, double pendingFee,
			double totalFee, String subjects, String teachers) {
		super();
		this.srNo = srNo;
		this.studentId = studentId;
		this.studentName = studentName;
		this.courseName = courseName;
		this.paidFee = paidFee;
		this.pendingFee = pendingFee;
		this.totalFee = totalFee;
		this.subjects = subjects;
		this.teachers = teachers;
	}


	public int getSrNo() {
		return srNo;
	}


	public int getStudentId() {
		return studentId;
	}


	public String getStudentName() {
		return studentName;
	}


	public String getCourseName() {
		return courseName;
	}


	public double getPaidFee() {
		return paidFee;
	}


	public double getPendingFee() {
		return pendingFee;
	}


	public double getTotalFee() {
		return totalFee;
	}


	public String getSubjects() {
		return subjects;
	}


	public String getTeachers() {
		return teachers;
	}
   
	
    
	
}
