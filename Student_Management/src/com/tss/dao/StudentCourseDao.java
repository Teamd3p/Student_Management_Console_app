package com.tss.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.tss.database.DBConnection;
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
}
