package com.tss.model;


public class SubjectCourse {
    private int courseSubjectId;
    private int subjectId;
    private int courseId;

    public SubjectCourse() {
    }

    public SubjectCourse(int courseSubjectId, int subjectId, int courseId) {
        this.courseSubjectId = courseSubjectId;
        this.subjectId = subjectId;
        this.courseId = courseId;
    }

    public int getCourseSubjectId() {
        return courseSubjectId;
    }

    public void setCourseSubjectId(int courseSubjectId) {
        this.courseSubjectId = courseSubjectId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "CourseSubject{" +
                "courseSubjectId=" + courseSubjectId +
                ", subjectId=" + subjectId +
                ", courseId=" + courseId +
                '}';
    }
}
