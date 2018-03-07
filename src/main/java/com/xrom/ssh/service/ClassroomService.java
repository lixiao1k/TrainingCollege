package com.xrom.ssh.service;

import com.xrom.ssh.entity.Classroom;

import java.util.List;

public interface ClassroomService {
    Long saveClass(Classroom classroom);
    void deleteClass(Long id);
    Classroom getClass(Long id);
    void flush();
    List<Classroom> findAll();
    List<Classroom> findAll(Long courseId);
    void updateNumPlan(Long id, int newValue);
    void updateNumNow(Long id, int change);
    Long createClass(Long courseId, int studentNumPlan, int studentNumNow, Long teacherId);
}
