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
                Course course = new Course(
                    result.getInt("course_id"),
                    result.getString("course_name"),
                    result.getDouble("course_fees"),
                    result.getBoolean("is_active")
                );
                courses.add(course);
            }

            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public boolean insertCourse(Course course) {
        String query = "INSERT INTO Courses (course_name, course_fees) VALUES (?, ?)";
        try {
            prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, course.getCourseName());
            prepareStatement.setDouble(2, course.getCourseFees());

            int rows = prepareStatement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Course searchCourse(int course_id) {
        Course course = null;
        String sql = "SELECT * FROM Courses WHERE course_id = ?";

        try {
            prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setInt(1, course_id);
            ResultSet result = prepareStatement.executeQuery();

            if (result.next()) {
                course = new Course(
                    result.getInt("course_id"),
                    result.getString("course_name"),
                    result.getDouble("course_fees"),
                    result.getBoolean("is_active")
                );
            }
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return course;
    }

    public Course softDeleteCourse(int course_id) {
        Course course = null;

        String updateSql = "UPDATE Courses SET is_active = 0 WHERE course_id = ? AND is_active = 1";
        String selectSql = "SELECT * FROM Courses WHERE course_id = ?";

        try {
            prepareStatement = connection.prepareStatement(updateSql);
            prepareStatement.setInt(1, course_id);
            int rowsAffected = prepareStatement.executeUpdate();

            if (rowsAffected > 0) {
                prepareStatement = connection.prepareStatement(selectSql);
                prepareStatement.setInt(1, course_id);
                ResultSet result = prepareStatement.executeQuery();

                if (result.next()) {
                    course = new Course(
                        result.getInt("course_id"),
                        result.getString("course_name"),
                        result.getDouble("course_fees"),
                        result.getBoolean("is_active")
                    );
                }
                result.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return course;
    }

    public List<Course> readAllActiveCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM Courses WHERE is_active = ?";

        try {
            prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setBoolean(1, true);
            ResultSet result = prepareStatement.executeQuery();

            while (result.next()) {
                Course course = new Course(
                    result.getInt("course_id"),
                    result.getString("course_name"),
                    result.getDouble("course_fees"),
                    result.getBoolean("is_active")
                );
                courses.add(course);
            }

            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }
    
    public Course searchCourseByName(String name) {
        String sql = "SELECT * FROM Courses WHERE course_name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Course course = new Course();
                course.setCourseId(rs.getInt("course_id"));
                course.setCourseName(rs.getString("course_name"));
                course.setCourseFees(rs.getDouble("course_fees"));
                course.setActive(rs.getBoolean("is_active"));
                return course;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateCourse(Course course) {
        String sql = "UPDATE Courses SET course_fees = ?, is_active = ? WHERE course_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, course.getCourseFees());
            ps.setBoolean(2, course.isActive());
            ps.setInt(3, course.getCourseId());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
