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

	public static Fees getFeeByStudentAndCourse(int studentId, int courseId) {
	    Fees fee = null;
	    String query = "SELECT f.fees_id, f.student_id, f.course_id, f.amount_paid, f.amount_pending, " +
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
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return fee;
	}

	public static boolean processFeePayment(int studentId, int courseId, double amountToPay, String paymentType) {
	    if (amountToPay <= 0) {
	        System.out.println("Entered amount cannot be zero or negative.");
	        return false;
	    }

	    Connection conn = null;
	    PreparedStatement selectStmt = null;
	    PreparedStatement updateStmt = null;
	    ResultSet rs = null;

	    try {
	        conn = DBConnection.connect();
	        conn.setAutoCommit(false); // Begin transaction

	        // Step 1: Fetch current paid and pending fees
	        String selectSQL = "SELECT amount_paid, amount_pending FROM Fees WHERE student_id = ? AND course_id = ?";
	        selectStmt = conn.prepareStatement(selectSQL);
	        selectStmt.setInt(1, studentId);
	        selectStmt.setInt(2, courseId);
	        rs = selectStmt.executeQuery();

	        if (!rs.next()) {
	            System.out.println("No fee record found for this student and course.");
	            conn.rollback();
	            return false;
	        }

	        double currentPaid = rs.getDouble("amount_paid");
	        double currentPending = rs.getDouble("amount_pending");

	        // Step 2: Validate amount
	        if (amountToPay > currentPending) {
	            System.out.println("Your entered amount is more than the required pending amount.");
	            conn.rollback();
	            return false;
	        }

	        // Step 3: Update values
	        double newPaid = currentPaid + amountToPay;
	        double newPending = currentPending - amountToPay;

	        String updateSQL = "UPDATE Fees SET amount_paid = ?, amount_pending = ?, payment_type = ? WHERE student_id = ? AND course_id = ?";
	        updateStmt = conn.prepareStatement(updateSQL);
	        updateStmt.setDouble(1, newPaid);
	        updateStmt.setDouble(2, newPending);
	        updateStmt.setString(3, paymentType);
	        updateStmt.setInt(4, studentId);
	        updateStmt.setInt(5, courseId);

	        int rowsAffected = updateStmt.executeUpdate();

	        if (rowsAffected == 1) {
	            conn.commit(); // All good
	            System.out.println("Payment successful. Paid ₹" + amountToPay);
	            return true;
	        } else {
	            conn.rollback(); // Something failed
	            System.out.println("Payment failed. Please try again.");
	            return false;
	        }

	    } catch (Exception e) {
	        try {
	            if (conn != null) conn.rollback(); // Ensure rollback on exception
	        } catch (SQLException rollbackEx) {
	            rollbackEx.printStackTrace();
	        }
	        e.printStackTrace();
	        return false;
	    } finally {
	        try { if (rs != null) rs.close(); } catch (SQLException e) {}
	        try { if (selectStmt != null) selectStmt.close(); } catch (SQLException e) {}
	        try { if (updateStmt != null) updateStmt.close(); } catch (SQLException e) {}
	        try { if (conn != null) conn.setAutoCommit(true); conn.close(); } catch (SQLException e) {}
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

}
