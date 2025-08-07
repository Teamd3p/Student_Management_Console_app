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
    
    public void deleteStudentCourse(int student_id)
    {
    	studentCourseDao.deleteCourseOfStudent(student_id);
    }
    
    public boolean checkAssignmentOfCourse(int student_id)
    {
    	return studentCourseDao.checkStudentCourseAssignment(student_id);
    }
}

