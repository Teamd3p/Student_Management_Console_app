package com.tss.service;


import com.tss.dao.SubjectCourseDao;
import com.tss.model.SubjectCourse;

public class SubjectCourseService {

    private SubjectCourseDao subjectCourseDao;

    public SubjectCourseService() {
        this.subjectCourseDao = new SubjectCourseDao();
    }

    public boolean addSubjectToCourse(int courseId, int subjectId) {
        SubjectCourse courseSubject = new SubjectCourse();
        courseSubject.setCourseId(courseId);
        courseSubject.setSubjectId(subjectId);
        return subjectCourseDao.insertCourseSubject(courseSubject);
    }
}
