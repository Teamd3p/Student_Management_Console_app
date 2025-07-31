package com.tss.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.tss.database.DBConnection;
import com.tss.model.Course;
import com.tss.model.StudentCourse;

public class StudentCourseDao {
	private Connection connection = null;
	private PreparedStatement prepareStatement = null;

	public StudentCourseDao() {
		this.connection = DBConnection.connect();
	}

	public void assignCourseToStudent(StudentCourse studentCourse) {
		String sql = "INSERT INTO StudentCourse (student_id, course_id, enrolled_at) VALUES (?, ?, ?)";

		try {
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setInt(1, studentCourse.getStudentId());
			prepareStatement.setInt(2, studentCourse.getCourseId());

			LocalDateTime enrolledAt = studentCourse.getEnrolledAt() != null ? studentCourse.getEnrolledAt()
					: LocalDateTime.now();

			prepareStatement.setTimestamp(3, Timestamp.valueOf(enrolledAt));

			int rowsAffected = prepareStatement.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Course assigned to student successfully.");
			} else {
				System.out.println("Failed to assign course.");
			}

		} catch (SQLException e) {
			System.out.println("Error while assigning course:");
			e.printStackTrace();
		}
	}

	public boolean checkStudentCourseAssignment(int student_id) {
		String sql = "SELECT * FROM StudentCourse WHERE student_id = ?";

		try {
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setInt(1, student_id);
			ResultSet result = prepareStatement.executeQuery();
			if (result != null) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}

	public void deleteCourseOfStudent(int student_id) {
		String sql1 = "DELETE FROM StudentCourse WHERE student_id = ?";

		try {

			if (checkStudentCourseAssignment(student_id)) {
				prepareStatement = connection.prepareStatement(sql1);
				prepareStatement.setInt(1, student_id);
				int updated = prepareStatement.executeUpdate();
				if (updated > 0) {
					System.out.println("Courses Of Students Is Deleted !!");
				}
			} else {
				System.out.println("No Course Assigned To Student !!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Course> getAllCourses(int student_id) {
	    List<Course> courses = new ArrayList<>();
	    String sql = "SELECT c.course_id, c.course_name, c.course_fees, c.is_active "
	               + "FROM Courses c "
	               + "JOIN StudentCourse sc ON c.course_id = sc.course_id "
	               + "WHERE sc.student_id = ?";

	    try {
	        if (checkStudentCourseAssignment(student_id)) {
	            prepareStatement = connection.prepareStatement(sql);
	            prepareStatement.setInt(1, student_id);

	            ResultSet result = prepareStatement.executeQuery();
	            while (result.next()) {
	                Course course = new Course();
	                course.setCourseId(result.getInt("course_id"));
	                course.setCourseName(result.getString("course_name"));
	                course.setCourseFees(result.getDouble("course_fees"));
	                course.setActive(result.getBoolean("is_active"));

	                courses.add(course);
	            }
	            result.close();
	        }
	    } catch (SQLException e) {
	        System.out.println("Error fetching courses: " + e.getMessage());
	    }

	    return courses;
	}

}
