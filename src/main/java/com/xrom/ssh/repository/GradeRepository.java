package com.xrom.ssh.repository;

import com.xrom.ssh.entity.Grade;

public interface GradeRepository extends DomainRepository<Grade,Long> {
    Grade get(Long sid, Long cid);
}
