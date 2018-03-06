package com.xrom.ssh.controller;

import com.xrom.ssh.entity.*;
import com.xrom.ssh.enums.ApplicationState;
import com.xrom.ssh.exceptions.RepeatInsertException;
import com.xrom.ssh.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class TestController {

    @Autowired(required=true)
    private PersonService personService;

    @Autowired(required = true)
    private StudentService studentService;

    @Autowired(required = true)
    private CourseService courseService;

    @Autowired(required = true)
    private ClassroomService classroomService;

    @Autowired(required = true)
    private GradeService gradeService;

    @Autowired(required = true)
    private LearnSignService learnSignService;


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "test";
    }

    @RequestMapping(value = "/savePerson", method = RequestMethod.GET)
    @ResponseBody
    public String savePerson() {
        System.out.print("here");
        personService.savePerson();
        return "success!";
    }


    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.GET)
    @ResponseBody
    public String saveOrUpdate() {
        Student student = new Student();
        student.setEmail("lixiao1k@163.com");
        student.setUserName("mike");
        student.setId(2L);
        studentService.update(student);
        return "success!";
    }

    @RequestMapping(value = "/getCourses", method = RequestMethod.GET)
    @ResponseBody
    public String getCourseList() {
        List<Course> courses = courseService.findAll();
        for (Course course : courses){
            System.out.println(course.getDescription());
        }
        return "success!";
    }

    @RequestMapping(value = "/createSign", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String saveSign() throws ParseException {
        LearnSign sign = new LearnSign();
        sign.setStudentId(2L);
        sign.setClassId(2L);
        sign.setDate(new Date());
        learnSignService.createSign(sign);
        return "success!";
    }

    @RequestMapping(value = "/getAllSigns", method = RequestMethod.GET)
    @ResponseBody
    public String getSignList() {
        List<LearnSign> signs = learnSignService.findAll(2L);
        for(LearnSign sign : signs){
            System.out.println(sign.getDate());
        }
        return "success!";
    }


    @RequestMapping(value = "/getSigns", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getClassByCode() {
        List<LearnSign> signs = learnSignService.findAll(1L, 2L);
        for(LearnSign sign : signs){
            System.out.println(sign.getDate());
        }
        return "success!";
    }

    @RequestMapping(value = "/deleteClass", method = RequestMethod.GET)
    @ResponseBody
    public String deleteClass() {
        classroomService.deleteClass(1L);
        return "success!";
    }
}
