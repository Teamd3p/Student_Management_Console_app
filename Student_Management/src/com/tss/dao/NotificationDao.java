package com.tss.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tss.database.DBConnection;

public class NotificationDao {
	public boolean insertNotificationPreference(int studentId, String preference) {
		String sql = "INSERT INTO Notification (student_id, preference) VALUES (?, ?)";
		try (Connection con = DBConnection.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, studentId);
			ps.setString(2, preference);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String getNotificationPreference(int studentId) {
		String sql = "SELECT preference FROM Notification WHERE student_id = ?";
		try (Connection con = DBConnection.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, studentId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("preference");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "None";
	}

	public boolean updateNotificationPreference(int studentId, String newPreference) {
		String sql = "UPDATE Notification SET preference = ? WHERE student_id = ?";
		try (Connection con = DBConnection.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, newPreference);
			ps.setInt(2, studentId);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
