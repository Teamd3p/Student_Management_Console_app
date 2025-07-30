package com.tss.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tss.database.DBConnection;
import com.tss.model.Course;

public class CourseDao {

	private Connection connection = null;
	private Statement statement = null;
	private PreparedStatement prepareStatement = null;

	public CourseDao() {
		this.connection = DBConnection.connect();
	}

	public List<Course> readAllCourses() {
		List<Course> courses = new ArrayList<>();
		try {
			statement = connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM Courses");

			while (result.next()) {
				Course course = new Course(result.getInt("course_id"),
						result.getString("course_name"),
						result.getDouble("course_fees"),
						result.getBoolean("is_active"));
				courses.add(course);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courses;
	}

	public boolean insertCourse(Course course) {
		String query = "INSERT INTO Courses (course_name, course_fees, is_active) VALUES (?, ?, ?)";
		try {
			prepareStatement = connection.prepareStatement(query);
			prepareStatement.setString(1, course.getCourseName());
			prepareStatement.setDouble(2, course.getCourseFees());
			prepareStatement.setBoolean(3, course.isActive());

			int rows = prepareStatement.executeUpdate();
			return rows > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
