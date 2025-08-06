package com.tss.model;

public class Teacher {
	private int teacherId;
	private String teacherName;
	private boolean isActive;
	private String joiningDate;

	public Teacher(int teacherId, String teacherName, boolean isActive, String joiningDate) {
		super();
		this.teacherId = teacherId;
		this.teacherName = teacherName;
		this.isActive = isActive;
		this.joiningDate = joiningDate;
	}

	public Teacher() {
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}

	@Override
	public String toString() {
		return String.format("| %-5d | %-20s | %-7s | %-12s |", teacherId, teacherName, isActive, joiningDate);
	}

}
