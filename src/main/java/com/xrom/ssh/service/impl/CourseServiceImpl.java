package com.xrom.ssh.service.impl;

import com.xrom.ssh.entity.Course;
import com.xrom.ssh.entity.Grade;
import com.xrom.ssh.entity.ICourseA;
import com.xrom.ssh.entity.ICourseTypeA;
import com.xrom.ssh.entity.vo.OrderVO;
import com.xrom.ssh.entity.vo.SCourseVO;
import com.xrom.ssh.enums.OrderState;
import com.xrom.ssh.repository.CourseRepository;
import com.xrom.ssh.service.CourseService;
import com.xrom.ssh.service.GradeService;
import com.xrom.ssh.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired(required = true)
    private CourseRepository courseRepository;

    @Autowired(required = true)
    private OrderService orderService;

    @Autowired(required = true)
    private GradeService gradeService;

    @Override
    public Long saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.delete(id);
        flush();
    }

    @Override
    public Course getCourse(Long id) {
        return courseRepository.get(id);
    }

    @Override
    public void flush() {
        courseRepository.flush();
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    private List<Course> getUnderWay(List<Course> courses, Boolean isUnderWay){
        List<Course> underWayCourses = new ArrayList<>();
        List<Course> closedCourses = new ArrayList<>();
        Date now = new Date();
        if(courses == null){
            return null;
        }
        for(Course course : courses){
            if(course.getEndDate().before(now)){
                closedCourses.add(course);
            }else {
                underWayCourses.add(course);
            }
        }
        if(isUnderWay){
            return underWayCourses;
        }else {
            return closedCourses;
        }


    }

    @Override
    public List<Course> findAll(Boolean isUnderWay) {
        return getUnderWay(findAll(), isUnderWay);
    }

    @Override
    public List<Course> findAll(String institutionCode) {
        return courseRepository.findAll(institutionCode);
    }

    @Override
    public List<Course> findAll(String institutionCode, Boolean isUnderWay) {
        return getUnderWay(findAll(institutionCode), isUnderWay);
    }

    @Override
    public List<Course> findAllByType(String type) {
        return courseRepository.findAllByType(type);
    }

    @Override
    public Long createCourse(Date begin, Date end, String type, String description, int hourPerWeek, int weeks, String institutionCode, int price) {
        Course course = new Course();
        course.setBeginDate(begin);
        course.setEndDate(end);
        course.setType(type);
        course.setDescription(description);
        course.setHourPerWeek(hourPerWeek);
        course.setWeeks(weeks);
        course.setInstitutionCode(institutionCode);
        course.setPrice(price);
        Long id = saveCourse(course);
        flush();
        return id;
    }

    @Override
    public List<SCourseVO> findAllOfStudent(Boolean isUnderWay, Long studentId) {
        List<OrderVO> orderVOS = orderService.getAllOfStudentByState(studentId, OrderState.PAYED);
        List<SCourseVO> uCourse = new ArrayList<>(); // 进行中的课程列表
        List<SCourseVO> oCourse = new ArrayList<>(); // 已结课的课程列表
        SCourseVO scourseVO = null;
        Course course = null;
        if(orderVOS == null || orderVOS.size() == 0){
            return null;
        }
        for (OrderVO orderVO : orderVOS){
            System.out.println(orderVO);
            scourseVO = new SCourseVO();
            course = getCourse(orderVO.getCourseId());
            scourseVO.setBeginDate(course.getBeginDate());
            scourseVO.setCourseId(orderVO.getCourseId());
            scourseVO.setDescription(orderVO.getDescription());
            scourseVO.setEndDate(course.getEndDate());
            scourseVO.setHourPerWeek(course.getHourPerWeek());
            scourseVO.setPrice(course.getPrice());
            scourseVO.setType(course.getType());
            if(scourseVO.getEndDate().before(new Date())){
                Grade grade = gradeService.get(studentId, orderVO.getClassId());
                System.out.println(studentId);
                System.out.println(orderVO.getClassId());
                System.out.println();
                if(grade != null){
                    scourseVO.setGrade(grade.getGrade());
                }
                oCourse.add(scourseVO);
            }else {
                uCourse.add(scourseVO);
            }
        }
        if(isUnderWay == true){
            return uCourse;
        }else {
            return oCourse;
        }
    }


    @Override
    public ICourseTypeA getICourseTypeA(String code){
        return courseRepository.getICourseTypeA(code);
    }


    @Override
    public HashMap getICourseA(String code){
        List<Course> courses = courseRepository.findAll(code);
        List<ICourseA> iCourseAS = new ArrayList<>();
        for(Course course : courses){
            ICourseA iCourseA = courseRepository.getICourseA(course.getId());
            if(iCourseA == null){
                iCourseA = new ICourseA();
                iCourseA.setCourseId(course.getId());
                iCourseA.setInstitutionCode(course.getInstitutionCode());
                iCourseAS.add(iCourseA);
            }else {
                iCourseAS.add(iCourseA);
            }
        }
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("courses", courses);
        hashMap.put("iCourseAS", iCourseAS);
        return hashMap;
    }

    @Override
    public ICourseA getICourseA(Long courseId) {
        return courseRepository.getICourseA(courseId);
    }
}
