package com.xrom.ssh.controller;

import com.xrom.ssh.entity.Account;
import com.xrom.ssh.entity.Card;
import com.xrom.ssh.entity.Institution;
import com.xrom.ssh.entity.Student;
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

    @RequestMapping(value = "/getAllInstitutions", method = RequestMethod.GET)
    @ResponseBody
    public String getStudentList() {
        List<Institution> institutions = institutionService.getAllInstitutions();
        for(Institution institution: institutions){
            System.out.println(institution.getDescription());
        }
        return "success!";
    }

    @RequestMapping(value = "/createInstitution", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String saveInstitution() {
        Institution institution = new Institution();
        institution.setPhone("15951921161");
        institution.setAddress("NanJing,JiangSu");
        institution.setDescription("Our institution is head for people who want to get education abroad.");
        institutionService.createInstitution(institution);
        institutionService.flush();
        return "success!";
    }
    @RequestMapping(value = "/updatePhone", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String updatePhone() {
        institutionService.updatePhone("1234567", "13641281522");
        return "success!";
    }

    @RequestMapping(value = "/addAccount", method = RequestMethod.GET)
    @ResponseBody
    public String addAccount() {
        accountService.updateAccount(2L, 500);
        return "success!";
    }

    @RequestMapping(value = "/deleteInstitution", method = RequestMethod.GET)
    @ResponseBody
    public String deleteInstitution() {
        institutionService.deleteInstitution("1234567");
        return "success!";
    }

}
