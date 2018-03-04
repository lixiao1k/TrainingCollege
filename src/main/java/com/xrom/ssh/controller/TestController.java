package com.xrom.ssh.controller;

import com.xrom.ssh.entity.Student;
import com.xrom.ssh.exceptions.RepeatInsertException;
import com.xrom.ssh.service.PersonService;
import com.xrom.ssh.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TestController {

    @Autowired(required=true)
    private PersonService personService;

    @Autowired(required = true)
    private StudentService studentService;

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

    @RequestMapping(value = "/saveStudent", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String saveStudent() {
        Student student = new Student();
        student.setEmail("shfjhjk");
        student.setUserName("立夏的");
        try {
            studentService.saveStudent(student);
        } catch (RepeatInsertException e) {
            return "重复值";
        }
        return "success!";
    }

    @RequestMapping(value = "/deleteStudent", method = RequestMethod.GET)
    @ResponseBody
    public String deleteStudent() {
        studentService.delete(1L);
        return "success!";
    }

    @RequestMapping(value = "/getAllStudents", method = RequestMethod.GET)
    @ResponseBody
    public String getStudentList() {
        List<Student> students = studentService.getAllStudents();
        for(Student student: students){
            System.out.println(student.getEmail());
        }
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

    @RequestMapping(value = "/getStudent", method = RequestMethod.GET)
    @ResponseBody
    public String getStudent() {
        Student student = studentService.getStudent(2L);
        System.out.print(student.getUserName());
        return "success!";
    }

}
