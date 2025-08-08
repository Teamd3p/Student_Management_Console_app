package com.tss.service;

import com.tss.dao.NotificationDao;

public class NotificationService {
	private NotificationDao notificationDao = new NotificationDao();
	
	public boolean insertNotificationPreference(int studentId, String preference) {
		return notificationDao.insertNotificationPreference(studentId,preference);
	}
	
	public String getNotificationPreference(int studentId) {
		return notificationDao.getNotificationPreference(studentId);
	}
	
	public boolean updateNotificationPreference(int studentId, String newPreference) {
		return notificationDao.updateNotificationPreference(studentId,newPreference);
	}

}
