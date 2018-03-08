package com.xrom.ssh.controller;

import com.xrom.ssh.entity.*;
import com.xrom.ssh.enums.ApplicationState;
import com.xrom.ssh.enums.OrderState;
import com.xrom.ssh.exceptions.NotValidatedUserException;
import com.xrom.ssh.exceptions.RepeatInsertException;
import com.xrom.ssh.exceptions.SignInFailedException;
import com.xrom.ssh.exceptions.UsedMailException;
import com.xrom.ssh.service.*;
import net.sf.ehcache.search.expression.Or;
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

    @Autowired(required = true)
    private StudentService studentService;

    @Autowired(required = true)
    private CourseService courseService;

    @Autowired(required = true)
    private ClassroomService classroomService;

    @Autowired(required = true)
    private LearnSignService learnSignService;

    @Autowired(required = true)
    private OrderService orderService;

    @Autowired(required = true)
    private AccountService accountService;

    @Autowired(required = true)
    private InstitutionService institutionService;

    @Autowired(required = true)
    private RegisterApplicationService registerApplicationService;

    @Autowired(required = true)
    private ModifyApplicationService modifyApplicationService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "test";
    }


    @RequestMapping(value = "/insertCard", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String insertCard(){
        accountService.insertCard("123456789", 5L);
        return "success!";
    }


    @RequestMapping(value = "/getConsumption", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getConsumption(){
        try {
            return Integer.toString(accountService.getConsumption(5L));
        } catch (NotValidatedUserException e) {
            return "未验证用户";
        }
    }

    @RequestMapping(value = "/getConsumption1", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getConsumption1(){
        try {
            return Integer.toString(accountService.getConsumption(4L));
        } catch (NotValidatedUserException e) {
            return "未验证用户";
        }
    }

    @RequestMapping(value = "/signIn1", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String signIn1(){
        try {
            Student student = studentService.signIn("lixiao1k@163.com", "passrd");
            return student.toString();
        } catch (SignInFailedException e) {
            return e.toString();
        }
    }


    @RequestMapping(value = "/deleteClass", method = RequestMethod.GET)
    @ResponseBody
    public String deleteClass() {
        classroomService.deleteClass(1L);
        return "success!";
    }
}
