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

	public boolean addCourse(Course course) {
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

	public Course searchCourseByName(String name) {
	    return courseDao.searchCourseByName(name);
	}

	public boolean updateCourse(Course course) {
	    return courseDao.updateCourse(course);
	}

	
	
}
