package com.xrom.ssh.controller;

import com.xrom.ssh.entity.Account;
import com.xrom.ssh.entity.Card;
import com.xrom.ssh.entity.Student;
import com.xrom.ssh.exceptions.RepeatInsertException;
import com.xrom.ssh.service.AccountService;
import com.xrom.ssh.service.CardService;
import com.xrom.ssh.service.PersonService;
import com.xrom.ssh.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.List;

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

    @RequestMapping(value = "/createAccount", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String saveAccount() {
        Account account = new Account();
        account.setUserId(2L);
        account.setCardNumber("1234567890");
        accountService.createAccount(account);
        accountService.flush();
        return "success!";
    }

    @RequestMapping(value = "/addAccount", method = RequestMethod.GET)
    @ResponseBody
    public String addAccount() {
        accountService.updateAccount(2L, 500);
        return "success!";
    }

    @RequestMapping(value = "/deleteAccount", method = RequestMethod.GET)
    @ResponseBody
    public String deleteAccount() {
        accountService.deleteAccount(2L);
        return "success!";
    }

}
