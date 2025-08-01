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
	
	 public Subject addSubject(Subject subject) {
		    return subjectDao.addSubject(subject);
		}
	 
	 public boolean updateSubject(Subject subject) {
	        return subjectDao.updateSubject(subject);
	    }
	 
	 public boolean deleteSubjectById(int subjectId) {
	        return subjectDao.deleteSubjectById(subjectId);
	    }
	 
	 public Subject getSubjectById(int subjectId) {
		    return subjectDao.getSubjectById(subjectId);
		}

	 public List<Subject> readSubjectsAssignedToTeachers(int teacherId) {
		 return subjectDao.readSubjectsAssignedToTeachers(teacherId);
	 }
}
