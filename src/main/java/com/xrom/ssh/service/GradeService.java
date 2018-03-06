package com.xrom.ssh.service;

import com.xrom.ssh.entity.Grade;

public interface GradeService {
    Grade get(Long sid, Long cid);
    void createGrade(Grade entity);
    int getGrade(Long sid, Long cid);
    void flush();
}
