package com.xrom.ssh.controller;

import com.xrom.ssh.entity.*;
import com.xrom.ssh.enums.ApplicationState;
import com.xrom.ssh.enums.OrderState;
import com.xrom.ssh.exceptions.RepeatInsertException;
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

    @Autowired(required=true)
    private PersonService personService;

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

    @RequestMapping(value = "/saveInstitution", method = RequestMethod.GET)
    @ResponseBody
    public String savePerson() {
        Institution institution = new Institution();
        institution.setAddress("Shanghai");
        institution.setPhone("15951921161");
        institution.setName("NanDa");
        institution.setDescription("Gooooooood!");
        institutionService.createInstitution(institution);
        institutionService.flush();
        return "success!";
    }


    @RequestMapping(value = "/saveStudent", method = RequestMethod.GET)
    @ResponseBody
    public String saveOrUpdate() throws RepeatInsertException {
        Student student = new Student();
        student.setEmail("lixiao1k@163.com");
        student.setUserName("mike");
        student.setPassword("hello");
        studentService.saveStudent(student);
        studentService.flush();
        return "success!";
    }

    @RequestMapping(value = "/getCourses", method = RequestMethod.GET)
    @ResponseBody
    public String getCourseList() {
        List<Course> courses = courseService.findAll();
        for (Course course : courses){
            System.out.println(course.getDescription());
        }
        return "success!";
    }

    @RequestMapping(value = "/createOrder", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String saveOrder(){
        Order order = new Order();
        order.setClassId(1L);
        order.setCreateTime(new Date());
        order.setStudentId(1L);
        orderService.save(order);
        orderService.flush();
        return "success!";
    }

    @RequestMapping(value = "/getAllOrdersOfStudent", method = RequestMethod.GET)
    @ResponseBody
    public String getOrdersOfStudent() {
        orderService.payOffline(1L,3L);
        return "success!";
    }


    @RequestMapping(value = "/getSigns", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getClassByCode() {
        List<LearnSign> signs = learnSignService.findAll(1L, 2L);
        for(LearnSign sign : signs){
            System.out.println(sign.getDate());
        }
        return "success!";
    }

    @RequestMapping(value = "/deleteClass", method = RequestMethod.GET)
    @ResponseBody
    public String deleteClass() {
        classroomService.deleteClass(1L);
        return "success!";
    }
}
