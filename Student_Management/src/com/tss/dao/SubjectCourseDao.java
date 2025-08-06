package com.tss.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tss.database.DBConnection;
import com.tss.model.Subject;
import com.tss.model.SubjectCourse;

public class SubjectCourseDao {

    private Connection connection;
    private PreparedStatement preparedStatement;

    public SubjectCourseDao() {
        this.connection = DBConnection.connect();
    }

    public boolean insertCourseSubject(SubjectCourse subjectCourse) {
        String checkSql = "SELECT 1 FROM CourseSubjects WHERE subject_id = ? AND course_id = ?";
        String insertSql = "INSERT INTO CourseSubjects (subject_id, course_id) VALUES (?, ?)";

        try {
            preparedStatement = connection.prepareStatement(checkSql);
            preparedStatement.setInt(1, subjectCourse.getSubjectId());
            preparedStatement.setInt(2, subjectCourse.getCourseId());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Error: This subject is already assigned to the course.");
                return false;
            }

            // If not exists, insert the new record
            preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setInt(1, subjectCourse.getSubjectId());
            preparedStatement.setInt(2, subjectCourse.getCourseId());

            int rows = preparedStatement.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Error inserting course-subject relation: " + e.getMessage());
        }

        return false;
    }

    
    public List<Subject> getSubjectsByCourseId(int courseId) {
        List<Subject> subjects = new ArrayList<>();

        try {
            String query = "SELECT * FROM Subjects inner join CourseSubjects on CourseSubjects.subject_id = Subjects.subject_id where CourseSubjects.course_id = ? ";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Subject subject = new Subject();
                subject.setSubjectId(rs.getInt("subject_id"));
                subject.setSubjectName(rs.getString("subject_name"));
                subject.setSubjectDescription(rs.getString("subject_description"));
                subjects.add(subject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subjects;
    }

}
