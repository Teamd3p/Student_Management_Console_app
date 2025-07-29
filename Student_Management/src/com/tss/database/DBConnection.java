package com.tss.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;

public class DBConnection {
    public static Connection connect() {
        try {
            Dotenv dotenv = Dotenv.configure()
                                  .directory(System.getProperty("user.dir")) 
                                  .load();

            String url = dotenv.get("DB_URL");
            String user = dotenv.get("DB_USER");
            String password = dotenv.get("DB_PASS");

            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.err.println("❌ Failed to connect to the database.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("❌ Failed to load .env file.");
            e.printStackTrace();
        }
        return null;
    }
}