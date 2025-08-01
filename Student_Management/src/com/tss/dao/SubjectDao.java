package com.tss.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tss.database.DBConnection;
import com.tss.model.Subject;

public class SubjectDao {
	

    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;

    public SubjectDao() {
        this.connection = DBConnection.connect();
    }

    public List<Subject> readAllSubjects() {
        List<Subject> subjectList = new ArrayList<>();
        String query = "SELECT * FROM Subjects"; 

        try {
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                Subject subject = new Subject();
                subject.setSubjectId(result.getInt("subject_id"));
                subject.setSubjectName(result.getString("subject_name"));
                subject.setSubjectDescription(result.getString("subject_description"));
                subjectList.add(subject);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subjectList;
    }

    public Subject addSubject(Subject subject) {
        String query = "INSERT INTO Subjects (subject_name, subject_description) VALUES (?, ?)";

        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, subject.getSubjectName());
            preparedStatement.setString(2, subject.getSubjectDescription());

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    return new Subject(generatedId, subject.getSubjectName(), subject.getSubjectDescription());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateSubject(Subject subject) {
        String query = "UPDATE Subjects SET subject_name = ?, subject_description = ? WHERE subject_id = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, subject.getSubjectName());
            preparedStatement.setString(2, subject.getSubjectDescription());
            preparedStatement.setInt(3, subject.getSubjectId());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public boolean deleteSubjectById(int subjectId) {
        String query = "DELETE FROM Subjects WHERE subject_id = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, subjectId);

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public Subject getSubjectById(int subjectId) {
        String query = "SELECT * FROM Subjects WHERE subject_id = ?";
        Subject subject = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, subjectId);
            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                subject = new Subject();
                subject.setSubjectId(result.getInt("subject_id"));
                subject.setSubjectName(result.getString("subject_name"));
                subject.setSubjectDescription(result.getString("subject_description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subject;
    }

    public List<Subject> readSubjectsAssignedToTeachers(int teacherId) {
        List<Subject> subjectList = new ArrayList<>();
        String query = "SELECT * FROM Subjects " +
                       "INNER JOIN TeacherSubjects ON Subjects.subject_id = TeacherSubjects.subject_id " +
                       "WHERE TeacherSubjects.teacher_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, teacherId); 

            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                Subject subject = new Subject();
                subject.setSubjectId(result.getInt("subject_id"));
                subject.setSubjectName(result.getString("subject_name"));
                subject.setSubjectDescription(result.getString("subject_description"));
                subjectList.add(subject);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subjectList;
    }

    
    
    
}
