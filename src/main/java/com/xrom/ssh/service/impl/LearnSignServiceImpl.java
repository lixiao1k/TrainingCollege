package com.xrom.ssh.service.impl;

import com.xrom.ssh.entity.LearnSign;
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
}
