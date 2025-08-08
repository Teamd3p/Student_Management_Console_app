package com.tss.dto;


import com.tss.model.Profile;
import com.tss.model.Student;

public class StudentWithProfileDTO {
    private Student student;
    private Profile profile;

    public StudentWithProfileDTO(Student student, Profile profile) {
        this.student = student;
        this.profile = profile;
    }

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

    
}