package com.xrom.ssh.controller;

import com.xrom.ssh.entity.*;
import com.xrom.ssh.exceptions.RepeatInsertException;
import com.xrom.ssh.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Controller
public class TestController {

    @Autowired(required=true)
    private PersonService personService;

    @Autowired(required = true)
    private StudentService studentService;

    @Autowired(required = true)
    private CardService cardService;

    @Autowired(required = true)
    private AccountService accountService;

    @Autowired(required = true)
    private InstitutionService institutionService;

    @Autowired(required = true)
    private TeacherService teacherService;

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

    @RequestMapping(value = "/getTeachers", method = RequestMethod.GET)
    @ResponseBody
    public String getTeacherList() {
        List<Teacher> teachers = teacherService.findTeachersOfInstitution("cf01a9c");
        for(Teacher teacher : teachers){
            System.out.println(teacher.getName());
        }
        return "success!";
    }

    @RequestMapping(value = "/createTeacher", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String saveTeacher() {
        Teacher teacher = new Teacher();
        teacher.setName("Shelton");
        teacher.setPhone("15951921161");
        teacher.setInstitutionCode("cf01a9c");
        teacher.setTeachingType("English");
        teacherService.saveTeacher(teacher);
        teacherService.flush();
        return "success!";
    }
    @RequestMapping(value = "/updatePhone", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String updatePhone() {
        teacherService.updatePhone(1L, "15896257359");
        return "success!";
    }

    @RequestMapping(value = "/addAccount", method = RequestMethod.GET)
    @ResponseBody
    public String addAccount() {
        accountService.updateAccount(2L, 500);
        return "success!";
    }

    @RequestMapping(value = "/deleteTeacher", method = RequestMethod.GET)
    @ResponseBody
    public String deleteTeacher() {
        teacherService.deleteTeacher(1L);
        return "success!";
    }

}
