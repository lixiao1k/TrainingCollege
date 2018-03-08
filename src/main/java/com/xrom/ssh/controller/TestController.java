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

    @Autowired(required = true)
    private TeacherService teacherService;


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "test";
    }


    @RequestMapping(value = "/createTeacher", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String createTeacher(){
        teacherService.createTeacher("Mike", "127799495", "Maths", "96eda85");
        return "success";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String delete(){
        teacherService.deleteTeacher(3L);
        return "success";
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getAll(){
        List<Teacher> teachers = teacherService.findTeachersOfInstitution("cf01a9c");
        for (Teacher teacher : teachers){
            System.out.println(teacher);
        }
        return "success";
    }

}
