package com.tss.service;

import java.util.List;

import com.tss.dao.TeacherDao;
import com.tss.dto.TeacherWithProfileDTO;
import com.tss.model.Subject;
import com.tss.model.Teacher;

public class TeacherService {
	private TeacherDao dao = new TeacherDao();

	public List<TeacherWithProfileDTO> getAllTeachers() {
		return dao.getAllTeachers();
	}

	public boolean addTeacher(Teacher teacher) {
		return dao.addTeacher(teacher);
	}

	public Teacher getByIdTeacher(int id) {
		return dao.getByIdTeacher(id);
	}
	
	public boolean deleteTeacher(int id) {
		return dao.deleteTeacher(id);
	}
	
	public boolean assignSubject(int teacherId, int subjectId) {
		return dao.assignSubject(teacherId, subjectId);
	}

	public boolean removeSubject(int teacherId, int subjectId) {
		return dao.removeSubject(teacherId, subjectId);
	}

	public List<Subject> readTeacherSubjectById(int teacherId) {
		return dao.getSubjectsOfTeachers(teacherId);
	}

	public List<TeacherWithProfileDTO> getAllActiveTeachers() {
		return dao.getAllActiveTeachers();
	}

	public List<TeacherWithProfileDTO> getAlldeActiveTeachers() {
		return dao.getAlldeActiveTeachers();
	}

	public boolean restoreStudent(int teacherId) {
		return dao.restoreTeacher(teacherId);
	}

}
