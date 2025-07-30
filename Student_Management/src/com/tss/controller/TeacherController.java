package com.tss.controller;

import java.util.List;
import java.util.Scanner;

import com.tss.model.Profile;
import com.tss.model.Teacher;
import com.tss.service.ProfileService;
import com.tss.service.TeacherService;

public class TeacherController {
	Scanner scanner  = new Scanner(System.in);
	private TeacherService  teacherService = new TeacherService();
	private ProfileService profileService = new ProfileService();

	public void displayAllTeachers() {

	    List<Teacher> teachers = teacherService.getAllTeachers();
	    List<Profile> profiles = profileService.readAllProfiles("Teacher");

	    if (teachers.isEmpty()) {
	        System.out.println("No Teacher Found.");
	        return;
	    }

	    // Header
	    System.out.println("\n+-----------------------------------------------------------------------------------------------------------------------------------------------------+");
	    System.out.println("|                                                         TEACHER RECORDS                                                                             |");
	    System.out.println("+-----------------------------------------------------------------------------------------------------------------------------------------------------+");
	    System.out.printf("| %-10s | %-20s | %-6s | %-15s | %-25s | %-20s | %-5s | %-25s |\n", 
	                      "Teacher ID", "Name", "Active", "Phone", "Email", "Address", "Age", "Joining Date");
	    System.out.println("+-----------------------------------------------------------------------------------------------------------------------------------------------------+");

	    for (Teacher teacher : teachers) {
	        Profile matchedProfile = null;
	        for (Profile profile : profiles) {
	            if (profile.getUserId() == teacher.getTeacherId()) {
	                matchedProfile = profile;
	                break;
	            }
	        }

	        String phone = matchedProfile != null ? matchedProfile.getPhoneNumber() : "N/A";
	        String email = matchedProfile != null ? matchedProfile.getEmail() : "N/A";
	        String address = matchedProfile != null ? matchedProfile.getAddress() : "N/A";
	        int age = matchedProfile != null ? matchedProfile.getAge() : 0;

	        System.out.printf("| %-10d | %-20s | %-6s | %-15s | %-25s | %-20s | %-5d | %-25s |\n",
	                teacher.getTeacherId(),
	                teacher.getTeacherName(),
	                teacher.isActive() ? "Yes" : "No",
	                phone,
	                email,
	                address,
	                age,
	                teacher.getJoiningDate());
	    }

	    System.out.println("+-----------------------------------------------------------------------------------------------------------------------------------------------------+");
	}

}
