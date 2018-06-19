package com.xrom.ssh.repository;

import com.xrom.ssh.entity.Classroom;

import java.util.List;

public interface ClassroomRepository extends DomainRepository<Classroom,Long>{
    List<Classroom> findAll(Long courseId);
    void addClassUpdateA(Classroom classroom);
}
