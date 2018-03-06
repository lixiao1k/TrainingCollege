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
    private RegisterApplicationService registerApplicationService;

    @Autowired(required = true)
    private ClassroomService classroomService;


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

    @RequestMapping(value = "/createClass", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String saveClass() throws ParseException {
        Classroom classroom = new Classroom();
        classroom.setTeacherId(1L);
        classroom.setCourseId(1L);
        classroom.setStudentNumPlan(50);
        classroom.setStudentNumNow(48);
        classroomService.saveClass(classroom);
        classroomService.flush();
        return "success!";
    }

    @RequestMapping(value = "/getClass", method = RequestMethod.GET)
    @ResponseBody
    public String getClassroom() {
        System.out.println(classroomService.getClass(1L).getStudentNumPlan());
        return "success!";
    }

    @RequestMapping(value = "/getAllClass", method = RequestMethod.GET)
    @ResponseBody
    public String getClassList() {
        List<Classroom> classrooms = classroomService.findAll();
        for(Classroom classroom : classrooms){
            System.out.println(classroom.getStudentNumNow());
        }
        return "success!";
    }


    @RequestMapping(value = "/getClassByCode", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getClassByCode() {
        List<Classroom> classrooms = classroomService.findAll(1L);
        for(Classroom classroom : classrooms){
            System.out.println(classroom.getStudentNumPlan());
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
