package com.xrom.ssh.controller;

import com.xrom.ssh.entity.*;
import com.xrom.ssh.exceptions.RepeatInsertException;
import com.xrom.ssh.service.*;
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
    private CardService cardService;

    @Autowired(required = true)
    private AccountService accountService;

    @Autowired(required = true)
    private InstitutionService institutionService;

    @Autowired(required = true)
    private TeacherService teacherService;

    @Autowired(required = true)
    private CourseService courseService;


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

    @RequestMapping(value = "/getCourses", method = RequestMethod.GET)
    @ResponseBody
    public String getCourseList() {
        List<Course> courses = courseService.findAll();
        for (Course course : courses){
            System.out.println(course.getDescription());
        }
        return "success!";
    }

    @RequestMapping(value = "/createCourse", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String saveCourse() throws ParseException {
        Course course = new Course();
        SimpleDateFormat simFormat = new SimpleDateFormat("yyyy.MM.dd");
        course.setBeginDate(simFormat.parse("2017.11.19"));
        course.setEndDate(simFormat.parse("2017.12.19"));
        course.setHourPerWeek(4);
        course.setInstitutionCode("1234567");
        course.setWeeks(4);
        course.setDescription("Course for English");
        course.setPrice(25);
        course.setType("English");
        courseService.saveCourse(course);
        courseService.flush();
        return "success!";
    }

    @RequestMapping(value = "/getCoursesOnWay", method = RequestMethod.GET)
    @ResponseBody
    public String getCourseOnWayList() {
        List<Course> courses = courseService.findAll(true);
        for (Course course : courses){
            System.out.println(course.getDescription());
        }
        return "success!";
    }

    @RequestMapping(value = "/getCoursesOnWayCode", method = RequestMethod.GET)
    @ResponseBody
    public String getCourseOnWayCodeList() {
        List<Course> courses = courseService.findAll("1234567", true);
        for (Course course : courses){
            System.out.println(course.getDescription());
        }
        return "success!";
    }

    @RequestMapping(value = "/getCoursesType", method = RequestMethod.GET)
    @ResponseBody
    public String getCourseTypeList() {
        List<Course> courses = courseService.findAllByType("English");
        for (Course course : courses){
            System.out.println(course.getDescription());
        }
        return "success!";
    }

    @RequestMapping(value = "/updatePhone", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String updatePhone() {
        teacherService.updatePhone(1L, "15896257359");
        return "success!";
    }

    @RequestMapping(value = "/addAccount", method = RequestMethod.GET)
    @ResponseBody
    public String addAccount() {
        accountService.updateAccount(2L, 500);
        return "success!";
    }

    @RequestMapping(value = "/deleteTeacher", method = RequestMethod.GET)
    @ResponseBody
    public String deleteTeacher() {
        teacherService.deleteTeacher(1L);
        return "success!";
    }

}
