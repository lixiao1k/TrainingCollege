package com.xrom.ssh.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.xrom.ssh.entity.Course;

import java.util.Date;
import java.util.List;

public interface CourseService {
    Long saveCourse(Course course);
    void deleteCourse(Long id);
    Course getCourse(Long id);
    void flush();
    List<Course> findAll();
    List<Course> findAll(Boolean isUnderWay);
    List<Course> findAll(String institutionCode);
    List<Course> findAll(String institutionCode, Boolean isUnderWay);
    List<Course> findAllByType(String type);
    Long createCourse(Date begin, Date end, String type, String description,
                      int hourPerWeek, int weeks, String institutionCode, int price);
}
