package com.xrom.ssh.service;

import com.xrom.ssh.entity.ICourseSignA;
import com.xrom.ssh.entity.LearnSign;
import com.xrom.ssh.exceptions.LearnSignExistException;

import java.util.List;

public interface LearnSignService {
    void createSign(LearnSign sign);

    void flush();

    List<LearnSign> findAll(Long cid);

    List<LearnSign> findAll(Long sid, Long cid);

    LearnSign get(Long sid, Long cid, int week);

    void createSign(Long sid, Long cid, int week);

    List<ICourseSignA> getICourseSignA(Long courseId);
}
