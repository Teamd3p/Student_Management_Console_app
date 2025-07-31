package com.tss.service;

import java.sql.SQLException;
import java.util.List;
import com.tss.dao.FeesDao;
import com.tss.model.Fees;

public class FeeService {

    private FeesDao dao = new FeesDao();

    public double getTotalPaidFees() throws SQLException {
        return dao.getTotalPaidFees();
    }

    public double getTotalPendingFees() throws SQLException {
        return dao.getTotalPendingFees();
    }

    public List<Fees> getFeesByStudent(int studentId) throws SQLException {
        return dao.getFeesByStudent(studentId);
    }

    public List<Fees> getFeesByCourse(int courseId) throws SQLException {
        return dao.getFeesByCourse(courseId);
    }

    public boolean updateCourseFees(int courseId, double newAmount) throws SQLException {
        return dao.updateCourseFees(courseId, newAmount);
    }

    public double getTotalEarning() throws SQLException {
        return dao.getTotalEarning();
    }
    public List<Fees> getCourseFeesSummary(int courseId) throws SQLException {
        return FeesDao.getCourseFeesSummary(courseId);  
    }

	public void deleteStudent(int id) {
		FeesDao.deleteStudent(id);
	}

}
