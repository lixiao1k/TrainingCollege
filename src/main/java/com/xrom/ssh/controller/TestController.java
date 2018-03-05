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
    private AccountService accountService;

    @Autowired(required = true)
    private TeacherService teacherService;

    @Autowired(required = true)
    private CourseService courseService;

    @Autowired(required = true)
    private ModifyApplicationService modifyApplicationService;


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

    @RequestMapping(value = "/createModify", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String saveModify() throws ParseException {
        ModifyApplication modifyApplication = new ModifyApplication();
        modifyApplication.setAddress("Nanjing,Jiangsu");
        modifyApplication.setCreateTime(new Date());
        modifyApplication.setDescription("Our goal is to .....");
        modifyApplication.setInstitutionCode("1234567");
        modifyApplication.setPhone("15951921161");
        modifyApplicationService.saveApplication(modifyApplication);
        modifyApplicationService.flush();
        return "success!";
    }

    @RequestMapping(value = "/getModify", method = RequestMethod.GET)
    @ResponseBody
    public String getModify() {
        ModifyApplication modifyApplication = modifyApplicationService.getApplication(1L);
        System.out.println(modifyApplication.getDescription());
        return "success!";
    }

    @RequestMapping(value = "/getAllModify", method = RequestMethod.GET)
    @ResponseBody
    public String getModifyList() {
        List<ModifyApplication> applications = modifyApplicationService.findAll();
        for(ModifyApplication application : applications){
            System.out.println(application.getDescription());
        }
        return "success!";
    }

    @RequestMapping(value = "/getModifyByCode", method = RequestMethod.GET)
    @ResponseBody
    public String getModifyByCode() {
        List<ModifyApplication> applications = modifyApplicationService.findAll("1234567");
        for(ModifyApplication application : applications){
            System.out.println(application.getDescription());
        }
        return "success!";
    }

    @RequestMapping(value = "/getModifyRejected", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getModifyRejected() {
        List<ModifyApplication> applications = modifyApplicationService.findAll(ApplicationState.RESERVED);
        for(ModifyApplication application : applications){
            System.out.println(application.getDescription());
        }
        return "success!";
    }

    @RequestMapping(value = "/deleteModify", method = RequestMethod.GET)
    @ResponseBody
    public String deleteModify() {
        modifyApplicationService.deleteApplication(1L);
        return "success!";
    }

    @RequestMapping(value = "/reject", method = RequestMethod.GET)
    @ResponseBody
    public String reject() {
        modifyApplicationService.reject(1L);
        return "success!";
    }

    @RequestMapping(value = "/agree", method = RequestMethod.GET)
    @ResponseBody
    public String agree() {
        modifyApplicationService.agree(2L);
        return "success!";
    }

}
