package com.tss.model;

public class Subject {

	private int subjectId;
	private String subjectName;
	private String subjectDescription;
		
	public Subject() {
	}

	public Subject(int subjectId, String subjectName, String subjectDescription) {
	    this.subjectId = subjectId;
	    this.subjectName = subjectName;
	    this.subjectDescription = subjectDescription;
	}

	public int getSubjectId() {
	    return subjectId;
	}

	public void setSubjectId(int subjectId) {
	    this.subjectId = subjectId;
	}

	public String getSubjectName() {
	    return subjectName;
	}

	public void setSubjectName(String subjectName) {
	    this.subjectName = subjectName;
	}

	public String getSubjectDescription() {
	    return subjectDescription;
	}

	public void setSubjectDescription(String subjectDescription) {
	    this.subjectDescription = subjectDescription;
	}

	@Override
	public String toString() {
	    return "Subjects {" +
	            "subjectId=" + subjectId +
	            ", subjectName='" + subjectName + '\'' +
	            ", subjectDescription='" + subjectDescription + '\'' +
	            '}';
	}
	
}
