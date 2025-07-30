package com.tss.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.tss.database.DBConnection;
import com.tss.model.SubjectCourse;

public class SubjectCourseDao {

    private Connection connection;
    private PreparedStatement preparedStatement;

    public SubjectCourseDao() {
        this.connection = DBConnection.connect();
    }

    public boolean insertCourseSubject(SubjectCourse subjectCourse) {
        String sql = "INSERT INTO CourseSubjects (subject_id, course_id) VALUES (?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, subjectCourse.getSubjectId());
            preparedStatement.setInt(2, subjectCourse.getCourseId());
            int rows = preparedStatement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting course-subject relation: " + e.getMessage());
        }
        return false;
    }
}
