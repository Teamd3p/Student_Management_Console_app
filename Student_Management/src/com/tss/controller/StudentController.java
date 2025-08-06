package com.tss.controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import com.tss.exception.ValidationException;
import com.tss.model.Fees;
import com.tss.model.Profile;
import com.tss.model.Student;
import com.tss.service.FeeService;
import com.tss.service.NotificationService;
import com.tss.service.ProfileService;
import com.tss.service.StudentService;
import com.tss.util.InputValidator;

public class StudentController {

	private StudentService studentService;
	private ProfileService profileService;
	private StudentCourseController studentCourseController;
	private NotificationService notificationService;
	private FeeService feeService;
	private Scanner scanner = new Scanner(System.in);

	public StudentController() {
		this.studentService = new StudentService();
		this.profileService = new ProfileService();
		this.notificationService = new NotificationService();
		this.feeService = new FeeService();
	}

	public void readAllRecords() {
		List<Student> students = studentService.readAllStudent();
		List<Profile> profiles = profileService.readAllProfiles("student");
		System.out.println(
				"\n+----------------------------------------------------------------------------------------------------------------------------------------------------------+");
	
		System.out.println(
				"|                                                           STUDENT RECORDS                                                                                |");

		System.out.println(
				"+----------------------------------------------------------------------------------------------------------------------------------------------------------+");

		
		System.out.printf("| %-12s | %-20s | %-6s | %-15s | %-25s | %-30s | %-3s | %-20s |\n",
				"Student ID", "Name", "Active", "Phone", "Email", "Address", "Age", "Admission");

		
		System.out.println(
				"+----------------------------------------------------------------------------------------------------------------------------------------------------------+");
		
		for (Student student : students) {
			Profile matchedProfile = null;
			for (Profile profile : profiles) {
				if (profile.getUserId() == student.getStudentId()) {
					matchedProfile = profile;
					break;
				}
			}
			String phone = matchedProfile != null ? matchedProfile.getPhoneNumber() : "N/A";
			String email = matchedProfile != null ? truncate(matchedProfile.getEmail(), 25) : "N/A";
			String address = matchedProfile != null ? truncate(matchedProfile.getAddress(), 30) : "N/A";
			int age = matchedProfile != null ? matchedProfile.getAge() : 0;


			System.out.printf("| %-12d | %-20s | %-6s | %-15s | %-25s | %-30s | %-3d | %-20s |\n",
					student.getStudentId(),student.getStudentName(),student.isActive() ? "Yes" : "No",
					phone,email,address,age,student.getAdmission());
		}

		System.out.println(
				"+----------------------------------------------------------------------------------------------------------------------------------------------------------+");
	}

	
	private String truncate(String value, int limit) {
		if (value == null) return "N/A";
		return value.length() > limit ? value.substring(0, limit - 3) + "..." : value;
	}

	public void insertStudent() throws ValidationException {
		try {
			String name = InputValidator.readName("Enter Full Student Name: ");

			System.out.print("Enter Admission Date (yyyy-MM-dd HH:mm) or press Enter for now: ");
			String dateInput = scanner.nextLine().trim();
			LocalDateTime admission = dateInput.isEmpty() ? LocalDateTime.now()
					: LocalDateTime.parse(dateInput.replace(" ", "T"));

			Student student = new Student(name, admission);
			if (!studentService.insertStudent(student)) {
				System.out.println("Failed to add student.");
				return;
			}

			int studentId = student.getStudentId();

			// --- Profile ---
			Profile profile = new Profile();
			profile.setUserType("student");
			profile.setUserId(studentId);

			while (true) {
				try {
					String phone = InputValidator.readPhone("Enter Phone Number (10 digits): ");
					profile.setPhoneNumber(phone);
					break;
				} catch (ValidationException e) {
					System.out.println("Error: " + e.getMessage());
				}
			}

			while (true) {
				try {
					String email = InputValidator.readEmail("Enter Email: ");
					profile.setEmail(email);
					break;
				} catch (ValidationException e) {
					System.out.println("Error: " + e.getMessage());
				}
			}

			while (true) {
				try {
					String address = InputValidator.readAddress("Enter Address: ");
					profile.setAddress(address);
					break;
				} catch (ValidationException e) {
					System.out.println("Error: " + e.getMessage());
				}
			}

			while (true) {
				try {
					int age = InputValidator.readAge("Enter Age: ");
					profile.setAge(age);
					break;
				} catch (ValidationException e) {
					System.out.println("Error: " + e.getMessage());
				}
			}

			boolean profileSuccess = profileService.insertProfile(profile);

			String preference = "None";
			while (true) {
				try {
					System.out.println("╔══════════════════════════════════╗");
					System.out.println("║     CHOOSE NOTIFICATION TYPE     ║");
					System.out.println("╠══════════════════════════════════╣");
					System.out.println("║ 1. SMS                           ║");
					System.out.println("║ 2. Email                         ║");
					System.out.println("║ 3. Both                          ║");
					System.out.println("║ 4. None                          ║");
					System.out.println("╚══════════════════════════════════╝");
					int choice = InputValidator.readChoice("Enter your choice: ");

					switch (choice) {
						case 1:
							preference = "SMS";
							break;
						case 2:
							preference = "Email";
							break;
						case 3:
							preference = "Both";
							break;
						case 4:
							preference = "None";
							break;
					}
					break;
				} catch (ValidationException e) {
					System.out.println("Error: " + e.getMessage());
				}
			}

			boolean notificationSuccess = notificationService.insertNotificationPreference(studentId, preference);

			if (profileSuccess && notificationSuccess) {
				System.out.println("Student, profile, and notification preference added successfully.");
			} else if (!profileSuccess && !notificationSuccess) {
				System.out.println("Student added, but failed to add profile and notification preference.");
			} else if (!profileSuccess) {
				System.out.println("Student added, but failed to add profile.");
			} else {
				System.out.println("Student added, but failed to add notification preference.");
			}

			readAllRecords();

		} catch (ValidationException ve) {
			throw ve;
		} catch (Exception e) {
			throw new ValidationException("Error during student insertion: " + e.getMessage());
		}
	}

	public void searchStudentById() {
		try {
			int id = InputValidator.readStudentId("Enter Student ID to search: ");
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

	public boolean studentExistance(int studentId) {
		Student student = studentService.readStudentById(studentId);
		return student != null && student.isActive();
	}

	public void deleteStudentById() {
		readAllRecords();
		try {
			int id = InputValidator.readStudentId("Enter Student ID to Delete: ");
			
			List<Fees> fees = feeService.getFeesByStudent(id);
			
			boolean isPending = fees.stream().anyMatch(fee -> fee.getAmountPending() > 0);
			if (fees.isEmpty()) {
			    System.out.println("No fee records found for student ID: " + id);
			    return;
			} else if (isPending) {
			    System.out.println("Student with ID " + id + " has pending fees.");
			    return;
			}

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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void payStudentFees(int studentId) {
		try {
		if (studentCourseController == null)
			studentCourseController = new StudentCourseController();

		List<Fees> enrolledCourses = studentCourseController.getEnrolledCoursesByStudentId(studentId);

		if (enrolledCourses == null || enrolledCourses.isEmpty()) {
			System.out.println("No enrolled courses for this student.");
			return;
		}

		PaymentController paymentController = new PaymentController();
		paymentController.handleStudentFeePayment(studentId, enrolledCourses);
		}
		catch(ValidationException e)
		{
			System.out.println(e.getMessage());
		}
	}

	public void showAllCoursesById() {
		try {
			readAllRecords();
			int id = InputValidator.readStudentId("Enter Student ID: ");
			if (!studentExistance(id)) {
				System.out.println("Student with ID " + id + " not found Or Inactive.");
				return;
			}

			studentCourseController = new StudentCourseController();
			studentCourseController.getAllCourses(id);
		} catch (ValidationException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public boolean manageNotification() {
	    readAllRecords();
	    try {
	        int studentId = InputValidator.readStudentId("Enter Student ID: ");
	        NotificationController notificationController = new NotificationController();
	        return notificationController.manageStudentNotification(studentId);
	    } catch (ValidationException e) {
	        System.out.println("Error: " + e.getMessage());
	        return false;
	    }
	}

	public void restoreStudent() {
		List<Student> students = studentService.readAllStudent();
		if (students.isEmpty()) {
			System.out.println("No Student Present In Database !!");
			return;
		}
		boolean hasDeactivated = students.stream().anyMatch(s -> !s.isActive());
		if (!hasDeactivated) {
			System.out.println("No deactivated students found.");
			return;
		}

		String border = "+------------+--------------------------+--------+---------------------+";
		System.out.println(border);
		System.out.println("|                    DEACTIVATED STUDENT DETAILS                       |");
		System.out.println(border);
		System.out.printf("| %-10s | %-24s | %-6s | %-19s |\n", "Student ID", "Name", "Active", "Admission");
		System.out.println(border);

		for (Student student : students) {
			if (!student.isActive()) {
				System.out.printf("| %-10d | %-24s | %-6s | %-19s |\n",
						student.getStudentId(), student.getStudentName(), "No", student.getAdmission());
			}
		}
		System.out.println(border);

		try {
			int studentId = InputValidator.readStudentId("Enter Student ID: ");
			Student student = studentService.readStudentById(studentId);
			if (student == null || student.isActive()) {
				System.out.println("Student Is Already Active Or Not Found !!");
				return;
			}

			boolean restored = studentService.restoreStudent(studentId);
			System.out.println(restored ? "Restored Successfully !!" : "Restore Failed !!");

		} catch (ValidationException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
