package com.tss.service;

import java.util.List;

import com.tss.dao.StudentCourseDao;
import com.tss.model.Course;
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

	public List<Course> getAllCourses(int student_id) {
		return studentCourseDao.getAllCourses(student_id);
	}
}

