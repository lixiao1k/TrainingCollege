package com.xrom.ssh.service.impl;

import com.xrom.ssh.entity.Student;
import com.xrom.ssh.repository.StudentRepository;
import com.xrom.ssh.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired(required = true)
    private StudentRepository studentRepository;

    @Override
    public Long saveStudent() {
        Student student = new Student();
        student.setEmail("lixiao1k@163.com");
        student.setUserName("shelton");
        return studentRepository.save(student);
    }
}
