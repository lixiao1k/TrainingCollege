package com.xrom.ssh.service;

import com.xrom.ssh.entity.Grade;
import com.xrom.ssh.exceptions.GradeExistException;

public interface GradeService {
    Grade get(Long sid, Long cid);
    void createGrade(Grade entity);
    int getGrade(Long sid, Long cid);
    void createGrade(Long sid, Long cid, int gradeValue) throws GradeExistException;
    void flush();
}
