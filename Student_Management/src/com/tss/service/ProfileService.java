package com.tss.service;

import java.util.List;

import com.tss.dao.ProfileDao;
import com.tss.model.Profile;

public class ProfileService {

	private ProfileDao profileDao;

	public ProfileService() {
		this.profileDao = new ProfileDao();
	}

	public List<Profile> readAllProfiles(String user_type) {
		return profileDao.readAllProfiles(user_type);
	}

	public boolean insertProfile(Profile profile) {

		return profileDao.insertStudent(profile);
	}
	
	public boolean checkExistanceOfUser(String fullName, String phoneNumber, String userType) {
	    return profileDao.existsProfileByNamePhoneAndType(fullName, phoneNumber, userType);
	}

	public boolean checkExistanceOfEmail(String email, String user_type) {
		return profileDao.checkExistanceOfEmail(email,user_type);
		
	}
}
