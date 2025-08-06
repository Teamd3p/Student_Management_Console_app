package com.tss.model;

import java.util.List;

public class Teacher {
	private int teacherId;
	private String teacherName;
	private boolean isActive;
	private String joiningDate;
	private List<String> subjects;

	public Teacher(int teacherId, String teacherName, List<String> subjects, boolean isActive, String joiningDate) {
		super();
		this.teacherId = teacherId;
		this.teacherName = teacherName;
		this.isActive = isActive;
		this.joiningDate = joiningDate;
		this.subjects = subjects;
	}

	public Teacher(int teacherId, String teacherName, boolean isActive, String joiningDate) {
		super();
		this.teacherId = teacherId;
		this.teacherName = teacherName;
		this.isActive = isActive;
		this.joiningDate = joiningDate;
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
	    StringBuilder builder = new StringBuilder();

	    // Top part of the box
	    builder.append("\n+--------------------------------+");
	    builder.append("\n|          TEACHER DETAILS       |");
	    builder.append("\n+--------------------------------+");
	    builder.append(String.format("\n| ID: %-27s|", teacherId));
	    builder.append(String.format("\n| Name: %-25s|", teacherName));
	    builder.append("\n+--------------------------------+");
	    builder.append("\n| Subjects:                      |");

	    // List subjects vertically
	    for (String subject : subjects) {
	        builder.append(String.format("\n|   - %-27s|", subject));
	    }

	    // Bottom border
	    builder.append("\n+--------------------------------+");

	    return builder.toString();
	}



}
