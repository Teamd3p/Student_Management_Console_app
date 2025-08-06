package com.tss.util;

import java.util.Scanner;

import com.tss.exception.ValidationException;

public class InputValidator {

    private static final Scanner scanner = new Scanner(System.in);

    public static String readName(String prompt) throws ValidationException {
        System.out.print(prompt);
        String name = scanner.nextLine().trim();
        if (name == null || !name.contains(" ") || !name.matches("[a-zA-Z ]+")) {
            throw new ValidationException("Enter Proper Name (Only letters and space, full name required).");
        }
        return name;
    }

    public static String readPhone(String prompt) throws ValidationException {
        System.out.print(prompt);
        String phone = scanner.nextLine().trim();
        if (phone == null || !phone.matches("\\d{10}")) {
            throw new ValidationException("Phone number must be exactly 10 digits.");
        }
        return phone;
    }

    public static String readEmail(String prompt) throws ValidationException {
        System.out.print(prompt);
        String email = scanner.nextLine().trim();
        if (email == null || !email.matches("^\\S+@\\S+\\.\\S+$")) {
            throw new ValidationException("Invalid email format.");
        }
        return email;
    }

    public static String readAddress(String prompt) throws ValidationException {
        System.out.print(prompt);
        String address = scanner.nextLine().trim();
        if (address == null || address.isEmpty()) {
            throw new ValidationException("Address cannot be empty.");
        }
        return address;
    }

    public static int readAge(String prompt) throws ValidationException {
        System.out.print(prompt);
        String ageStr = scanner.nextLine().trim();
        if (!ageStr.matches("\\d+")) {
            throw new ValidationException("Age must be a positive integer.");
        }
        int age = Integer.parseInt(ageStr);
        if (age < 1 || age > 80) {
            throw new ValidationException("Age must be between 1 to 80.");
        }
        return age;
    }

    public static int readStudentId(String prompt) throws ValidationException {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (!input.matches("\\d+")) {
            throw new ValidationException("Student ID must be a positive number.");
        }
        int id = Integer.parseInt(input);
        if (id <= 0) {
            throw new ValidationException("Student ID must be greater than zero.");
        }
        return id;
    }

    public static int readChoice(String prompt) throws ValidationException {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (!input.matches("\\d+")) {
            throw new ValidationException("Input must be a number.");
        }
        int choice = Integer.parseInt(input);
        if (choice < 1 ) {
            throw new ValidationException("Please enter a valid option !!!");
        }
        return choice;
    }

	
}
