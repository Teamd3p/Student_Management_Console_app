package com.tss.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
        System.out.println(">> Adding a new teacher...");

        // 1. NAME VALIDATION
        String name;
        while (true) {
            System.out.print("Enter Teacher Name: ");
            name = scanner.nextLine().trim();
            if (!name.isEmpty() && name.matches("[a-zA-Z ]+")) {
                break;
            } else {
                System.out.println("Error: Name must contain only letters and spaces.");
            }
        }

        // 2. JOINING DATE VALIDATION
        LocalDateTime joiningDate;
        while (true) {
            try {
                System.out.print("Enter Joining Date (yyyy-MM-dd HH:mm) or press Enter for now: ");
                String dateInput = scanner.nextLine().trim();
                joiningDate = dateInput.isEmpty()
                        ? LocalDateTime.now()
                        : LocalDateTime.parse(dateInput.replace(" ", "T"));

                if (joiningDate.isAfter(LocalDateTime.now())) {
                    throw new ValidationException("Joining date cannot be in the future.");
                }
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Error: Invalid date format.");
            } catch (ValidationException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        String formattedDate = joiningDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Teacher teacher = new Teacher(0, name, true, formattedDate);
        boolean teacherSuccess = teacherService.addTeacher(teacher);

        if (!teacherSuccess) {
            System.out.println("❌ Failed to add teacher.");
            return;
        }

        int teacherId = teacher.getTeacherId();
        Profile profile = new Profile();
        profile.setUserType("teacher");
        profile.setUserId(teacherId);

        // 3. PHONE VALIDATION + DUPLICATE CHECK
        while (true) {
            try {
                System.out.print("Enter Phone Number: ");
                String phone = scanner.nextLine().trim();

                if (!phone.matches("\\d{10}")) {
                    throw new ValidationException("Phone number must be exactly 10 digits.");
                }

                if (profileService.checkDuplicatePhone(phone)) {
                    throw new ValidationException("Phone number already exists.");
                }

                profile.setPhoneNumber(phone);
                break;
            } catch (ValidationException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        // 4. EMAIL VALIDATION + DUPLICATE CHECK
        while (true) {
            try {
                System.out.print("Enter Email: ");
                String email = scanner.nextLine().trim();

                if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                    throw new ValidationException("Invalid email format.");
                }

                if (profileService.checkDuplicateEmail(email)) {
                    throw new ValidationException("Email already exists.");
                }

                profile.setEmail(email);
                break;
            } catch (ValidationException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        // 5. ADDRESS VALIDATION
        while (true) {
            try {
                System.out.print("Enter Address: ");
                String address = scanner.nextLine().trim();

                if (address.isEmpty()) {
                    throw new ValidationException("Address cannot be empty.");
                }

                profile.setAddress(address);
                break;
            } catch (ValidationException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        // 6. AGE VALIDATION
        while (true) {
            try {
                System.out.print("Enter Age: ");
                String ageStr = scanner.nextLine().trim();

                if (!ageStr.matches("\\d+")) {
                    throw new ValidationException("Age must be a number.");
                }

                int age = Integer.parseInt(ageStr);
                if (age < 18 || age > 80) {
                    throw new ValidationException("Age must be between 18 and 80.");
                }

                profile.setAge(age);
                break;
            } catch (ValidationException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        // 7. SAVE PROFILE
        boolean profileSuccess = profileService.insertProfile(profile);
        if (profileSuccess) {
            System.out.println("✅ Teacher and profile added successfully.");
        } else {
            System.out.println("⚠ Teacher added, but failed to add profile.");
        }

        displayAllTeachers();

    } catch (Exception e) {
        System.out.println("Unhandled Error: " + e.getMessage());
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
