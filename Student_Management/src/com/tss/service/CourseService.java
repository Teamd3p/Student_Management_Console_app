package com.tss.service;

import java.util.List;

import com.tss.dao.CourseDao;
import com.tss.model.Course;

public class CourseService {
	private CourseDao courseDao;

	public CourseService() {
		this.courseDao = new CourseDao();
	}

	public List<Course> readAllCourses() {
		return courseDao.readAllCourses();
	}

	public Course addCourse(Course course) {
		return courseDao.insertCourse(course);
	}

	public Course searchCourse(int course_id) {
		return courseDao.searchCourse(course_id);		
	}

	public Course softDeleteCourse(int course_id) {
	    return courseDao.softDeleteCourse(course_id);		
	}

	public List<Course> readAllActiveCourses() {
		return courseDao.readAllActiveCourses();

	}
	
	public List<Course> readAlldeActiveCourses()
	{
		return courseDao.readAlldeActiveCourses();
	}

	public boolean restoreCourse(int courseId) {
		return courseDao.restoreCourse(courseId);
	}
	
	
}
