package com.xrom.ssh.repository;

import com.xrom.ssh.entity.Course;

import java.util.List;

public interface CourseRepository extends DomainRepository<Course,Long> {
    List<Course> findAllByType(String type);
    List<Course> findAll(String institution);
}
