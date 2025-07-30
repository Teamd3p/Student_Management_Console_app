package com.tss.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.tss.database.DBConnection;
import com.tss.model.Teacher;

public class TeacherDao {

	public TeacherDao() {
		this.conn = DBConnection.connect();
	}

	private Connection conn = null;
	private Statement stmt = null;

	public List<Teacher> getAllTeachers() {
		List<Teacher> teachers = new ArrayList<>();

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Teachers");

			while (rs.next()) {
				Teacher teacher = new Teacher(rs.getInt("teacher_id"), rs.getString("teacher_name"),
						rs.getBoolean("is_active"), rs.getString("joining_date"));
				teachers.add(teacher);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return teachers;
	}

	public boolean addTeacher(Teacher teacher) {
		String sql = "INSERT INTO Teachers (teacher_name, is_active, joining_date) VALUES (?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, teacher.getTeacherName());
			pstmt.setBoolean(2, teacher.isActive());
			pstmt.setString(3, teacher.getJoiningDate());
			int rowsInserted = pstmt.executeUpdate();
			return rowsInserted > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Teacher getByIdTeacher(int id) {
		String sql = "SELECT * FROM Teachers WHERE teacher_id = ?";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new Teacher(rs.getInt("teacher_id"), rs.getString("teacher_name"),
							rs.getBoolean("is_active"), rs.getString("joining_date"));
				} else {
					System.out.println("No teacher found with ID: " + id);
					return null;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public boolean deleteTeacher(int id) {
	    String checkSql = "SELECT is_active FROM Teachers WHERE teacher_id = ?";
	    String updateSql = "UPDATE Teachers SET is_active = false WHERE teacher_id = ?";

	    try {
	        PreparedStatement checkStmt = conn.prepareStatement(checkSql);
	        checkStmt.setInt(1, id);
	        ResultSet rs = checkStmt.executeQuery();

	        if (rs.next()) {
	            boolean isActive = rs.getBoolean("is_active");

	            if (!isActive) {
	                System.out.println("Teacher is already inactive.");
	                return false;
	            }

	            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
	            updateStmt.setInt(1, id);
	            int rowsUpdated = updateStmt.executeUpdate();
	            return rowsUpdated > 0;
	        } else {
	            System.out.println("Teacher ID not found.");
	            return false;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	
	public boolean assignSubject(int teacherId, int subjectId) {
	    String checkTeacher = "SELECT teacher_id FROM Teachers WHERE teacher_id = ?";
	    String checkSubject = "SELECT subject_id FROM Subjects WHERE subject_id = ?";
	    String checkAssignment = "SELECT * FROM TeacherSubjects WHERE teacher_id = ? AND subject_id = ?";
	    String insertSql = "INSERT INTO TeacherSubjects(teacher_id, subject_id) VALUES (?, ?)";

	    try {
	        // 1. Check if teacher exists
	        PreparedStatement checkTec = conn.prepareStatement(checkTeacher);
	        checkTec.setInt(1, teacherId);
	        ResultSet tecRs = checkTec.executeQuery();
	        if (!tecRs.next()) {
	            System.out.println("Teacher ID not found.");
	            return false;
	        }

	        // 2. Check if subject exists
	        PreparedStatement checkSub = conn.prepareStatement(checkSubject);
	        checkSub.setInt(1, subjectId);
	        ResultSet subRs = checkSub.executeQuery();
	        if (!subRs.next()) {
	            System.out.println("Subject ID not found.");
	            return false;
	        }

	        // 3. Check if already assigned
	        PreparedStatement checkAssign = conn.prepareStatement(checkAssignment);
	        checkAssign.setInt(1, teacherId);
	        checkAssign.setInt(2, subjectId);
	        ResultSet assignRs = checkAssign.executeQuery();
	        if (assignRs.next()) {
	            System.out.println("Teacher already has this subject assigned.");
	            return false;
	        }

	        // 4. Insert assignment
	        PreparedStatement insertQuery = conn.prepareStatement(insertSql);
	        insertQuery.setInt(1, teacherId);
	        insertQuery.setInt(2, subjectId);
	        int rowsInserted = insertQuery.executeUpdate();
	        return rowsInserted > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public boolean removeSubject(int teacherId, int subjectId) {
	    String checkTeacher = "SELECT teacher_id FROM Teachers WHERE teacher_id = ?";
	    String checkSubject = "SELECT subject_id FROM Subjects WHERE subject_id = ?";
	    String checkAssignment = "SELECT * FROM TeacherSubjects WHERE teacher_id = ? AND subject_id = ?";
	    String deleteSql = "DELETE FROM TeacherSubjects WHERE teacher_id = ? AND subject_id = ?";

	    try {
	        // 1. Check if teacher exists
	        PreparedStatement checkTec = conn.prepareStatement(checkTeacher);
	        checkTec.setInt(1, teacherId);
	        ResultSet tecRs = checkTec.executeQuery();
	        if (!tecRs.next()) {
	            System.out.println("Teacher ID not found.");
	            return false;
	        }

	        // 2. Check if subject exists
	        PreparedStatement checkSub = conn.prepareStatement(checkSubject);
	        checkSub.setInt(1, subjectId);
	        ResultSet subRs = checkSub.executeQuery();
	        if (!subRs.next()) {
	            System.out.println("Subject ID not found.");
	            return false;
	        }

	        // 3. Check if assignment exists
	        PreparedStatement checkAssign = conn.prepareStatement(checkAssignment);
	        checkAssign.setInt(1, teacherId);
	        checkAssign.setInt(2, subjectId);
	        ResultSet assignRs = checkAssign.executeQuery();
	        if (!assignRs.next()) {
	            System.out.println("This subject is not assigned to the teacher.");
	            return false;
	        }

	        // 4. Perform deletion
	        PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
	        deleteStmt.setInt(1, teacherId);
	        deleteStmt.setInt(2, subjectId);
	        int rowsDeleted = deleteStmt.executeUpdate();
	        if (rowsDeleted > 0) {
	            return true;
	        } else {
	            return false;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}



}
