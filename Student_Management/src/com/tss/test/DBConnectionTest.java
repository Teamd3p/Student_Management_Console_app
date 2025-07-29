package com.tss.test;

import java.sql.Connection;

import com.tss.database.DBConnection;

public class DBConnectionTest {

	public static void main(String[] args) {
		Connection connection = DBConnection.connect();

		if (connection != null) {
			System.out.println("✅ Database connection successful!");
		} else {
			System.out.println("❌ Failed to connect to the database.");
		}
	}
}
