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
    private ModifyApplicationService modifyApplicationService;

    @Autowired(required = true)
    private RegisterApplicationService registerApplicationService;


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

    @RequestMapping(value = "/createRegister", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String saveRegister() throws ParseException {
        RegisterApplication application = new RegisterApplication();
        application.setAddress("ShenZheng");
        application.setDescription("Goal is to make you better and better");
        application.setPhone("10086");
        application.setCreated_time(new Date());
        registerApplicationService.saveApplication(application);
        registerApplicationService.flush();
        return "success!";
    }

    @RequestMapping(value = "/getRegister", method = RequestMethod.GET)
    @ResponseBody
    public String getRegister() {
        RegisterApplication application = registerApplicationService.getApplication(1L);
        System.out.println(application.getDescription());
        return "success!";
    }

    @RequestMapping(value = "/getAllRegister", method = RequestMethod.GET)
    @ResponseBody
    public String getRegisterList() {
        List<RegisterApplication> applications = registerApplicationService.findAll();
        for(RegisterApplication application : applications){
            System.out.println(application.getDescription());
        }
        return "success!";
    }


    @RequestMapping(value = "/getModifyReserved", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getRegisterReserved() {
        List<RegisterApplication> applications = registerApplicationService.findAll(ApplicationState.RESERVED);
        for(RegisterApplication application : applications){
            System.out.println(application.getDescription());
        }
        return "success!";
    }

    @RequestMapping(value = "/deleteRegister", method = RequestMethod.GET)
    @ResponseBody
    public String deleteRegister() {
        registerApplicationService.deleteApplication(1L);
        return "success!";
    }

    @RequestMapping(value = "/reject", method = RequestMethod.GET)
    @ResponseBody
    public String reject() {
        registerApplicationService.reject(2L);
        return "success!";
    }

    @RequestMapping(value = "/agree", method = RequestMethod.GET)
    @ResponseBody
    public String agree() {
        registerApplicationService.agree(2L);
        return "success!";
    }

}
