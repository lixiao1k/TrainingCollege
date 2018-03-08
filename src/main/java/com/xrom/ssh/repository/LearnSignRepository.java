package com.xrom.ssh.repository;

import com.xrom.ssh.entity.LearnSign;

import java.util.List;

public interface LearnSignRepository extends DomainRepository<LearnSign,Long> {
    List<LearnSign> findAll(Long cid);
    List<LearnSign> findAll(Long sid, Long cid);
    LearnSign get(Long sid, Long cid);
}
