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
	            Profile profile = new Profile(
	                result.getInt("id"),
	                result.getString("phone_number"),
	                result.getString("email"),
	                result.getString("address"),
	                result.getInt("age"),
	                result.getString("user_type"),
	                result.getInt("user_id")
	            );
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
	        prepareStatement.setString(5, profile.getUserType()); // using getter
	        prepareStatement.setInt(6, profile.getUserId());

	        int rowsInserted = prepareStatement.executeUpdate();
	        return rowsInserted > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean existsProfileByNamePhoneAndType(String fullName, String phoneNumber, String userType) {
	    // Choose table & column names based on user type
	    String tableName;
	    String idColumn;
	    String nameColumn;

	    if ("student".equalsIgnoreCase(userType)) {
	        tableName = "Students";
	        idColumn = "student_id";
	        nameColumn = "student_name";
	    } else if ("teacher".equalsIgnoreCase(userType)) {
	        tableName = "Teachers";
	        idColumn = "teacher_id";
	        nameColumn = "teacher_name";
	    } else {
	        throw new IllegalArgumentException("Invalid userType: " + userType);
	    }

	    String sql = String.format("""
	        SELECT COUNT(*) 
	        FROM %s u
	        JOIN Profiles p ON u.%s = p.user_id
	        WHERE u.%s = ? 
	          AND p.phone_number = ? 
	          AND p.user_type = ?
	    """, tableName, idColumn, nameColumn);

	    try {
	        prepareStatement = connection.prepareStatement(sql);
	        prepareStatement.setString(1, fullName);
	        prepareStatement.setString(2, phoneNumber);
	        prepareStatement.setString(3, userType);
	        ResultSet rs = prepareStatement.executeQuery();
	        if (rs.next()) {
	            return rs.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	public boolean checkExistanceOfEmail(String email, String user_type) {
		String sql = "SELECT COUNT(*) FROM Profiles WHERE email = ? AND user_type=?";
		
		try {
	        prepareStatement = connection.prepareStatement(sql);
	        prepareStatement.setString(1, email);
	        prepareStatement.setString(2, user_type);
	        ResultSet rs = prepareStatement.executeQuery();
	        if (rs.next()) {
	            return rs.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}


}
