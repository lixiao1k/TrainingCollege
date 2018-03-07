package com.xrom.ssh.service;

import com.xrom.ssh.entity.Student;
import com.xrom.ssh.exceptions.RepeatInsertException;
import com.xrom.ssh.exceptions.SignInFailedException;
import com.xrom.ssh.exceptions.UsedMailException;

import java.util.List;

public interface StudentService {
    Long saveStudent(Student student) throws RepeatInsertException;
    Long register(String username, String mail, String password) throws UsedMailException;
    void delete(Long id);
    List<Student> getAllStudents();
    void update(Student student);
    Student getStudent(Long id);
    Student getStudent(String mail);
    Student getStudent(String mail, String password);
    void flush();
    void validate(String mail);
    void cancel(String mail);
    int getLevel(String mail);
    Student signIn(String mail, String password) throws SignInFailedException;
}
