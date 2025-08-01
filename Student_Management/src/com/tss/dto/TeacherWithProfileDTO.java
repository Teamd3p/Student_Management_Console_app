package com.tss.dto;


import com.tss.model.Teacher;
import com.tss.model.Profile;

public class TeacherWithProfileDTO {
    private Teacher teacher;
    private Profile profile;

    public TeacherWithProfileDTO(Teacher teacher, Profile profile) {
        this.teacher = teacher;
        this.profile = profile;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return teacher.toString() + " " + profile.toString();
    }
}
