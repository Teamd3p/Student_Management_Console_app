package com.tss.controller;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.tss.exception.ValidationException;
import com.tss.model.Profile;
import com.tss.model.Student;
import com.tss.service.ProfileService;
import com.tss.service.StudentService;

public class StudentController {

	private StudentService studentService;
	private ProfileService profileService;
	private FeeController feecontroller;
	private StudentCourseController studentCourseController;
	private Scanner scanner = new Scanner(System.in);

	public StudentController() {
		this.studentService = new StudentService();
		this.profileService = new ProfileService();
	}

	public void readAllRecords() {
		List<Student> students = studentService.readAllStudent();
		List<Profile> profiles = profileService.readAllProfiles("student");

		System.out.println(
				"\n+-----------------------------------------------------------------------------------------------------------------------------------------------------+");
		System.out.println(
				"|                                                           STUDENT RECORDS                                                                           |");
		System.out.println(
				"+-----------------------------------------------------------------------------------------------------------------------------------------------------+");
		System.out.printf("| %-10s | %-20s | %-6s | %-15s | %-25s | %-20s | %-5s | %-25s |\n", "Student ID", "Name",
				"Active", "Phone", "Email", "Address", "Age", "Admission");
		System.out.println(
				"+-----------------------------------------------------------------------------------------------------------------------------------------------------+");

		for (Student student : students) {
			Profile matchedProfile = null;
			for (Profile profile : profiles) {
				if (profile.getUserId() == student.getStudentId()) {
					matchedProfile = profile;
					break;
				}
			}

			String phone = matchedProfile != null ? matchedProfile.getPhoneNumber() : "N/A";
			String email = matchedProfile != null ? matchedProfile.getEmail() : "N/A";
			String address = matchedProfile != null ? matchedProfile.getAddress() : "N/A";
			int age = matchedProfile != null ? matchedProfile.getAge() : 0;

			System.out.printf("| %-10d | %-20s | %-6s | %-15s | %-25s | %-20s | %-5d | %-25s |\n",
					student.getStudentId(), student.getStudentName(), student.isActive() ? "Yes" : "No", phone, email,
					address, age, student.getAdmission());
		}

		System.out.println(
				"+-----------------------------------------------------------------------------------------------------------------------------------------------------+");
	}

	public void insertStudent() throws ValidationException {
		try {
			System.out.print("\nEnter Full Student Name: ");
			String name = scanner.nextLine();
			if (!name.contains(" ") || !name.matches("[a-zA-Z ]+")) {
				throw new ValidationException("Enter Proper Name");
			}

			LocalDateTime admission = null;

			while (true) {
				try {
					System.out.print("Enter Admission Date (yyyy-MM-dd HH:mm) or press Enter for now: ");
					String dateInput = scanner.nextLine().trim();

					if (dateInput.isEmpty()) {
						admission = LocalDateTime.now();
						break;
					}

					admission = LocalDateTime.parse(dateInput.replace(" ", "T"));

					if (admission.isAfter(LocalDateTime.now())) {
						throw new ValidationException("Admission date cannot be in the future.");
					}

					break;
				} catch (ValidationException e) {
					System.out.println("Error: " + e.getMessage());
				}
			}

			Student student = new Student(name, admission);

			boolean success = studentService.insertStudent(student);

			if (success) {
				Profile profile = new Profile();
				int studentId = student.getStudentId();
				profile.setUserType("student");
				profile.setUserId(studentId);

				while (true) {
					try {
						System.out.print("Enter Phone Number (10 digits): ");
						String phone = scanner.nextLine().trim();
						if (!phone.matches("\\d{10}")) {
							throw new ValidationException("Phone number must be exactly 10 digits and positive.");
						}
						profile.setPhoneNumber(phone);
						break;
					} catch (ValidationException e) {
						System.out.println("Error: " + e.getMessage());
					}
				}

				while (true) {
					try {
						System.out.print("Enter Email: ");
						String email = scanner.nextLine().trim();
						if (!email.matches("^\\S+@\\S+\\.\\S+$")) {
							throw new ValidationException("Invalid email format.");
						}
						profile.setEmail(email);
						break;
					} catch (ValidationException e) {
						System.out.println("Error: " + e.getMessage());
					}
				}

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

				while (true) {
					try {
						System.out.print("Enter Age: ");
						String ageStr = scanner.nextLine().trim();
						if (!ageStr.matches("\\d+"))
							throw new ValidationException("Age must be a positive integer.");
						int age = Integer.parseInt(ageStr);
						if (age < 1 || age > 80)
							throw new ValidationException("Age Must Be Between 1 to 80");
						profile.setAge(age);
						break;
					} catch (ValidationException e) {
						System.out.println("Error: " + e.getMessage());
					}
				}
				boolean profileSuccess = profileService.insertProfile(profile);
				if (profileSuccess) {
					System.out.println("Student and profile added successfully.");
				} else {
					System.out.println("Student added, but failed to add profile.");
				}

				readAllRecords();
			} else {
				System.out.println("Failed to add student.");
			}

		} catch (ValidationException ve) {
			throw ve;
		} catch (Exception e) {
			throw new ValidationException("Error during student insertion: " + e.getMessage());
		}
	}

	public void searchStudentById() {
		try {
			System.out.print("Enter Student ID to search: ");
			String input = scanner.nextLine().trim();

			if (!input.matches("\\d+"))
				throw new ValidationException("Student ID must be a positive number.");

			int id = Integer.parseInt(input);
			if (id <= 0)
				throw new ValidationException("Student ID must be greater than zero.");

			Student student = studentService.readStudentById(id);

			if (student != null) {
				String border = "+------------------------------------------------------------+";
				String title = "|                    STUDENT DETAIL                          |";

				System.out.println(border);
				System.out.println(title);
				System.out.println(border);
				System.out.printf("| %-15s : %-40s |\n", "Student ID", student.getStudentId());
				System.out.printf("| %-15s : %-40s |\n", "Name", student.getStudentName());
				System.out.printf("| %-15s : %-40s |\n", "Active", student.isActive() ? "Yes" : "No");
				System.out.printf("| %-15s : %-40s |\n", "Admission", student.getAdmission());
				System.out.println(border);
			} else {
				System.out.println("Student with ID " + id + " not found.");
			}
		} catch (ValidationException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public boolean studentExistance(int student_id) {
		Student student = studentService.readStudentById(student_id);
		return student != null;
	}

	public void deleteStudentById() {
		readAllRecords();
		try {
			System.out.print("Enter Student ID to Delete: ");
			String input = scanner.nextLine().trim();

			if (!input.matches("\\d+"))
				throw new ValidationException("Student ID must be a positive number.");

			int id = Integer.parseInt(input);
			if (id <= 0)
				throw new ValidationException("Student ID must be greater than zero.");

			feecontroller = new FeeController();
			studentCourseController = new StudentCourseController();

			if (feecontroller.checkPendingFees(id)) {
				System.out.println("Cannot Deactivate Student Because Student Fees is Pending !!");
				return;
			}
			
			feecontroller.deleteStudent(id);
			studentCourseController.deleteCourseFromStudent(id);
			Student student = studentService.deleteStudentById(id);

			if (student != null) {
				String border = "+------------------------------------------------------------+";
				String title = "|                    DELETED STUDENT DETAIL                   |";

				System.out.println(border);
				System.out.println(title);
				System.out.println(border);
				System.out.printf("| %-15s : %-40s |\n", "Student ID", student.getStudentId());
				System.out.printf("| %-15s : %-40s |\n", "Name", student.getStudentName());
				System.out.printf("| %-15s : %-40s |\n", "Active", student.isActive() ? "Yes" : "No");
				System.out.printf("| %-15s : %-40s |\n", "Admission", student.getAdmission());
				System.out.println(border);
			} else {
				System.out.println("Student with ID " + id + " not found Or Already Inactive.");
			}
		} catch (ValidationException e) {
			System.out.println("Error: " + e.getMessage());
		}
<<<<<<< HEAD

		studentCourseController.deleteCourseFromStudent(id);
		Student student = studentService.deleteStudentById(id);

		if (student != null) {
			String border = "+------------------------------------------------------------+";
			String title = "|                    DELETED STUDENT DETAIL                   |";

			System.out.println(border);
			System.out.println(title);
			System.out.println(border);
			System.out.printf("| %-15s : %-40s |\n", "Student ID", student.getStudentId());
			System.out.printf("| %-15s : %-40s |\n", "Name", student.getStudentName());
			System.out.printf("| %-15s : %-40s |\n", "Active", student.isActive() ? "Yes" : "No");
			System.out.printf("| %-15s : %-40s |\n", "Admission", student.getAdmission());
			System.out.println(border);
		} else {
			System.out.println("Student with ID " + id + " not found Or Already Inactive.");
		}

=======
>>>>>>> 489fc909dad716c349abb96fff8f00b46d2c18a5
	}
}
