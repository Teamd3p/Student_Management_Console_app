package com.tss.service;

import java.util.List;

import com.tss.dao.TeacherDao;
import com.tss.model.Teacher;

public class TeacherService {
	private TeacherDao dao = new TeacherDao();

	public List<Teacher> getAllTeachers() {
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

}
