package com.tss.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tss.database.DBConnection;
import com.tss.dto.TeacherWithProfileDTO;
import com.tss.model.Profile;
import com.tss.model.Subject;
import com.tss.model.Teacher;

public class TeacherDao {

	private Connection connection = null;
	private Statement stmt = null;
	private PreparedStatement prepareStatement = null;

	public TeacherDao() {
		this.connection = DBConnection.connect();
	}

	public List<Teacher> getAllTeachers() {
		List<Teacher> teachers = new ArrayList<>();

		try {
			stmt = connection.createStatement();
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
			prepareStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			prepareStatement.setString(1, teacher.getTeacherName());
			prepareStatement.setBoolean(2, teacher.isActive());
			prepareStatement.setString(3, teacher.getJoiningDate());

			int rowsInserted = prepareStatement.executeUpdate();

			if (rowsInserted > 0) {
				ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
				if (generatedKeys.next()) {
					int generatedId = generatedKeys.getInt(1);
					teacher.setTeacherId(generatedId);
				}
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public Teacher getByIdTeacher(int id) {
		String sql = "SELECT t.teacher_id, t.teacher_name, s.subject_name, t.is_active, t.joining_date "
				+ "FROM Teachers AS t " + "JOIN TeacherSubjects AS ts ON t.teacher_id = ts.teacher_id "
				+ "JOIN Subjects AS s ON ts.subject_id = s.subject_id " + "WHERE t.teacher_id = ?;";

		try {
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setInt(1, id);

			try (ResultSet rs = prepareStatement.executeQuery()) {
				List<String> subjects = new ArrayList<>();
				int teacherId = 0;
				String teacherName = null;
				boolean isActive = false;
				String joiningDate = null;

				while (rs.next()) {
					if (teacherName == null) { // capture common teacher details once
						teacherId = rs.getInt("teacher_id");
						teacherName = rs.getString("teacher_name");
						isActive = rs.getBoolean("is_active");
						joiningDate = rs.getString("joining_date");
					}
					subjects.add(rs.getString("subject_name"));
				}

				if (teacherName != null) {
					return new Teacher(teacherId, teacherName, subjects, isActive, joiningDate);
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
		String deleteSub = "DELETE FROM TeacherSubjects WHERE teacher_id = ?";
		String checkSql = "SELECT is_active FROM Teachers WHERE teacher_id = ?";
		String updateSql = "UPDATE Teachers SET is_active = false WHERE teacher_id = ?";

		try {
			prepareStatement = connection.prepareStatement(checkSql);
			prepareStatement.setInt(1, id);
			ResultSet rs = prepareStatement.executeQuery();

			if (rs.next()) {
				boolean isActive = rs.getBoolean("is_active");

				if (!isActive) {
					System.out.println("Teacher is already inactive.");
					return false;
				}

				prepareStatement = connection.prepareStatement(deleteSub);
				prepareStatement.setInt(1, id);
				if (prepareStatement.executeUpdate() > 0) {
					prepareStatement = connection.prepareStatement(updateSql);
					prepareStatement.setInt(1, id);
					int rowsUpdated = prepareStatement.executeUpdate();
					return rowsUpdated > 0;
				}
			} else {
				System.out.println("Teacher ID not found.");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public boolean assignSubject(int teacherId, int subjectId) {
		String checkTeacher = "SELECT teacher_id FROM Teachers WHERE teacher_id = ?";
		String checkSubject = "SELECT subject_id FROM Subjects WHERE subject_id = ?";
		String checkAssignment = "SELECT * FROM TeacherSubjects WHERE teacher_id = ? AND subject_id = ?";
		String insertSql = "INSERT INTO TeacherSubjects(teacher_id, subject_id) VALUES (?, ?)";

		try {
			// 1. Check if teacher exists
			PreparedStatement checkTec = connection.prepareStatement(checkTeacher);
			checkTec.setInt(1, teacherId);
			ResultSet tecRs = checkTec.executeQuery();
			if (!tecRs.next()) {
				System.out.println("Teacher ID not found.");
				return false;
			}

			// 2. Check if subject exists
			PreparedStatement checkSub = connection.prepareStatement(checkSubject);
			checkSub.setInt(1, subjectId);
			ResultSet subRs = checkSub.executeQuery();
			if (!subRs.next()) {
				System.out.println("Subject ID not found.");
				return false;
			}

			// 3. Check if already assigned
			PreparedStatement checkAssign = connection.prepareStatement(checkAssignment);
			checkAssign.setInt(1, teacherId);
			checkAssign.setInt(2, subjectId);
			ResultSet assignRs = checkAssign.executeQuery();
			if (assignRs.next()) {
				System.out.println("Teacher already has this subject assigned.");
				return false;
			}

			// 4. Insert assignment
			PreparedStatement insertQuery = connection.prepareStatement(insertSql);
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
			PreparedStatement checkTec = connection.prepareStatement(checkTeacher);
			checkTec.setInt(1, teacherId);
			ResultSet tecRs = checkTec.executeQuery();
			if (!tecRs.next()) {
				System.out.println("Teacher ID not found.");
				return false;
			}

			// 2. Check if subject exists
			PreparedStatement checkSub = connection.prepareStatement(checkSubject);
			checkSub.setInt(1, subjectId);
			ResultSet subRs = checkSub.executeQuery();
			if (!subRs.next()) {
				System.out.println("Subject ID not found.");
				return false;
			}

			// 3. Check if assignment exists
			PreparedStatement checkAssign = connection.prepareStatement(checkAssignment);
			checkAssign.setInt(1, teacherId);
			checkAssign.setInt(2, subjectId);
			ResultSet assignRs = checkAssign.executeQuery();
			if (!assignRs.next()) {
				System.out.println("This subject is not assigned to the teacher.");
				return false;
			}

			// 4. Perform deletion
			PreparedStatement deleteStmt = connection.prepareStatement(deleteSql);
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

	public List<Subject> getSubjectsOfTeachers(int teacherId) {
		List<Subject> subjects = new ArrayList<Subject>();
		String sql = "SELECT s.* " + "FROM Teachers t " + "JOIN TeacherSubjects tc ON tc.teacher_id = t.teacher_id "
				+ "JOIN Subjects s ON s.subject_id = tc.subject_id " + "WHERE tc.teacher_id = ?";

		try {
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setInt(1, teacherId);
			ResultSet result = prepareStatement.executeQuery();

			while (result.next()) {
				Subject subject = new Subject();
				subject.setSubjectId(result.getInt(1));
				subject.setSubjectName(result.getString(2));
				subjects.add(subject);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return subjects;
	}

	public List<TeacherWithProfileDTO> getAllActiveTeachers() {
		List<TeacherWithProfileDTO> list = new ArrayList<>();

		String query = "SELECT * FROM Teachers INNER JOIN Profiles ON Teachers.teacher_id = Profiles.user_id WHERE Teachers.is_active = true and user_type = 'teacher' ";

		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				Teacher teacher = new Teacher(rs.getInt("teacher_id"), rs.getString("teacher_name"),
						rs.getBoolean("is_active"), rs.getString("joining_date"));

				Profile profile = new Profile(rs.getInt("id"), rs.getString("phone_number"), rs.getString("email"),
						rs.getString("address"), rs.getInt("age"), rs.getString("user_type"), rs.getInt("user_id"));

				TeacherWithProfileDTO twp = new TeacherWithProfileDTO(teacher, profile);
				list.add(twp);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<TeacherWithProfileDTO> getAlldeActiveTeachers() {
		List<TeacherWithProfileDTO> list = new ArrayList<>();

		String query = "SELECT * FROM Teachers INNER JOIN Profiles ON Teachers.teacher_id = Profiles.user_id WHERE Teachers.is_active = false and user_type = 'teacher' ";

		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				Teacher teacher = new Teacher(rs.getInt("teacher_id"), rs.getString("teacher_name"),
						rs.getBoolean("is_active"), rs.getString("joining_date"));

				Profile profile = new Profile(rs.getInt("id"), rs.getString("phone_number"), rs.getString("email"),
						rs.getString("address"), rs.getInt("age"), rs.getString("user_type"), rs.getInt("user_id"));

				TeacherWithProfileDTO twp = new TeacherWithProfileDTO(teacher, profile);
				list.add(twp);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public boolean restoreTeacher(int teacherId) {
		String sql = "UPDATE Teachers SET is_active = true where teacher_id = ? ";
		try {
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setInt(1, teacherId);
			
			return prepareStatement.executeUpdate()>0;
		}catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return false;
	}

}
