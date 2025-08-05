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
				Fees fee = new Fees(result.getInt("course_id"), result.getString("course_name"),
						result.getDouble("course_fees"));

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

	public static void deleteStudent(int id) {
		String sql = "DELETE FROM Fees WHERE student_id = ?";

		try (Connection connection = DBConnection.connect();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setInt(1, id);
			if (preparedStatement.executeUpdate() > 0) {
				System.out.println("Deleted Successfully !!");
			} else {
				System.out.println("Not Found !!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void insertNewRecord(Fees fee) {
		String sql = "INSERT INTO Fees(course_id, student_id, amount_paid, amount_pending) VALUES(?,?,?,?)";

		try (Connection connection = DBConnection.connect();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setInt(1, fee.getCourseId());
			preparedStatement.setInt(2, fee.getStudentId());
			preparedStatement.setDouble(3, fee.getAmountPaid());
			preparedStatement.setDouble(4, fee.getAmountPending());

			int updated = preparedStatement.executeUpdate();

			if (updated > 0) {
				System.out.println("Inserted !!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Fees getFeeByStudentAndCourse(int studentId, int courseId) {
	    Fees fee = null;
	    String query = "SELECT f.fees_id, f.student_id, f.course_id, f.amount_paid, f.amount_pending,f.payment_type, " +
	                   "c.course_name " +
	                   "FROM Fees f " +
	                   "JOIN Courses c ON f.course_id = c.course_id " +
	                   "WHERE f.student_id = ? AND f.course_id = ?";

	    try (Connection conn = DBConnection.connect();
	         PreparedStatement ps = conn.prepareStatement(query)) {

	        ps.setInt(1, studentId);
	        ps.setInt(2, courseId);

	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                fee = new Fees();
	                fee.setFeeId(rs.getInt("fees_id"));
	                fee.setStudentId(rs.getInt("student_id"));
	                fee.setCourseId(rs.getInt("course_id"));
	                fee.setAmountPaid(rs.getDouble("amount_paid"));
	                fee.setAmountPending(rs.getDouble("amount_pending"));
	                fee.setCourseName(rs.getString("course_name"));
	                fee.setPaymentType(rs.getString("payment_type"));
	                
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return fee;
	}

	public static boolean processFeePayment(int studentId, int courseId, double amountToPay, String paymentType) {
	    String selectQuery = "SELECT * FROM Fees WHERE student_id = ? AND course_id = ?";
	    String updateQuery = "UPDATE Fees SET amount_paid = ?, amount_pending = ?, payment_type = ? WHERE student_id = ? AND course_id = ?";
	    String insertQuery = "INSERT INTO Fees (student_id, course_id, amount_paid, amount_pending, payment_type) VALUES (?, ?, ?, ?, ?)";

	    try (Connection conn = DBConnection.connect();
	         PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {

	        selectStmt.setInt(1, studentId);
	        selectStmt.setInt(2, courseId);
	        ResultSet rs = selectStmt.executeQuery();

	        if (rs.next()) {
	            // Fee record exists, update it
	            double currentPaid = rs.getDouble("amount_paid");
	            double currentPending = rs.getDouble("amount_pending");
	            double newPaid = currentPaid + amountToPay;
	            double newPending = currentPending - amountToPay;

	            if (newPending < 0) {
	                System.out.println("Payment exceeds pending amount. Aborting.");
	                return false;
	            }

	            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
	                updateStmt.setDouble(1, newPaid);
	                updateStmt.setDouble(2, newPending);
	                updateStmt.setString(3, paymentType);
	                updateStmt.setInt(4, studentId);
	                updateStmt.setInt(5, courseId);
	                int rowsUpdated = updateStmt.executeUpdate();
	                return rowsUpdated > 0;
	            }

	        } else {
	            // No record exists; get course fee to calculate pending
	            double courseFee = 0.0;
	            try (PreparedStatement courseStmt = conn.prepareStatement("SELECT course_fees FROM Courses WHERE course_id = ?")) {
	                courseStmt.setInt(1, courseId);
	                ResultSet courseRs = courseStmt.executeQuery();
	                if (courseRs.next()) {
	                    courseFee = courseRs.getDouble("course_fees");
	                } else {
	                    System.out.println("Invalid course ID.");
	                    return false;
	                }
	            }

	            double pending = courseFee - amountToPay;
	            if (pending < 0) {
	                System.out.println("Payment exceeds course fee. Aborting.");
	                return false;
	            }

	            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
	                insertStmt.setInt(1, studentId);
	                insertStmt.setInt(2, courseId);
	                insertStmt.setDouble(3, amountToPay);
	                insertStmt.setDouble(4, pending);
	                insertStmt.setString(5, paymentType);
	                int rowsInserted = insertStmt.executeUpdate();
	                return rowsInserted > 0;
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
	}
}
