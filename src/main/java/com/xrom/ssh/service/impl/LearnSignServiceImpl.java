package com.xrom.ssh.service.impl;

import com.xrom.ssh.entity.LearnSign;
import com.xrom.ssh.exceptions.LearnSignExistException;
import com.xrom.ssh.repository.LearnSignRepository;
import com.xrom.ssh.service.LearnSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LearnSignServiceImpl implements LearnSignService {
    @Autowired(required = true)
    LearnSignRepository repository;


    @Override
    public void createSign(LearnSign sign) {
        repository.save(sign);
        flush();
    }

    @Override
    public void flush() {
        repository.flush();
    }

    @Override
    public List<LearnSign> findAll(Long cid) {
        return repository.findAll(cid);
    }

    @Override
    public List<LearnSign> findAll(Long sid, Long cid) {
        return repository.findAll(sid, cid);
    }

    @Override
    public LearnSign get(Long sid, Long cid, int week) {
        return repository.get(sid, cid, week);
    }

    @Override
    public void createSign(Long sid, Long cid, int week){
        LearnSign sign = get(sid, cid, week);
        if(sign == null){
            sign = new LearnSign();
            sign.setStudentId(sid);
            sign.setClassId(cid);
            sign.setWeek(week);
            createSign(sign);
        }
    }
}
