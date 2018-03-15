package com.xrom.ssh.service.impl;

import com.xrom.ssh.entity.Classroom;
import com.xrom.ssh.entity.Course;
import com.xrom.ssh.entity.Order;
import com.xrom.ssh.entity.vo.OrderVO;
import com.xrom.ssh.entity.vo.SClassroomVO;
import com.xrom.ssh.enums.OrderState;
import com.xrom.ssh.repository.ClassroomRepository;
import com.xrom.ssh.service.ClassroomService;
import com.xrom.ssh.service.CourseService;
import com.xrom.ssh.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassroomServiceImpl implements ClassroomService {
    @Autowired(required = true)
    private ClassroomRepository repository;

    @Autowired(required = true)
    private CourseService courseService;

    @Autowired(required = true)
    private OrderService orderService;

    @Autowired(required = true)
    private ClassroomService classroomService;

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

    @Override
    public List<SClassroomVO> toSClassroomVO(List<Classroom> classrooms) {
        List<SClassroomVO> sClassroomVOS = new ArrayList<>();
        SClassroomVO sClassroomVO = null;
        for (Classroom classroom : classrooms){
            sClassroomVO = new SClassroomVO();
            Course course = courseService.getCourse(classroom.getCourseId());
            if (course != null && classroom.getStudentNumPlan() != 0){
                int price = course.getPrice();
                //定价策略，单价*课时/（班级人数/20）
                int priceTotal = (int)(price * course.getWeeks() * course.getHourPerWeek() / (classroom.getStudentNumPlan() / 20.0));
                sClassroomVO.setPriceTotal(priceTotal);
            }
            sClassroomVO.setCourseId(classroom.getCourseId());
            sClassroomVO.setId(classroom.getId());
            sClassroomVO.setStudentNumNow(classroom.getStudentNumNow());
            sClassroomVO.setStudentNumPlan(classroom.getStudentNumPlan());
            sClassroomVO.setTeacherId(classroom.getTeacherId());
            sClassroomVOS.add(sClassroomVO);
        }
        return sClassroomVOS;
    }

    @Override
    public Classroom getClassroom(Long sid, Long cid) {
        List<OrderVO> orderVOS = orderService.getAllOfStudentByState(sid, OrderState.PAYED);
        OrderVO result = null;
        for (OrderVO orderVO : orderVOS){
            if(orderVO.getCourseId() == cid){
                result = orderVO;
            }
        }
        if(result != null){
            Classroom classroom = classroomService.getClass(result.getClassId());
            return classroom;
        }else {
            return null;
        }
    }
}
