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
import com.tss.model.Fees;
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
//        	PreparedStatement checkAssign = connection.prepareStatement(checkAssignment);
//	        checkAssign.setInt(1, studentCourse.getCourseId());
//	        checkAssign.setInt(2, studentCourse.getStudentId());
//	        ResultSet assignRs = checkAssign.executeQuery();
//	        if (assignRs.next()) {
//	            System.out.println("Teacher already has this subject assigned.");
//	            return false;
//	        }
            prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setInt(1, studentCourse.getStudentId());
            prepareStatement.setInt(2, studentCourse.getCourseId());
            
            LocalDateTime enrolledAt = studentCourse.getEnrolledAt() != null
                ? studentCourse.getEnrolledAt()
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

    public List<Fees> getCourseByStudentId(int studentId) {
        List<Fees> fees = new ArrayList<>();
        String sql = "SELECT c.course_id, c.course_name, c.course_fees, " +
                     "IFNULL(f.amount_paid, 0) AS amount_paid, " +
                     "IFNULL(f.amount_pending, c.course_fees) AS amount_pending, " +
                     "IFNULL(f.payment_type, 'Not Paid') AS payment_type " +
                     "FROM StudentCourse sc " +
                     "JOIN Courses c ON sc.course_id = c.course_id " +
                     "LEFT JOIN Fees f ON f.student_id = sc.student_id AND f.course_id = sc.course_id " +
                     "WHERE sc.student_id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	Fees fee = new Fees();
            	fee.setCourseId(rs.getInt("course_id"));
            	fee.setCourseName(rs.getString("course_name"));
            	fee.setAmountPaid(rs.getDouble("amount_paid"));
            	fee.setAmountPending(rs.getDouble("amount_pending"));
            	fees.add(fee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fees;
    }

	public List<Course> getAllCourses(int id) {
		List<Course> courses = new ArrayList<>();
	    String sql = "SELECT c.course_id, c.course_name, c.course_fees, c.is_active "
	               + "FROM Courses c "
	               + "JOIN StudentCourse sc ON c.course_id = sc.course_id "
	               + "WHERE sc.student_id = ?";

	    try {
	        if (checkStudentCourseAssignment(id)) {
	            prepareStatement = connection.prepareStatement(sql);
	            prepareStatement.setInt(1, id);

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

	    return courses;	}

	private boolean checkStudentCourseAssignment(int id) {
		String sql = "SELECT * FROM StudentCourse WHERE student_id = ?";

		try {
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setInt(1, id);
			ResultSet result = prepareStatement.executeQuery();
			if (result != null) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

}
