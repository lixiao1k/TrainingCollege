package com.xrom.ssh.service;

import com.xrom.ssh.entity.Teacher;

import java.util.List;

public interface TeacherService {
    Long saveTeacher(Teacher teacher);
    void deleteTeacher(Long id);
    Teacher getTeacher(Long id);
    void updateName(Long id, String name);
    void updatePhone(Long id, String phone);
    void updateInstitution(Long id, String institution_code);
    List<Teacher> findTeachersOfInstitution(String institution_code);
    void flush();
}
