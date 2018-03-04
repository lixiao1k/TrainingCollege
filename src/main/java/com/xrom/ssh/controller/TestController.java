package com.xrom.ssh.controller;

import com.xrom.ssh.entity.Card;
import com.xrom.ssh.entity.Student;
import com.xrom.ssh.exceptions.RepeatInsertException;
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

    @RequestMapping(value = "/saveCard", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String saveCard() {
        Card card = new Card();
        card.setUserId(2L);
        card.setCardNumber("1234567890");
        cardService.saveCard(card);
        cardService.flush();
        System.out.print("Here");
        return "success!";
    }

    @RequestMapping(value = "/deleteCard", method = RequestMethod.GET)
    @ResponseBody
    public String deleteCard() {
        cardService.deleteCard(2L);
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

    @RequestMapping(value = "/getCard", method = RequestMethod.GET)
    @ResponseBody
    public String getCard() {
        Card card = cardService.getCard(2L);
        System.out.print(card.getBalance());
        return "success!";
    }

}
