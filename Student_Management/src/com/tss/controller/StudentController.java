package com.tss.controller;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.tss.exception.ValidationException;
import com.tss.model.Fees;
import com.tss.model.Profile;
import com.tss.model.Student;
import com.tss.service.ProfileService;
import com.tss.service.StudentService;

public class StudentController {

	private StudentService studentService;
	private ProfileService profileService;
<<<<<<< HEAD
	private FeeController feecontroller;
	private StudentCourseController studentCourseController;
=======
	private StudentCourseController studentCourseController;
	private FeeController feesController;
	private StudentController studentController;
>>>>>>> 53634cfc15739c05d340928f1a2c3c3f7fea0722
	private Scanner scanner = new Scanner(System.in);

	public StudentController() {
	    this.studentService = new StudentService();
	    this.profileService = new ProfileService();
	    this.studentController = studentController;
	    this.feesController = new FeeController(); 
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
		if(student != null)
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
	}

<<<<<<< HEAD
	public void showAllCoursesById() throws ValidationException {
=======
//	public void payStudentFees() {
//		// showed student records
//	    System.out.print("Enter Student ID to search: ");
//	    int id = scanner.nextInt();
//	    scanner.nextLine();
//	    studentCourseController  = new StudentCourseController();
//	    studentCourseController.viewCourseByStudentId(id);
//
//	    System.out.print("Enter Course ID to pay fee: ");
//	    int courseId = Integer.parseInt(scanner.nextLine().trim());
//
//	    // Loop for selecting payment method
//	    while (true) {
//	        System.out.println("\nSelect Payment Method:");
//	        System.out.println("1. Cash");
//	        System.out.println("2. UPI");
//	        System.out.println("3. Card");
//	        System.out.println("4. Exit");
//	        System.out.print("Enter your choice: ");
//
//	        int choice = Integer.parseInt(scanner.nextLine().trim());
//
//	        String paymentType = null;
//	        
//	        double amountToPay = 0.0;
//
//	        switch (choice) {
//	            case 1:
//	                paymentType = "cash";
//	                break;
//	            case 2:
//	                paymentType = "upi";
//	                break;
//	            case 3:
//	                paymentType = "card";
//	                break;
//	            case 4:
//	                System.out.println("Exiting payment menu...");
//	                return;
//	            default:
//	                System.out.println("Invalid choice! Please try again.");
//	                continue;
//	        }
//
//	        // Prompt for amount only after valid payment type is selected
//	        System.out.print("Enter amount to pay: ₹");
//	        try {
//	            amountToPay = Double.parseDouble(scanner.nextLine().trim());
//	        } catch (NumberFormatException e) {
//	            System.out.println("Invalid amount entered. Try again.");
//	            continue; // loop again
//	        }
//
//	        // Call controller/service to process payment
//	        feesController.processFeePayment(id, courseId, amountToPay, paymentType);
//
//	        break;
//	    }
//	}
	public void payStudentFees() {
	    System.out.print("Enter Student ID: ");
	    int studentId = Integer.parseInt(scanner.nextLine().trim());

	    // Initialize studentCourseController only once
	    if (studentCourseController == null) {
	        studentCourseController = new StudentCourseController();
	    }

	    // Step 1: Display enrolled courses
	    List<Fees> enrolledCourses = studentCourseController.getEnrolledCoursesByStudentId(studentId);

	    if (enrolledCourses == null || enrolledCourses.isEmpty()) {
	        System.out.println("No courses found for the student.");
	        return;
	    }

	    System.out.println("\n+--------------------------------------------------------------+");
	    System.out.println("|                  Enrolled Courses                            |");
	    System.out.println("+--------------------------------------------------------------+");
	    System.out.printf("| %-10s | %-25s | %-10s | %-10s |\n", "Course ID", "Course Name", "Paid (₹)", "Pending (₹)");
	    System.out.println("+--------------------------------------------------------------+");

	    for (Fees fee : enrolledCourses) {
	        System.out.printf("| %-10d | %-25s | %-10.2f | %-11.2f |\n",
	            fee.getCourseId(), fee.getCourseName(),
	            fee.getAmountPaid(), fee.getAmountPending());
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
	                break;
	            case 2:
	                paymentType = "UPI";
	                break;
	            case 3:
	                paymentType = "Card";
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

	    // Step 5: Enter amount
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
	        System.out.println("Payment successful!");
	    } else {
	        System.out.println("Payment failed.");
	    }
	}

	public void showAllCoursesById() throws ValidationException{
>>>>>>> 53634cfc15739c05d340928f1a2c3c3f7fea0722
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
<<<<<<< HEAD
=======

	
	

>>>>>>> 53634cfc15739c05d340928f1a2c3c3f7fea0722
}
