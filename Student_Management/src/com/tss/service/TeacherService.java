package com.tss.service;

import java.util.List;

import com.tss.dao.TeacherDao;
import com.tss.model.Teacher;

public class TeacherService {
	private TeacherDao dao = new TeacherDao();
	
	public List<Teacher> getAllTeachers() {
		return dao.getAllTeachers();
	}

}
