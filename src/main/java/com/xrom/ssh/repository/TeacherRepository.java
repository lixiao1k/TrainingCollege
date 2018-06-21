package com.xrom.ssh.repository;

import com.xrom.ssh.controller.TestController;
import com.xrom.ssh.entity.ITeacherA;
import com.xrom.ssh.entity.Teacher;

import java.util.List;

public interface TeacherRepository extends DomainRepository<Teacher,Long> {
    List<Teacher> findTeachersOfInstitution(String institutionCode);
    ITeacherA getTeacherA(Long tid);
}
