package com.tss.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.tss.database.DBConnection;
import com.tss.model.Fees;

public class FeesDao {

	public double getTotalPaidFees() throws SQLException {
		String sql = "SELECT SUM(amount_paid) FROM Fees";
		try (Connection connection = DBConnection.connect();
				Statement statement = connection.createStatement();
				ResultSet result = statement.executeQuery(sql)) {
			return result.next() ? result.getDouble(1) : 0;
		}
	}

	public double getTotalPendingFees() throws SQLException {
		String sql = "SELECT SUM(amount_pending) FROM Fees";
		try (Connection connection = DBConnection.connect();
				Statement statement = connection.createStatement();
				ResultSet result = statement.executeQuery(sql)) {
			return result.next() ? result.getDouble(1) : 0;
		}
	}

	public List<Fees> getFeesByStudent(int studentId) throws SQLException {
		List<Fees> list = new ArrayList<>();

		String sql = "SELECT f.fees_id, f.course_id, f.student_id, f.amount_paid, f.amount_pending, "
				+ "c.course_name, s.student_name " + "FROM Fees f " + "JOIN Students s ON f.student_id = s.student_id "
				+ "JOIN Courses c ON f.course_id = c.course_id " + "WHERE f.student_id = ?";

		try (Connection connection = DBConnection.connect();
				PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setInt(1, studentId);
			ResultSet result = stmt.executeQuery();

			while (result.next()) {
				Fees fee = new Fees(result.getInt("fees_id"), result.getInt("course_id"), result.getInt("student_id"),
						result.getDouble("amount_paid"), result.getDouble("amount_pending"),
						result.getString("course_name"), result.getString("student_name"));
				list.add(fee);
			}
		}

		return list;
	}

	public static List<Fees> getCourseFeesSummary(int courseId) throws SQLException {
		List<Fees> list = new ArrayList<>();

		String sql = "SELECT c.course_id, c.course_name, c.course_fees " + "FROM Courses c " + "WHERE c.course_id = ?";

		try (Connection connection = DBConnection.connect();
				PreparedStatement statament = connection.prepareStatement(sql)) {

			statament.setInt(1, courseId);
			ResultSet result = statament.executeQuery();

			while (result.next()) {
				Fees fee = new Fees(result.getInt("course_id"), result.getString("course_name"),result.getDouble("course_fees"));

				
		
				list.add(fee);
			}
		}

		return list;
	}

	public List<Fees> getFeesByCourse(int courseId) throws SQLException {
		List<Fees> list = new ArrayList<>();

		String sql = "SELECT f.fees_id, f.course_id, f.student_id, f.amount_paid, f.amount_pending, "
				+ "c.course_name, s.student_name " + "FROM Fees f " + "JOIN Students s ON f.student_id = s.student_id "
				+ "JOIN Courses c ON f.course_id = c.course_id " + "WHERE f.course_id = ? AND c.is_active = 1";

		try (Connection connection = DBConnection.connect();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setInt(1, courseId);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				Fees fee = new Fees(result.getInt("fees_id"), result.getInt("course_id"), result.getInt("student_id"),
						result.getDouble("amount_paid"), result.getDouble("amount_pending"),
						result.getString("course_name"), result.getString("student_name"));
				list.add(fee);
			}
		}
		return list;
	}

	public boolean updateCourseFees(int courseId, double newAmount) throws SQLException {
		String sql = "UPDATE Courses SET course_fees = ? WHERE course_id = ?";
		try (Connection connection = DBConnection.connect();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setDouble(1, newAmount);
			preparedStatement.setInt(2, courseId);
			return preparedStatement.executeUpdate() > 0;
		}
	}

	public double getTotalEarning() throws SQLException {
		String sql = "SELECT SUM(amount_paid + amount_pending) FROM Fees";
		try (Connection connection = DBConnection.connect();
				Statement statement = connection.createStatement();
				ResultSet result = statement.executeQuery(sql)) {
			return result.next() ? result.getDouble(1) : 0;
		}
	}
}
