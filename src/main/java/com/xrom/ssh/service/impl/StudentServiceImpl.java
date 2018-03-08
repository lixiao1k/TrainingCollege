package com.xrom.ssh.service.impl;

import com.xrom.ssh.entity.Account;
import com.xrom.ssh.entity.Student;
import com.xrom.ssh.exceptions.NotValidatedUserException;
import com.xrom.ssh.exceptions.RepeatInsertException;
import com.xrom.ssh.exceptions.SignInFailedException;
import com.xrom.ssh.exceptions.UsedMailException;
import com.xrom.ssh.repository.StudentRepository;
import com.xrom.ssh.service.AccountService;
import com.xrom.ssh.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired(required = true)
    private StudentRepository studentRepository;

    @Autowired(required = true)
    private AccountService accountService;

    @Override
    public Long saveStudent(Student student) throws RepeatInsertException {
        Long id = -1L;
        try {
            id = studentRepository.save(student);
        }catch (Exception e){
            throw new RepeatInsertException();
        }
        return id;
    }

    @Override
    public Long register(String username, String mail, String password) throws UsedMailException {
        Student student = getStudent(mail);
        if(student != null){
            throw new UsedMailException();
        }else{
            student = new Student();
            student.setPassword(password);
            student.setUserName(username);
            student.setEmail(mail);
            return studentRepository.save(student);
        }
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

    @Override
    public Student getStudent(String mail) {
        return studentRepository.getStudent(mail);
    }

    @Override
    public Student getStudent(String mail, String password) {
        return studentRepository.getStudent(mail, password);
    }

    @Override
    public void flush() {
        studentRepository.flush();
    }

    @Override
    public void validate(String mail) {
        Student student = getStudent(mail);
        student.setUserState(1);
        update(student);
        flush();
        System.out.println("Yes");
        accountService.createAccount(student.getId());
    }

    @Override
    public void cancel(String mail) {
        Student student = getStudent(mail);
        student.setUserState(2);
        update(student);
        flush();
    }

    @Override
    public int getLevel(String mail) {
        int[] levelValue = new int[]{1, 1000, 3000, 10000, 20000, 50000, 100000, 200000, Integer.MAX_VALUE};
        Student student = getStudent(mail);
        int consumption = 0;
        try {
            consumption = accountService.getConsumption(student.getId());
        } catch (NotValidatedUserException e) {
            return 0;
        }
        int level = 0;
        while (consumption >= levelValue[level]){
            level++;
        }

        return level;
    }

    @Override
    public Student signIn(String mail, String password) throws SignInFailedException {
        Student student = getStudent(mail, password);
        if(student == null){
            throw new SignInFailedException();
        }else {
            return student;
        }
    }

}
