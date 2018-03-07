package com.xrom.ssh.repository;

import com.xrom.ssh.entity.Student;

public interface StudentRepository extends DomainRepository<Student,Long> {
    Student getStudent(String mail);
    Student getStudent(String mail, String password);
}
