package com.tss.model;

import java.util.ArrayList;
import java.util.List;

public class Dashboard {

    private int srNo;
    private int studentId;
    private String studentName;

    private List<String> courseNames = new ArrayList<>();
    private List<Double> courseFees = new ArrayList<>();
    private List<Double> paidFees = new ArrayList<>();
    private List<Double> pendingFees = new ArrayList<>();

    private String subjects;
    private String teachers;

    // Totals
    private double totalFee;
    private double totalPaid;
    private double totalPending;

    public Dashboard(int srNo, int studentId, String studentName,
                     String courseNameCsv, String courseFeeCsv, String paidFeeCsv, String pendingFeeCsv,
                     String subjects, String teachers) {

        this.srNo = srNo;
        this.studentId = studentId;
        this.studentName = studentName;

        this.courseNames = parseCsvToStringList(courseNameCsv);
        this.courseFees = parseCsvToDoubleList(courseFeeCsv);
        this.paidFees = parseCsvToDoubleList(paidFeeCsv);
        this.pendingFees = parseCsvToDoubleList(pendingFeeCsv);

        this.totalFee = calculateTotal(this.courseFees);
        this.totalPaid = calculateTotal(this.paidFees);
        this.totalPending = calculateTotal(this.pendingFees);

        this.subjects = subjects;
        this.teachers = teachers;
    }

    // ----- CSV Parsing Methods -----

    private List<String> parseCsvToStringList(String csv) {
        List<String> list = new ArrayList<>();
        if (csv != null && !csv.isEmpty()) {
            for (String val : csv.split(",\\s*")) {
                list.add(val.trim());
            }
        }
        return list;
    }

    private List<Double> parseCsvToDoubleList(String csv) {
        List<Double> list = new ArrayList<>();
        if (csv != null && !csv.isEmpty()) {
            for (String val : csv.split(",\\s*")) {
                try {
                    list.add(Double.parseDouble(val.trim()));
                } catch (NumberFormatException e) {
                    list.add(0.0);
                }
            }
        }
        return list;
    }

    private double calculateTotal(List<Double> list) {
        return list.stream().mapToDouble(Double::doubleValue).sum();
    }

    // ----- Getters -----

    public int getSrNo() {
        return srNo;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public List<String> getCourseNames() {
        return courseNames;
    }

    public List<Double> getCourseFees() {
        return courseFees;
    }

    public List<Double> getPaidFees() {
        return paidFees;
    }

    public List<Double> getPendingFees() {
        return pendingFees;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public double getTotalPaid() {
        return totalPaid;
    }

    public double getTotalPending() {
        return totalPending;
    }

    public String getSubjects() {
        return subjects;
    }

    public String getTeachers() {
        return teachers;
    }
}
