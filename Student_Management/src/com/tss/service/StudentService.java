package com.tss.service;

import java.util.List;
import com.tss.dao.StudentDao;
import com.tss.model.Student;

public class StudentService {

    private StudentDao studentDao;

    public StudentService() {
        this.studentDao = new StudentDao();
    }

    public List<Student> readAllStudent() {
        return studentDao.readAllStudents();
    }

    public boolean insertStudent(Student student) {
        if (student.getStudentName() == null || student.getStudentName().trim().isEmpty()) {
            System.out.println("Error: Student name cannot be null or empty.");
            return false;
        }

        if (student.getAdmission() == null) {
            student.setAdmission(java.time.LocalDateTime.now());
        }

        return studentDao.insertStudent(student);
    }
}
