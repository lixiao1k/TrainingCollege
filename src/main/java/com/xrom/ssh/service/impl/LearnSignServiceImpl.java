package com.xrom.ssh.service.impl;

import com.xrom.ssh.entity.Course;
import com.xrom.ssh.entity.ICourseSignA;
import com.xrom.ssh.entity.LearnSign;
import com.xrom.ssh.exceptions.LearnSignExistException;
import com.xrom.ssh.repository.LearnSignRepository;
import com.xrom.ssh.service.CourseService;
import com.xrom.ssh.service.LearnSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class LearnSignServiceImpl implements LearnSignService {
    @Autowired(required = true)
    LearnSignRepository repository;

    @Autowired(required = true)
    CourseService courseService;


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


    @Override
    public List<ICourseSignA> getICourseSignA(Long courseId){
        Course course = courseService.getCourse(courseId);
        List<ICourseSignA> list = repository.getICourseSignA(courseId);
        if(list == null){
            return null;
        }else {
            Collections.sort(list, new Comparator<ICourseSignA>() {
                @Override
                public int compare(ICourseSignA o1, ICourseSignA o2) {
                    return o1.getWeek() - o2.getWeek();
                }
            });
        }
        int index = 0;
        List<ICourseSignA> list1 = new ArrayList<>();
        for(int i = 1; i <= course.getWeeks(); i++){
            if(index < list.size() && list.get(index).getWeek() == i){
                list1.add(list.get(index));
                index++;
            }else {
                ICourseSignA courseSignA = new ICourseSignA();
                courseSignA.setCourseId(courseId);
                courseSignA.setWeek(i);
                list1.add(courseSignA);
            }
        }
        return list1;
    }
}
