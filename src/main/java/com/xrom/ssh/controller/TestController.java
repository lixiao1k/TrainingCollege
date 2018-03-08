package com.xrom.ssh.controller;

import com.xrom.ssh.entity.*;
import com.xrom.ssh.enums.ApplicationState;
import com.xrom.ssh.enums.OrderState;
import com.xrom.ssh.exceptions.*;
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

    @Autowired(required = true)
    private CardService cardService;
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "test";
    }


    @RequestMapping(value = "/register", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String register(){
        registerApplicationService.register("NJU", "82816783", "Best university in Jiangsu", "Nanjing");
        return "Success!";
    }

    @RequestMapping(value = "/vertify", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String vertify(){
        registerApplicationService.agree(1L);
        return "Success!";
    }

    @RequestMapping(value = "/reject", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String reject(){
        registerApplicationService.reject(1L);
        return "Success!";
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String findAll(){
        List<RegisterApplication> applications = registerApplicationService.findAll();
        if(applications == null || applications.size() == 0){
            return "WU";
        }else {
            for (RegisterApplication application : applications){
                System.out.println(application);
            }
        }
        return "Success!";
    }
    @RequestMapping(value = "/findReserved", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String findReserved(){
        List<RegisterApplication> applications = registerApplicationService.findAll(ApplicationState.RESERVED);
        if(applications == null || applications.size() == 0){
            return "WU";
        }else {
            for (RegisterApplication application : applications){
                System.out.println(application);
            }
        }
        return "Success!";
    }
    @RequestMapping(value = "/findRejected", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String findRejected(){
        List<RegisterApplication> applications = registerApplicationService.findAll(ApplicationState.REJECTED);
        if(applications == null || applications.size() == 0){
            return "WU";
        }else {
            for (RegisterApplication application : applications){
                System.out.println(application);
            }
        }
        return "Success!";
    }
    @RequestMapping(value = "/findAgreed", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String findAgreed(){
        List<RegisterApplication> applications = registerApplicationService.findAll(ApplicationState.AGREED);
        if(applications == null || applications.size() == 0){
            return "WU";
        }else {
            for (RegisterApplication application : applications){
                System.out.println(application);
            }
        }
        return "Success!";
    }

}
