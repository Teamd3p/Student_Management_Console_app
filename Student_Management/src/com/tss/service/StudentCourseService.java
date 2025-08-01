package com.tss.service;

import java.util.List;

import com.tss.dao.StudentCourseDao;
import com.tss.model.Course;
import com.tss.model.Fees;
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

	public List<Fees> getCourseByStudentId(int studentId) {
		return studentCourseDao.getCourseByStudentId(studentId);
		
	}

	public List<Course> getAllCourses(int id) {
		return studentCourseDao.getAllCourses(id);
	}
}
