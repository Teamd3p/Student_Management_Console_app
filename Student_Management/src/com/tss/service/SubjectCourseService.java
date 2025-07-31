package com.tss.service;


import java.util.List;

import com.tss.dao.SubjectCourseDao;
import com.tss.model.Subject;
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
    
    public List<Subject> viewSubjectsOfCourse(int courseId) {
        return subjectCourseDao.getSubjectsByCourseId(courseId);
    }

    public boolean checkIfSubjectAlreadyExistsInCourse(int subjectId, int courseId) {
        return subjectCourseDao.checkIfSubjectAlreadyExistsInCourse(subjectId, courseId);
    }

}
