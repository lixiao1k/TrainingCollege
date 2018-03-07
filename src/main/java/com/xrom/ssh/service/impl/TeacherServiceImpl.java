package com.xrom.ssh.service.impl;

import com.sun.security.auth.callback.TextCallbackHandler;
import com.xrom.ssh.entity.Teacher;
import com.xrom.ssh.repository.TeacherRepository;
import com.xrom.ssh.repository.impl.TeacherRepositoryImpl;
import com.xrom.ssh.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired(required = true)
    private TeacherRepository teacherRepository;

    @Override
    public Long saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public void deleteTeacher(Long id) {
        teacherRepository.delete(id);
        flush();
    }

    @Override
    public Teacher getTeacher(Long id) {
        return teacherRepository.get(id);
    }

    @Override
    public void updateName(Long id, String name) {
        Teacher teacher = getTeacher(id);
        teacher.setName(name);
        teacherRepository.update(teacher);
        flush();
    }

    @Override
    public void updatePhone(Long id, String phone) {
        Teacher teacher = getTeacher(id);
        teacher.setPhone(phone);
        teacherRepository.update(teacher);
        flush();
    }

    @Override
    public void updateInstitution(Long id, String institution_code) {
        Teacher teacher = getTeacher(id);
        teacher.setInstitutionCode(institution_code);
        teacherRepository.update(teacher);
        flush();
    }

    @Override
    public List<Teacher> findTeachersOfInstitution(String institution_code) {
        return teacherRepository.findTeachersOfInstitution(institution_code);
    }

    @Override
    public void flush() {
        teacherRepository.flush();
    }

    @Override
    public void createTeacher(String name, String phone, String type, String institutionCode) {
        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setPhone(phone);
        teacher.setTeachingType(type);
        teacher.setInstitutionCode(institutionCode);
        saveTeacher(teacher);
    }
}
