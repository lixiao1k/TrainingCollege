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


    @RequestMapping(value = "/signIn", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String signIn(){
        Boolean result = institutionService.signIn("1098678");
        if(result == true){
            return "Success!";
        }else {
            return "Failed!";
        }
    }
    @RequestMapping(value = "/signIn1", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String signIn1(){
        Boolean result = institutionService.signIn("10988");
        if(result == true){
            return "Success!";
        }else {
            return "Failed!";
        }
    }

    @RequestMapping(value = "/getInstitution", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getInstitution(){
        Institution institution = institutionService.getInstitution("1098678");
        if(institution == null){
            return "Wu";
        }else {
            return institution.toString();
        }
    }
    @RequestMapping(value = "/getInstitution1", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getInstitution1(){
        Institution institution = institutionService.getInstitution("10986");
        if(institution == null){
            return "Wu";
        }else {
            return institution.toString();
        }
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getAll(){
        List<Institution> institutions = institutionService.getAllInstitutions();
        if(institutions == null){
            return "WU";
        }else {
            for (Institution institution : institutions){
                System.out.println(institution);
            }
        }
        return "Success!";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String delete(){
        institutionService.deleteInstitution("1098678");
        return "Success!";
    }

    @RequestMapping(value = "/update1", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String update1(){
        institutionService.updateAddress("96eda85", "Nanjing");
        return "Success!";
    }

    @RequestMapping(value = "/update2", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String update2(){
        institutionService.updatePhone("96eda85", "15896257359");
        return "Success!";
    }

    @RequestMapping(value = "/update3", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String update3(){
        institutionService.updateDescription("96eda85", "The Best University in Jiangsu");
        return "Success!";
    }

    @RequestMapping(value = "/update4", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String update4(){
        institutionService.updateName("96eda85", "DongNan");
        return "Success!";
    }

}
