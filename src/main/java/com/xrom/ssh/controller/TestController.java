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


    @RequestMapping(value = "/createCourse", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String createCourse(){
        courseService.createCourse(new Date(1000), new Date(2000) , "Chinese",
                "Good for you chinese", 6, 4, "cf01a9c", 57);
        return "success";
    }

    @RequestMapping(value = "/getCourse", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getCourse(){
        List<Course> courses = courseService.findAll();
        for(Course course : courses){
            System.out.println(course);
        }
        return "success";
    }

    @RequestMapping(value = "/getCourse1", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getCourse1(){
        List<Course> courses = courseService.findAll("1234567");
        for(Course course : courses){
            System.out.println(course);
        }
        return "success";
    }

    @RequestMapping(value = "/getCourse2", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getCourse2(){
        List<Course> courses = courseService.findAll(true);
        for(Course course : courses){
            System.out.println(course);
        }
        return "success";
    }

    @RequestMapping(value = "/getCourse3", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getCourse3(){
        List<Course> courses = courseService.findAll(false);
        for(Course course : courses){
            System.out.println(course);
        }
        return "success";
    }

    @RequestMapping(value = "/getCourse4", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getCourse4(){
        List<Course> courses = courseService.findAll("1234567", true);
        for(Course course : courses){
            System.out.println(course);
        }
        return "success";
    }

    @RequestMapping(value = "/getCourse5", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getCourse5(){
        Course course = courseService.getCourse(1L);
        if (course == null){
            return "WU";
        }else {
            System.out.println(course);
        }
        return "Success!";
    }

    @RequestMapping(value = "/getCourse6", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getCourse6(){
        Course course = courseService.getCourse(0L);
        if (course == null){
            return "WU";
        }else {
            System.out.println(course);
        }
        return "Success!";
    }

    @RequestMapping(value = "/deleteCourse", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String delete(){
        courseService.deleteCourse(1L);
        return "Success!";
    }

    @RequestMapping(value = "/getAllByType", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String byType(){
        List<Course> courses = courseService.findAllByType("Chinese");
        for(Course course : courses){
            System.out.println(course);
        }
        return "Success!";
    }
}
