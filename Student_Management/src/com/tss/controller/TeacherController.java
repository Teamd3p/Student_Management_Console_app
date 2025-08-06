package com.tss.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import com.tss.exception.ValidationException;
import com.tss.model.Profile;
import com.tss.model.Subject;
import com.tss.model.Teacher;
import com.tss.service.ProfileService;
import com.tss.service.TeacherService;
import com.tss.util.InputValidator;

public class TeacherController {

    private final TeacherService teacherService = new TeacherService();
    private final ProfileService profileService = new ProfileService();
    private final SubjectController subjectController = new SubjectController();
    private final Scanner scanner = new Scanner(System.in);

    public void displayAllTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        List<Profile> profiles = profileService.readAllProfiles("Teacher");

        if (teachers.isEmpty()) {
            System.out.println("No Teacher Found.");
            return;
        }

        System.out.println("+-----------------------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("|                                                         TEACHER RECORDS                                                                             |");
        System.out.println("+-----------------------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.printf("| %-10s | %-20s | %-6s | %-15s | %-25s | %-20s | %-5s | %-25s |\n", "Teacher ID", "Name", "Active", "Phone", "Email", "Address", "Age", "Joining Date");
        System.out.println("+-----------------------------------------------------------------------------------------------------------------------------------------------------+");

        for (Teacher teacher : teachers) {
            Profile matchedProfile = profiles.stream()
                    .filter(p -> p.getUserId() == teacher.getTeacherId())
                    .findFirst().orElse(null);

            String phone = matchedProfile != null ? matchedProfile.getPhoneNumber() : "N/A";
            String email = matchedProfile != null ? matchedProfile.getEmail() : "N/A";
            String address = matchedProfile != null ? matchedProfile.getAddress() : "N/A";
            int age = matchedProfile != null ? matchedProfile.getAge() : 0;

            System.out.printf("| %-10d | %-20s | %-6s | %-15s | %-25s | %-20s | %-5d | %-25s |\n",
                    teacher.getTeacherId(), teacher.getTeacherName(), teacher.isActive() ? "Yes" : "No",
                    phone, email, address, age, teacher.getJoiningDate());
        }

        System.out.println("+-----------------------------------------------------------------------------------------------------------------------------------------------------+");
    }

    public void addTeacher() {
        try {
            Teacher teacher = new Teacher();

            teacher.setTeacherName(InputValidator.readName("Enter Teacher Name: "));
            teacher.setActive(true);

            System.out.print("Enter Joining Date (yyyy-MM-dd HH:mm) or press Enter for now: ");
            String dateInput = scanner.nextLine().trim();
            LocalDateTime joiningDate = dateInput.isEmpty()
                    ? LocalDateTime.now()
                    : LocalDateTime.parse(dateInput.replace(" ", "T"));

            String formattedDate = joiningDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            teacher.setJoiningDate(formattedDate);

            boolean success = teacherService.addTeacher(teacher);
            if (!success) {
                System.out.println("Failed to add teacher.");
                return;
            }

            int teacherId = teacher.getTeacherId();
            boolean profileSuccess = false;

            while (!profileSuccess) {
                try {
                    Profile profile = new Profile();
                    profile.setUserType("teacher");
                    profile.setUserId(teacherId);
                 
                    while (true) {
                        try {
                            profile.setPhoneNumber(InputValidator.readPhone("Enter Phone Number: "));
                            break;
                        } catch (ValidationException ve) {
                            System.out.println("Invalid Phone: " + ve.getMessage());
                        }
                    }

                    while (true) {
                        try {
                            profile.setEmail(InputValidator.readEmail("Enter Email: "));
                            break;
                        } catch (ValidationException ve) {
                            System.out.println("Invalid Email: " + ve.getMessage());
                        }
                    }

                    while (true) {
                        try {
                            profile.setAddress(InputValidator.readAddress("Enter Address: "));
                            break;
                        } catch (ValidationException ve) {
                            System.out.println("Invalid Address: " + ve.getMessage());
                        }
                    }

                    while (true) {
                        try {
                            profile.setAge(InputValidator.readAge("Enter Age: "));
                            break;
                        } catch (ValidationException ve) {
                            System.out.println("Invalid Age: " + ve.getMessage());
                        }
                    }


                    profileSuccess = profileService.insertProfile(profile);

                    if (profileSuccess) {
                        System.out.println("Teacher and profile added successfully.");
                        displayAllTeachers();
                    } else {
                        System.out.println("Teacher added, but failed to add profile.");
                    }

                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }

        } catch (ValidationException ve) {
            System.out.println("Validation Error: " + ve.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void getTeacherById() {
        try {
            int id = InputValidator.readStudentId("Enter Teacher ID: ");
            Teacher teacher = teacherService.getByIdTeacher(id);
            System.out.println(teacher != null ? teacher : "Teacher not found.");
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        }
    }

    public boolean deleteTeacher() {
        try {
            int id = InputValidator.readStudentId("Enter Teacher ID to Delete: ");
            boolean updated = teacherService.deleteTeacher(id);
            System.out.println(updated ? "Teacher deleted successfully!" : "Delete failed.");
            return updated;
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
            return false;
        }
    }

    public boolean assignSubject() {
        try {
            int teacherId = InputValidator.readStudentId("Enter Teacher ID: ");
            System.out.println("Subjects Table");
            subjectController.readAllSubjects();

            int subjectId = InputValidator.readStudentId("Enter Subject ID: ");
            boolean assigned = teacherService.assignSubject(teacherId, subjectId);

            System.out.println(assigned ? "Subject assigned to teacher successfully!" : "Assignment failed.");
            return assigned;
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
            return false;
        }
    }

    public void removeSubject() {
        try {
            int teacherId = InputValidator.readStudentId("Enter Teacher ID: ");
            if (!readAllSubjectsOfTeacher(teacherId)) return;

            int subjectId = InputValidator.readStudentId("Enter Subject ID to remove: ");
            boolean removed = teacherService.removeSubject(teacherId, subjectId);

            System.out.println(removed ? "Subject removed from teacher successfully!" : "Remove operation failed.");
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        }
    }

    public boolean readAllSubjectsOfTeacher(int teacherId) {
        List<Subject> assignedSubjects = teacherService.readTeacherSubjectById(teacherId);
        if (assignedSubjects.isEmpty()) {
            System.out.println("No subject assigned to this teacher.");
            return false;
        }

        System.out.println("+------------+----------------------+");
        System.out.printf("| %-10s | %-20s |\n", "Subject ID", "Subject Name");
        System.out.println("+------------+----------------------+");

        for (Subject subject : assignedSubjects) {
            System.out.printf("| %-10d | %-20s |\n", subject.getSubjectId(), subject.getSubjectName());
        }

        System.out.println("+------------+----------------------+");
        return true;
    }
}
