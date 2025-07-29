package com.tss.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.tss.database.DBConnection;
import com.tss.model.Student;

public class StudentDao {
    private Connection connection = null;
    private Statement statement = null;
	private PreparedStatement prepareStatement = null;

    public StudentDao() {
        this.connection = DBConnection.connect();
    }

    public List<Student> readAllStudents() {
        List<Student> students = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM Students");

            while (result.next()) {
                Student student = new Student();
                student.setStudentId(result.getInt("student_id"));
                student.setStudentName(result.getString("student_name"));
                student.setActive(result.getBoolean("is_active"));
                Timestamp ts = result.getTimestamp("admission");
                if (ts != null) {
                    student.setAdmission(ts.toLocalDateTime());
                }

                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public boolean insertStudent(Student student) {
        String sql = "INSERT INTO Students (student_name, is_active, admission) VALUES (?, ?, ?)";

        try {
            prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, student.getStudentName());
            prepareStatement.setBoolean(2, student.isActive());
            prepareStatement.setTimestamp(3, Timestamp.valueOf(student.getAdmission()));

            int rowsInserted = prepareStatement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
