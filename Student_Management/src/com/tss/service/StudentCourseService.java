package com.tss.service;

import com.tss.dao.StudentCourseDao;
import com.tss.model.StudentCourse;

public class StudentCourseService {
	private StudentCourseDao studentCourseDao;

    public StudentCourseService() {
        this.studentCourseDao = new StudentCourseDao();
    }
    
    public void AssignCourseToStudent(StudentCourse studentCourse)
    {
    	studentCourseDao.assignCourseToStudent(studentCourse);
    }
}
