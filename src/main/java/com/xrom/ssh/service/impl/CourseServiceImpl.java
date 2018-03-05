package com.xrom.ssh.service.impl;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.xrom.ssh.entity.Course;
import com.xrom.ssh.repository.CourseRepository;
import com.xrom.ssh.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired(required = true)
    private CourseRepository courseRepository;

    @Override
    public Long saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.delete(id);
        flush();
    }

    @Override
    public Course getCourse(Long id) {
        return courseRepository.get(id);
    }

    @Override
    public void flush() {
        courseRepository.flush();
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    private List<Course> getUnderWay(List<Course> courses, Boolean isUnderWay){
        List<Course> underWayCourses = new ArrayList<>();
        List<Course> closedCourses = new ArrayList<>();
        Date now = new Date();
        if(courses == null){
            return null;
        }
        for(Course course : courses){
            if(course.getEndDate().before(now)){
                closedCourses.add(course);
            }else {
                underWayCourses.add(course);
            }
        }
        if(isUnderWay){
            return underWayCourses;
        }else {
            return closedCourses;
        }


    }

    @Override
    public List<Course> findAll(Boolean isUnderWay) {
        return getUnderWay(findAll(), isUnderWay);
    }

    @Override
    public List<Course> findAll(String institutionCode) {
        return courseRepository.findAll(institutionCode);
    }

    @Override
    public List<Course> findAll(String institutionCode, Boolean isUnderWay) {
        return getUnderWay(findAll(institutionCode), isUnderWay);
    }

    @Override
    public List<Course> findAllByType(String type) {
        return courseRepository.findAllByType(type);
    }
}
