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
	
	
}
