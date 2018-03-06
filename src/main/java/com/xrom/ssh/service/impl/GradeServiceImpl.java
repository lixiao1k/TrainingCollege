package com.xrom.ssh.service.impl;

import com.xrom.ssh.entity.Grade;
import com.xrom.ssh.repository.GradeRepository;
import com.xrom.ssh.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GradeServiceImpl implements GradeService {

    @Autowired(required = true)
    private GradeRepository repository;

    @Override
    public Grade get(Long sid, Long cid) {
        return repository.get(sid, cid);
    }

    @Override
    public void createGrade(Grade entity) {
        repository.save(entity);
        flush();
    }

    @Override
    public int getGrade(Long sid, Long cid) {
        Grade grade = get(sid, cid);
        if(grade == null){
            return 0;
        }else {
            return grade.getGrade();
        }
    }

    @Override
    public void flush() {
        repository.flush();
    }
}
