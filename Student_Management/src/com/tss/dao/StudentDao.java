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
        String sql = "INSERT INTO Students (student_name, admission) VALUES (?, ?)";
        try {
            prepareStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prepareStatement.setString(1, student.getStudentName());
            prepareStatement.setTimestamp(2, Timestamp.valueOf(student.getAdmission()));

            int rowsInserted = prepareStatement.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    student.setStudentId(generatedId);
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    
    public Student readStudentById(int student_id) {
		Student student = null;

		try {
			prepareStatement = connection.prepareStatement("SELECT * FROM Students WHERE student_id = ?");
			prepareStatement.setInt(1, student_id);

			ResultSet result = prepareStatement.executeQuery();

			if (result.next()) {
			    student = new Student();
			    student.setStudentId(result.getInt("student_id"));
			    student.setStudentName(result.getString("student_name"));
			    student.setActive(result.getBoolean("is_active"));
			    student.setAdmission(result.getTimestamp("admission").toLocalDateTime());
			}


		} catch (SQLException e) {
			e.printStackTrace();
		}

		return student;
	}

    public Student deleteStudentByID(int student_id) {
        Student student = null;

        String updateSql = "UPDATE Students SET is_active = 0 WHERE student_id = ? AND is_active = 1";
        String selectSql = "SELECT * FROM Students WHERE student_id = ?";

        try {
            prepareStatement = connection.prepareStatement(updateSql);
            prepareStatement.setInt(1, student_id);
            int rowsAffected = prepareStatement.executeUpdate();

            if (rowsAffected > 0) {
                prepareStatement = connection.prepareStatement(selectSql);
                prepareStatement.setInt(1, student_id);
                ResultSet result = prepareStatement.executeQuery();

                if (result.next()) {
                    student = new Student();
                    student.setStudentId(result.getInt("student_id"));
    			    student.setStudentName(result.getString("student_name"));
    			    student.setActive(result.getBoolean("is_active"));
    			    student.setAdmission(result.getTimestamp("admission").toLocalDateTime());
                }
                result.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return student;
    }

	public Object payStudentFees(int id) {
		
		return null;
	}
}
