package com.xrom.ssh.repository;

import com.xrom.ssh.entity.Course;
import com.xrom.ssh.entity.ICourseA;
import com.xrom.ssh.entity.ICourseTypeA;

import java.util.List;

public interface CourseRepository extends DomainRepository<Course,Long> {
    List<Course> findAllByType(String type);
    List<Course> findAll(String institution);
    ICourseTypeA getICourseTypeA(String code);
    ICourseA getICourseA(Long courseId);
}
