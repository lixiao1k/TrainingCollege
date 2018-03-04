package com.xrom.ssh.service.impl;

import com.xrom.ssh.entity.Student;
import com.xrom.ssh.repository.StudentRepository;
import com.xrom.ssh.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired(required = true)
    private StudentRepository studentRepository;

    @Override
    public Long saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void delete(Long id) {
        studentRepository.delete(id);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public void update(Student student) {
        studentRepository.update(student);
    }

    @Override
    public Student getStudent(Long id) {
        return studentRepository.get(id);
    }

}
