package com.xrom.ssh.repository;

import com.xrom.ssh.entity.Grade;

import java.util.List;

public interface GradeRepository extends DomainRepository<Grade,Long> {
    Grade get(Long sid, Long cid);
    List<Grade> findAll(Long cid);
    void updateSOrderGradeA(Grade grade);
}
