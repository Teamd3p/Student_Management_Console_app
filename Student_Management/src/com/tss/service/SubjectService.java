package com.tss.service;

import java.util.List;

import com.tss.dao.SubjectDao;
import com.tss.model.Subject;

public class SubjectService {

	private SubjectDao subjectDao;

	public SubjectService() {
		super();
		this.subjectDao = new SubjectDao();
	}
	
	 public List<Subject> readAllSubjects(){
		 
		 return subjectDao.readAllSubjects();
	 }
	
}
