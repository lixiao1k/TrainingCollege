package com.xrom.ssh.service;

import com.xrom.ssh.entity.Student;
import com.xrom.ssh.exceptions.RepeatInsertException;

import java.util.List;

public interface StudentService {
    Long saveStudent(Student student) throws RepeatInsertException;
    void delete(Long id);
    List<Student> getAllStudents();
    void update(Student student);
    Student getStudent(Long id);
}
