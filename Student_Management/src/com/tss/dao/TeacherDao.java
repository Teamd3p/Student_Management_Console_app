package com.tss.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

import com.tss.database.DBConnection;
import com.tss.model.Teacher;

public class TeacherDao {
	
	public TeacherDao(){
		this.conn = DBConnection.connect();
	}
	
	private Connection conn = null;
	private Statement stmt = null;
	
	public List<Teacher> getAllTeachers() {
		List<Teacher> teachers = new ArrayList<>();

		try{
			stmt  = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Teachers");

			while (rs.next()) {
				Teacher teacher = new Teacher(rs.getInt("teacher_id"), rs.getString("teacher_name"), rs.getBoolean("is_active"),
						rs.getString("joining_date"));
				teachers.add(teacher);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return teachers;
	}

}
