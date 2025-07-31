package com.tss.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tss.database.DBConnection;
import com.tss.model.Dashboard;

public class DashBoardDao {

	 private Connection connection = null;

	    public DashBoardDao() {
	    	  this.connection = DBConnection.connect();	   
	    	  }
	    
	    public List<Dashboard> getDashboardData() {
	        List<Dashboard> list = new ArrayList<>();
	        String query = """
	                SELECT
	                    ROW_NUMBER() OVER (ORDER BY RAND()) AS `Sr No`,
	                    s.student_id,
	                    s.student_name,
	                    c.course_name,
	                    IFNULL(f.amount_paid, 0),
	                    (IFNULL(c.course_fees, 0) - IFNULL(f.amount_paid, 0)),
	                    IFNULL(c.course_fees, 0),
	                    GROUP_CONCAT(DISTINCT sub.subject_name ORDER BY sub.subject_name SEPARATOR ', '),
	                    GROUP_CONCAT(DISTINCT t.teacher_name ORDER BY t.teacher_name SEPARATOR ', ')
	                FROM Students s
	                LEFT JOIN StudentCourse sc ON sc.student_id = s.student_id
	                LEFT JOIN Courses c ON c.course_id = sc.course_id
	                LEFT JOIN Fees f ON f.student_id = s.student_id AND f.course_id = c.course_id
	                LEFT JOIN CourseSubjects cs ON cs.course_id = c.course_id
	                LEFT JOIN Subjects sub ON sub.subject_id = cs.subject_id
	                LEFT JOIN TeacherSubjects ts ON ts.subject_id = sub.subject_id
	                LEFT JOIN Teachers t ON t.teacher_id = ts.teacher_id
	                GROUP BY s.student_id, s.student_name, c.course_id, c.course_name, c.course_fees, f.amount_paid
	                """;

	        try (PreparedStatement stmt = connection.prepareStatement(query);
	             ResultSet rs = stmt.executeQuery()) {

	            while (rs.next()) {
	                Dashboard dash = new Dashboard(
	                    rs.getInt(1),  
	                    rs.getInt(2),  
	                    rs.getString(3), 
	                    rs.getString(4), 
	                    rs.getDouble(5), 
	                    rs.getDouble(6), 
	                    rs.getDouble(7), 
	                    rs.getString(8), 
	                    rs.getString(9) 
	                );
	                list.add(dash);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return list;
	    }	    
}
