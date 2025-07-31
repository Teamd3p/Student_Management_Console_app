package com.tss.service;

import java.util.List;

import com.tss.dao.DashBoardDao;
import com.tss.model.Dashboard;

public class DashBoardService {

	
	private DashBoardDao dashboardDao;
	
	public DashBoardService() {
		this.dashboardDao = new DashBoardDao();
	} 
	
	public List<Dashboard> getDashboardData(){
		return dashboardDao.getDashboardData();
	}
	
	
}
