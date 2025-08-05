package com.tss.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import com.tss.exception.ValidationException;
import com.tss.model.Profile;
import com.tss.model.Teacher;
import com.tss.service.ProfileService;
import com.tss.service.TeacherService;

public class TeacherController {
	Scanner scanner = new Scanner(System.in);
	private TeacherService teacherService = new TeacherService();
	private ProfileService profileService = new ProfileService();
	SubjectController subjectController = new SubjectController();

	public void displayAllTeachers() {

		List<Teacher> teachers = teacherService.getAllTeachers();
		List<Profile> profiles = profileService.readAllProfiles("Teacher");

		if (teachers.isEmpty()) {
			System.out.println("No Teacher Found.");
			return;
		}

		System.out.println(
				"\n+-----------------------------------------------------------------------------------------------------------------------------------------------------+");
		System.out.println(
				"|                                                         TEACHER RECORDS                                                                             |");
		System.out.println(
				"+-----------------------------------------------------------------------------------------------------------------------------------------------------+");
		System.out.printf("| %-10s | %-20s | %-6s | %-15s | %-25s | %-20s | %-5s | %-25s |\n", "Teacher ID", "Name",
				"Active", "Phone", "Email", "Address", "Age", "Joining Date");
		System.out.println(
				"+-----------------------------------------------------------------------------------------------------------------------------------------------------+");

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
					teacher.getTeacherId(), teacher.getTeacherName(), teacher.isActive() ? "Yes" : "No", phone, email,
					address, age, teacher.getJoiningDate());
		}

		System.out.println(
				"+-----------------------------------------------------------------------------------------------------------------------------------------------------+");
	}

	public void addTeacher() {
	    try {
	        System.out.print("Enter Teacher Name: ");
	        String name = scanner.nextLine().trim();

	        boolean isActive = true;

	        System.out.print("Enter Joining Date (yyyy-MM-dd HH:mm) or press Enter for now: ");
	        String dateInput = scanner.nextLine().trim();
	        LocalDateTime admission = dateInput.isEmpty()
	                ? LocalDateTime.now()
	                : LocalDateTime.parse(dateInput.replace(" ", "T"));

	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        String formattedDate = admission.format(formatter);

	        Teacher teacher = new Teacher(0, name, isActive, formattedDate);
	        boolean success = teacherService.addTeacher(teacher);

	        if (success) {
	            int teacherId = teacher.getTeacherId(); // Ensure this ID is being set after insert

	            boolean profileSuccess = false;
	            while (!profileSuccess) {
	                try {
	                    System.out.print("Enter Phone Number: ");
	                    String phone = scanner.nextLine().trim();
	                    if (!phone.matches("\\d{10}")) {
	                        throw new ValidationException("Phone number must be exactly 10 digits.");
	                    }

	                    System.out.print("Enter Email: ");
	                    String email = scanner.nextLine().trim();
	                    if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
	                        throw new ValidationException("Invalid email format.");
	                    }

	                    System.out.print("Enter Address: ");
	                    String address = scanner.nextLine().trim();
	                    if (address.isEmpty()) {
	                        throw new ValidationException("Address cannot be empty.");
	                    }

	                    System.out.print("Enter Age: ");
	                    int age = Integer.parseInt(scanner.nextLine().trim());
	                    if (age <= 0) {
	                        throw new ValidationException("Age must be a positive integer.");
	                    }

	                    Profile profile = new Profile();
	                    profile.setUserType("teacher");
	                    profile.setUserId(teacherId);
	                    profile.setPhoneNumber(phone);
	                    profile.setEmail(email);
	                    profile.setAddress(address);
	                    profile.setAge(age);

	                    profileSuccess = profileService.insertProfile(profile);

	                    if (profileSuccess) {
	                        System.out.println("Teacher and profile added successfully.");
	                        
	                    } else {
	                        System.out.println("Teacher added, but failed to add profile.");
	                    }
	                    
	                    displayAllTeachers();

	                } catch (ValidationException ve) {
	                    System.out.println("Validation Error: " + ve.getMessage());
	                } catch (Exception e) {
	                    System.out.println("Error: " + e.getMessage());
	                }
	            }
	        } else {
	            System.out.println("Failed to add teacher.");
	        }

	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	}

	public void getTeacherById() {
		System.out.print("Enter Teacher ID: ");
		int id = scanner.nextInt();
		Teacher teacher = teacherService.getByIdTeacher(id);
		if (teacher != null) {
			System.out.println(teacher);
		} else {
			System.out.println("Teacher not found.");
		}
	}

	public boolean deleteTeacher() {
		System.out.print("Enter Teacher ID to Delete: ");
		int deleteId = scanner.nextInt();
		boolean updated = teacherService.deleteTeacher(deleteId);
		if (updated) {
			System.out.println("Teacher delete successfully!");
		} else {
			System.out.println("Delete failed.");
		}
		return updated;
	}

	public boolean assignSubject() {
		System.out.print("Enter Teacher ID: ");
		int teacherId = scanner.nextInt();

		System.out.println("Subjects Tables");
		subjectController.readAllSubjects();

		System.out.print("Enter Subject ID: ");
		int subjectId = scanner.nextInt();
		boolean assigned = teacherService.assignSubject(teacherId, subjectId);
		if (assigned) {
			System.out.println("Subject Assign to Teacher successfully!");
		} else {
			System.out.println("Assignation failed.");
		}
		return assigned;

	}

	public boolean removeSubject() {
		System.out.print("Enter Teacher ID: ");
		int teacherId = scanner.nextInt();

		System.out.println("Subjects Tables");
		subjectController.readAllSubjects();

		System.out.print("Enter Subject ID: ");
		int subjectId = scanner.nextInt();
		boolean removed = teacherService.removeSubject(teacherId, subjectId);
		if (removed) {
			System.out.println("Subject removed From Teacher successfully!");
		} else {
			System.out.println("Remove Operation failed.");
		}
		return removed;

	}
}
