package com.xrom.ssh.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
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


    @RequestMapping(value = "/modifyApplication", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String modifyApplication(){
        try {
            modifyApplicationService.modifyApplication("96eda85", "Shanghai", "Best university in Shanghai", "15951921161",
                    "JiaoDa");
        } catch (NoInstitutionException e) {
            return e.toString();
        }
        return "success";
    }
    @RequestMapping(value = "/agree", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String agree(){
        modifyApplicationService.agree(1L);
        return "Success!";
    }

    @RequestMapping(value = "/reject", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String reject(){
        modifyApplicationService.reject(1L);
        return "Success!";
    }
    @RequestMapping(value = "/getApplication", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getApplication(){
        ModifyApplication application = modifyApplicationService.getApplication(1L);
        if(application == null){
            return "WU";
        }else {
            System.out.println(application);
            return "Success!";
        }
    }

    @RequestMapping(value = "/getApplication1", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getApplication1(){
        ModifyApplication application = modifyApplicationService.getApplication(2L);
        if(application == null){
            return "WU";
        }else {
            return "Success!";
        }
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getAll(){
        List<ModifyApplication> applications = modifyApplicationService.findAll();
        if(applications == null){
            return "WU";
        }else {
            for (ModifyApplication application : applications){
                System.out.println(application);
            }
        }
        return "Success!";
    }

    @RequestMapping(value = "/getAll1", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getAll4(){
        List<ModifyApplication> applications = modifyApplicationService.findAll("96eda85");
        if(applications == null){
            return "WU";
        }else {
            for (ModifyApplication application : applications){
                System.out.println(application);
            }
        }
        return "Success!";
    }

    @RequestMapping(value = "/getAll2", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getAll5(){
        List<ModifyApplication> applications = modifyApplicationService.findAll("96eda8i");
        if(applications == null || applications.size() == 0){
            return "WU";
        }else {
            for (ModifyApplication application : applications){
                System.out.println(application);
            }
        }
        return "Success!";
    }

    @RequestMapping(value = "/getReserved", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getAll1(){
        List<ModifyApplication> applications = modifyApplicationService.findAll(ApplicationState.RESERVED);
        if(applications == null){
            return "WU";
        }else {
            for (ModifyApplication application : applications){
                System.out.println(application);
            }
        }
        return "Success!";
    }

    @RequestMapping(value = "/getAgreed", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getAll2(){
        List<ModifyApplication> applications = modifyApplicationService.findAll(ApplicationState.AGREED);
        if(applications == null){
            return "WU";
        }else {
            for (ModifyApplication application : applications){
                System.out.println(application);
            }
        }
        return "Success!";
    }

    @RequestMapping(value = "/getRejected", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getAll3(){
        List<ModifyApplication> applications = modifyApplicationService.findAll(ApplicationState.REJECTED);
        if(applications == null){
            return "WU";
        }else {
            for (ModifyApplication application : applications){
                System.out.println(application);
            }
        }
        return "Success!";
    }



}
