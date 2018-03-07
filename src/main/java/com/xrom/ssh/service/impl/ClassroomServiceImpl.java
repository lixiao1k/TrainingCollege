package com.xrom.ssh.service.impl;

import com.xrom.ssh.entity.Classroom;
import com.xrom.ssh.repository.ClassroomRepository;
import com.xrom.ssh.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomServiceImpl implements ClassroomService {
    @Autowired(required = true)
    private ClassroomRepository repository;

    @Override
    public Long saveClass(Classroom classroom) {
        return repository.save(classroom);
    }

    @Override
    public void deleteClass(Long id) {
        repository.delete(id);
        flush();
    }

    @Override
    public Classroom getClass(Long id) {
        return repository.get(id);
    }

    @Override
    public void flush() {
        repository.flush();
    }

    @Override
    public List<Classroom> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Classroom> findAll(Long couserId) {
        return repository.findAll(couserId);
    }

    @Override
    public void updateNumPlan(Long id, int change) {
        Classroom classroom = getClass(id);
        classroom.setStudentNumPlan(change);
        repository.update(classroom);
        flush();
    }

    @Override
    public void updateNumNow(Long id, int change) {
        Classroom classroom = getClass(id);
        classroom.setStudentNumNow(classroom.getStudentNumNow() + change);
        repository.update(classroom);
        flush();
    }

    @Override
    public Long createClass(Long courseId, int studentNumPlan, int studentNumNow, Long teacherId) {
        Classroom classroom = new Classroom();
        classroom.setCourseId(courseId);
        classroom.setStudentNumPlan(studentNumPlan);
        classroom.setStudentNumNow(studentNumNow);
        classroom.setTeacherId(teacherId);
        Long id = saveClass(classroom);
        flush();
        return id;
    }
}
