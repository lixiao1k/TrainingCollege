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


    @RequestMapping(value = "/getCard", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getCard(){
        Card card = cardService.getCard(5L);
        if (card == null){
            return "无卡";
        }else {
            return card.toString();
        }
    }
    @RequestMapping(value = "/getCard1", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getCard1(){
        Card card = cardService.getCard(4L);
        if (card == null){
            return "无卡";
        }else {
            return card.toString();
        }
    }

    @RequestMapping(value = "/getCard2", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getCard2(){
        Card card = cardService.getCard("123456789");
        if (card == null){
            return "无卡";
        }else {
            return card.toString();
        }
    }

    @RequestMapping(value = "/getCard3", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getCard3(){
        Card card = cardService.getCard("1234");
        if (card == null){
            return "无卡";
        }else {
            return card.toString();
        }
    }

    @RequestMapping(value = "/changeBalance", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String changeBalance(){
        try {
            cardService.update(5L, 50);
        } catch (NoCardException e) {
            return e.toString();
        }
        return "success!";
    }

}
