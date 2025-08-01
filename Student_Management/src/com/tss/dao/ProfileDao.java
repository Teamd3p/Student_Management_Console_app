package com.tss.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.tss.database.DBConnection;
import com.tss.model.Profile;

public class ProfileDao {

	private Connection connection = null;
	private Statement statement = null;
	private PreparedStatement prepareStatement = null;

	public ProfileDao() {
		this.connection = DBConnection.connect();
	}

	public List<Profile> readAllProfiles(String user_type) {
		List<Profile> profiles = new ArrayList<Profile>();
		String sql = "SELECT * FROM Profiles WHERE user_type = ?";

		try {
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setString(1, user_type);
			ResultSet result = prepareStatement.executeQuery();

			while (result.next()) {
				Profile profile = new Profile(result.getInt("id"), result.getString("phone_number"),
						result.getString("email"), result.getString("address"), result.getInt("age"),
						result.getString("user_type"), result.getInt("user_id"));
				profiles.add(profile);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return profiles;
	}

	public boolean insertStudent(Profile profile) {
		String sql = "INSERT INTO Profiles (phone_number, email, address, age, user_type, user_id) VALUES (?, ?, ?, ?, ?, ?)";

		try {
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setString(1, profile.getPhoneNumber());
			prepareStatement.setString(2, profile.getEmail());
			prepareStatement.setString(3, profile.getAddress());
			prepareStatement.setInt(4, profile.getAge());
			prepareStatement.setString(5, profile.getUserType());
			prepareStatement.setInt(6, profile.getUserId());

			int rowsInserted = prepareStatement.executeUpdate();
			return rowsInserted > 0;

		} catch (SQLException e) {
			String message = e.getMessage().toLowerCase();

			if (message.contains("duplicate") || message.contains("unique")) {
				System.out.println("Error: Duplicate entry detected (email or phone might already exist).");
			} else if (message.contains("foreign key")) {
				System.out.println("Error: Invalid user reference. User may not exist.");
			} else if (message.contains("null")) {
				System.out.println("Error: One of the required fields was empty or invalid.");
			} else {
				System.out.println("Database Error: " + e.getMessage());
			}

			return false;
		}
	}

	public boolean checkDuplicatePhone(String phone) {
		String query = "SELECT COUNT(*) FROM Profiles WHERE phone_number = ?";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setString(1, phone);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			System.out.println("Error checking duplicate phone: " + e.getMessage());
		}
		return false;
	}

	public boolean checkDuplicateEmail(String email) {
		String query = "SELECT COUNT(*) FROM Profiles WHERE email = ?";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			System.out.println("Error checking duplicate email: " + e.getMessage());
		}
		return false;
	}

	public List<Profile> readAllActiveProfiles(String user_type) {
		// TODO Auto-generated method stub
		return null;
	}

}
