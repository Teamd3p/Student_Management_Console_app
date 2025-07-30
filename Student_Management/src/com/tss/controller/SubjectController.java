package com.tss.controller;

import java.util.List;

import com.tss.model.Subject;
import com.tss.service.SubjectService;

public class SubjectController {

	private SubjectService subjectService;

	public SubjectController() {
		super();
		this.subjectService = new SubjectService() ;
	}
	
	public void readAllSubjects() {
	    List<Subject> subjects = subjectService.readAllSubjects();

	    System.out.println("\n+--------------------------------------------------------------+");
	    System.out.println("|                        SUBJECT RECORDS                       |");
	    System.out.println("+--------------------------------------------------------------+");
	    System.out.printf("| %-10s | %-25s | %-20s |\n", "Subject ID", "Subject Name", "Description");
	    System.out.println("+--------------------------------------------------------------+");

	    for (Subject subject : subjects) {
	        System.out.printf("| %-10d | %-25s | %-20s |\n",
	                subject.getSubjectId(),
	                subject.getSubjectName(),
	                subject.getSubjectDescription());
	    }

	    System.out.println("+--------------------------------------------------------------+");
	}

	
	
	
}
