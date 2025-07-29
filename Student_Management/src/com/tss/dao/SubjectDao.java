package com.tss.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.Statement;
import com.tss.database.DBConnection;
import com.tss.model.Subject;

public class SubjectDao {
	

    private Connection connection = null;
    private Statement statement = null;

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

}
