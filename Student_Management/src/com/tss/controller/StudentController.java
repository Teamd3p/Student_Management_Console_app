package com.tss.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.tss.exception.ValidationException;
import com.tss.model.Fees;
import com.tss.model.Profile;
import com.tss.model.Student;
import com.tss.service.NotificationService;
import com.tss.service.ProfileService;
import com.tss.service.StudentService;

public class StudentController {

	private StudentService studentService;
	private ProfileService profileService;
	private StudentCourseController studentCourseController;
	private FeeController feesController;
	private NotificationService notificationService;
	private Scanner scanner = new Scanner(System.in);

	public StudentController() {
		this.studentService = new StudentService();
		this.profileService = new ProfileService();
		this.feesController = new FeeController();
		this.notificationService = new NotificationService();
	}

	public void readAllRecords() {
		List<Student> students = studentService.readAllStudent();
		List<Profile> profiles = profileService.readAllProfiles("student");

		// Header
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

			System.out.print("Enter Admission Date (yyyy-MM-dd HH:mm) or press Enter for now: ");
			String dateInput = scanner.nextLine().trim();
			LocalDateTime admission = dateInput.isEmpty() ? LocalDateTime.now()
					: LocalDateTime.parse(dateInput.replace(" ", "T"));

			Student student = new Student(name, admission);

			boolean success = studentService.insertStudent(student);

			if (success) {
				int studentId = student.getStudentId();

				// --- Profile ---
				Profile profile = new Profile();
				profile.setUserType("student");
				profile.setUserId(studentId);

				// Phone
				while (true) {
					try {
						System.out.print("Enter Phone Number (10 digits): ");
						String phone = scanner.nextLine().trim();
						if (!phone.matches("\\d{10}")) {
							throw new ValidationException("Phone number must be exactly 10 digits.");
						}
						profile.setPhoneNumber(phone);
						break;
					} catch (ValidationException e) {
						System.out.println("Error: " + e.getMessage());
					}
				}

				// Email
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

				// Address
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

				// Age
				while (true) {
					try {
						System.out.print("Enter Age: ");
						String ageStr = scanner.nextLine().trim();
						if (!ageStr.matches("\\d+"))
							throw new ValidationException("Age must be a positive integer.");
						int age = Integer.parseInt(ageStr);
						if (age < 1 || age > 80)
							throw new ValidationException("Age must be between 1 to 80");
						profile.setAge(age);
						break;
					} catch (ValidationException e) {
						System.out.println("Error: " + e.getMessage());
					}
				}

				boolean profileSuccess = profileService.insertProfile(profile);

				// Notification Preference
				String preference = "None";

				while (true) {
					try {
						System.out.println("\nChoose Notification Preference:");
						System.out.println("1. SMS");
						System.out.println("2. Email");
						System.out.println("3. Both");
						System.out.println("4. None");
						System.out.print("Enter your choice: ");

						int choice = Integer.parseInt(scanner.nextLine().trim());
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
						default:
							throw new ValidationException("Invalid choice. Please enter 1-4.");
						}
						break;
					} catch (ValidationException | NumberFormatException e) {
						System.out.println("Error: " + e.getMessage());
					}
				}

				// Insert into Notification table
				boolean notificationSuccess = notificationService.insertNotificationPreference(studentId, preference);

				// Display result
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
		System.out.print("Enter Student ID to search: ");
		int id = scanner.nextInt();
		scanner.nextLine();

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
	}

	public boolean studentExistance(int student_id) {
		Student student = studentService.readStudentById(student_id);
		if (student != null)
		{
			if(student.isActive())
			{
				return true;
			}
		}
		return false;
	}

	public void deleteStudentById() {
		readAllRecords();
		System.out.print("Enter Student ID to Delete: ");
		int id = scanner.nextInt();
		scanner.nextLine();

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

	}

	public void payStudentFees(int studentId) {
		

		if (studentCourseController == null) {
			studentCourseController = new StudentCourseController();
		}

		// Step 1: Display enrolled courses
		List<Fees> enrolledCourses = studentCourseController.getEnrolledCoursesByStudentId(studentId);

		if (enrolledCourses == null || enrolledCourses.isEmpty()) {
			System.out.println("No courses found for the student.");
			return;
		}

		boolean isPending = false;
		for (Fees fee : enrolledCourses) {
			if(fee.getAmountPending()!=0)
			{
				isPending = true;
				break;
			}
		}
		
		if(!isPending)
		{
			System.out.println("Fees ALready Paid !!");
			return;
		}
		
		List<Fees>pendingAmountCourse = new ArrayList<Fees>();
		
		System.out.println("\n+--------------------------------------------------------------+");
		System.out.println("|                  Enrolled Courses                            |");
		System.out.println("+--------------------------------------------------------------+");
		System.out.printf("| %-10s | %-25s | %-10s | %-10s |\n", "Course ID", "Course Name", "Paid (₹)", "Pending (₹)");
		System.out.println("+--------------------------------------------------------------+");

		for (Fees fee : enrolledCourses) {
			System.out.printf("| %-10d | %-25s | %-10.2f | %-11.2f |\n", fee.getCourseId(), fee.getCourseName(),
					fee.getAmountPaid(), fee.getAmountPending());
			
			if(fee.getAmountPending()==0)
			{
				pendingAmountCourse.add(fee);
			}
		}
		System.out.println("+--------------------------------------------------------------+\n");

		// Step 2: Ask course ID to pay
		System.out.print("Enter Course ID to pay fee for: ");
		int courseId = Integer.parseInt(scanner.nextLine().trim());

		// Step 3: Fetch fee detail for selected course
		Fees selectedFee = feesController.getFeeByStudentAndCourse(studentId, courseId);

		if (selectedFee == null) {
			System.out.println("No fee record found for the selected course.");
			return;
		}
		if(selectedFee.getAmountPending() == 0)
		{
			System.out.println("For This Course Fees Is Already Paid .");
			return;
		}

		System.out.println("\n---------------------- Fee Details ----------------------");
		System.out.println("Course ID       : " + selectedFee.getCourseId());
		System.out.println("Course Name     : " + selectedFee.getCourseName());
		System.out.println("Amount Paid     : ₹" + selectedFee.getAmountPaid());
		System.out.println("Amount Pending  : ₹" + selectedFee.getAmountPending());
		System.out.println("Payment Status  : " + selectedFee.getPaymentType());
		System.out.println("---------------------------------------------------------");

		// Step 4: Choose payment method
		String paymentType = null;
		while (true) {
			System.out.println("\nChoose Payment Method:");
			System.out.println("1. Cash");
			System.out.println("2. UPI");
			System.out.println("3. Card");
			System.out.println("4. Exit");
			System.out.print("Enter your choice: ");

			int choice = Integer.parseInt(scanner.nextLine().trim());

			switch (choice) {
			case 1:
				paymentType = "Cash";
				System.out.println("Cash payment selected. Please ensure you have the exact amount.");
				break;

			case 2:
				paymentType = "UPI";
				while (true) {
					System.out.print("Enter UPI ID (e.g., username@bank): ");
					String upiId = scanner.nextLine().trim();
					if (upiId.matches("^[\\w.-]+@[a-zA-Z]+$")) {
						System.out.println("UPI ID validated successfully.");
						break;
					} else {
						System.out.println("Invalid UPI ID. Try again.");
					}
				}
				break;

			case 3:
				paymentType = "Card";
				// Validate Card Number (16 digits)
				while (true) {
					System.out.print("Enter 16-digit Card Number: ");
					String cardNumber = scanner.nextLine().trim();
					if (cardNumber.matches("\\d{16}")) {
						break;
					} else {
						System.out.println("Invalid Card Number. It must be exactly 16 digits.");
					}
				}

				// Validate CVV (3 digits)
				while (true) {
					System.out.print("Enter 3-digit CVV: ");
					String cvv = scanner.nextLine().trim();
					if (cvv.matches("\\d{3}")) {
						break;
					} else {
						System.out.println("Invalid CVV. It must be exactly 3 digits.");
					}
				}

				// Validate Expiry Date (MM/YY)
				while (true) {
					System.out.print("Enter Expiry Date (MM/YY): ");
					String expiry = scanner.nextLine().trim();
					if (expiry.matches("^(0[1-9]|1[0-2])/[0-9]{2}$")) {
						break;
					} else {
						System.out.println("Invalid Expiry Date. Format should be MM/YY.");
					}
				}
				break;

			case 4:
				System.out.println("Payment canceled.");
				return;

			default:
				System.out.println("Invalid choice. Please try again.");
				continue;
			}
			break;
		}

		System.out.print("Enter amount to pay: ₹");
		double amountToPay = 0.0;

		try {
			amountToPay = Double.parseDouble(scanner.nextLine().trim());
		} catch (NumberFormatException e) {
			System.out.println("Invalid amount entered.");
			return;
		}

		// Step 6: Validate and process
		if (amountToPay <= 0) {
			System.out.println("Entered amount cannot be zero or negative.");
			return;
		} else if (amountToPay > selectedFee.getAmountPending()) {
			System.out.println("Your entered amount is more than required pending amount.");
			return;
		}

		boolean success = feesController.processFeePayment(studentId, courseId, amountToPay, paymentType);

		if (success) {
			System.out.println("Payment successful via " + paymentType + ". Amount Paid: ₹" + amountToPay);

			// --- Fetch Notification Preference ---
			String preference = notificationService.getNotificationPreference(studentId);

			// --- Send Notifications Based on Preference ---
			if ("Both".equalsIgnoreCase(preference)) {
				sendReceipt("SMS", studentId, amountToPay, selectedFee.getCourseName(), paymentType);
				sendReceipt("Email", studentId, amountToPay, selectedFee.getCourseName(), paymentType);
			} else if ("SMS".equalsIgnoreCase(preference)) {
				sendReceipt("SMS", studentId, amountToPay, selectedFee.getCourseName(), paymentType);
			} else if ("Email".equalsIgnoreCase(preference)) {
				sendReceipt("Email", studentId, amountToPay, selectedFee.getCourseName(), paymentType);
			} else {
				System.out.println("No notifications set for this student.");
			}

		} else {
			System.out.println("Payment failed.");
		}
	}

	public void showAllCoursesById() throws ValidationException {
		try {
			studentCourseController = new StudentCourseController();

			readAllRecords();
			System.out.print("Enter Student ID : ");
			String input = scanner.nextLine().trim();

			if (!input.matches("\\d+"))
				throw new ValidationException("Student ID must be a positive number.");

			int id = Integer.parseInt(input);
			if (id <= 0)
				throw new ValidationException("Student ID must be greater than zero.");

			if (!studentExistance(id)) {
				System.out.println("Student with ID " + id + " not found Or Inactive.");
				return;
			}
			studentCourseController.getAllCourses(id);
		} catch (ValidationException e) {
			throw e;
		}
	}

	private void sendReceipt(String method, int studentId, double amountToPay, String courseName, String paymentType) {
		System.out.println("\n====================================================");
		System.out.println("                   PAYMENT RECEIPT                  ");
		System.out.println("====================================================");
		System.out.printf("Receipt No.    : %05d\n", (int) (Math.random() * 100000));
		System.out.printf("Date & Time    : %s\n", java.time.LocalDateTime.now());
		System.out.printf("Student ID     : %d\n", studentId);
		System.out.printf("Course Name    : %s\n", courseName);
		System.out.printf("Amount Paid    : ₹%.2f\n", amountToPay);
		System.out.printf("Payment Method : %s\n", paymentType);
		System.out.println("----------------------------------------------------");
		System.out.println("          Thank you for your payment!               ");
		System.out.println("      An official receipt has been sent via         ");
		System.out.println("              " + method.toUpperCase() + " NOTIFICATION             ");
		System.out.println("====================================================\n");
	}

	public boolean manageNotification() {
		System.out.print("Enter Student ID: ");
		int studentId = Integer.parseInt(scanner.nextLine().trim());

		// Step 1: Get current preference
		String currentPreference = notificationService.getNotificationPreference(studentId);
		if (currentPreference == null) {
			System.out.println("No notification preference found for this student.");
			return false;
		}

		System.out.println("\n>>> Current Notification Preference: " + currentPreference);

		// Step 2: Ask to Update or Remove
		System.out.println("\nWhat do you want to do?");
		System.out.println("1. Update Notification");
		System.out.println("2. Remove Notification");
		System.out.println("3. Cancel");
		System.out.print("Enter your choice: ");

		int actionChoice;
		try {
			actionChoice = Integer.parseInt(scanner.nextLine().trim());
		} catch (NumberFormatException e) {
			System.out.println("Invalid input.");
			return false;
		}

		// Step 3: Based on action, handle separately
		String newPreference = currentPreference;

		if (actionChoice == 1) {
			// ----- Update Flow -----
			System.out.println("\nChoose new Notification Preference:");
			System.out.println("1. SMS");
			System.out.println("2. Email");
			System.out.println("3. Both");
			System.out.println("4. Cancel");
			System.out.print("Enter your choice: ");

			int updateChoice;
			try {
				updateChoice = Integer.parseInt(scanner.nextLine().trim());
				switch (updateChoice) {
				case 1:
					newPreference = "SMS";
					break;
				case 2:
					newPreference = "Email";
					break;
				case 3:
					newPreference = "Both";
					break;
				case 4:
					System.out.println("Update canceled.");
					return false;
				default:
					System.out.println("Invalid choice.");
					return false;
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input.");
				return false;
			}

		} else if (actionChoice == 2) {
			// ----- Remove Flow -----
			System.out.println("\nWhich notification do you want to remove?");
			System.out.println("1. SMS");
			System.out.println("2. Email");
			System.out.println("3. Both");
			System.out.println("4. Cancel");
			System.out.print("Enter your choice: ");

			int removeChoice;
			try {
				removeChoice = Integer.parseInt(scanner.nextLine().trim());
				switch (removeChoice) {
				case 1:
					newPreference = currentPreference.equals("Both") ? "Email" : "None";
					break;
				case 2:
					newPreference = currentPreference.equals("Both") ? "SMS" : "None";
					break;
				case 3:
					newPreference = "None";
					break;
				case 4:
					System.out.println("Remove canceled.");
					return false;
				default:
					System.out.println("Invalid choice.");
					return false;
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input.");
				return false;
			}

		} else {
			System.out.println("Canceled.");
			return false;
		}

		// Step 4: Update database
		boolean updated = notificationService.updateNotificationPreference(studentId, newPreference);
		if (updated) {
			System.out.println("Notification preference changed to: " + newPreference);
		} else {
			System.out.println("Failed to update notification preference.");
		}

		return updated;
	}

}
