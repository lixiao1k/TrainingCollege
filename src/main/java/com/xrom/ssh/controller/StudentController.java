package com.xrom.ssh.controller;


import com.xrom.ssh.entity.*;
import com.xrom.ssh.entity.vo.OrderVO;
import com.xrom.ssh.entity.vo.SClassroomVO;
import com.xrom.ssh.entity.vo.SCourseVO;
import com.xrom.ssh.enums.OrderState;
import com.xrom.ssh.exceptions.CreateSameCardException;
import com.xrom.ssh.exceptions.NoCardException;
import com.xrom.ssh.exceptions.SignInFailedException;
import com.xrom.ssh.exceptions.UsedMailException;
import com.xrom.ssh.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class StudentController {
    @Autowired(required = true)
    private StudentService studentService;

    @Autowired(required = true)
    private CardService cardService;

    @Autowired(required = true)
    private AccountService accountService;

    @Autowired(required = true)
    private OrderService orderService;

    @Autowired(required = true)
    private CourseService courseService;

    @Autowired(required = true)
    private ClassroomService classroomService;

    @Autowired(required = true)
    private LearnSignService learnSignService;

    @Autowired(required = true)
    private GradeService gradeService;

    @RequestMapping(value = "/sSignInPage", method = RequestMethod.GET)
    public String sSignInPage(){
        return "sSignInPage";
    }

    @RequestMapping(value = "/sSignUpPage", method = RequestMethod.GET)
    public String sSignUpPage(){
        return "sSignUpPage";
    }

    @RequestMapping(value = "/sSignUp", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
    public String sSignUp(HttpServletRequest request){
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(email == "" || username == "" || password == ""){
            return "alerts/sRegisterEmptyAlert";
        }
        try {
            studentService.register(username, email, password);
        } catch (UsedMailException e) {
            return "alerts/emailRegisteredAlert";
        }
        return "alerts/sRegisterSuccessAlert";
    }

    @RequestMapping(value = "/sSignIn", method = RequestMethod.POST)
    public ModelAndView sSignIn(HttpServletRequest request, HttpSession httpSession){
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if(email == null || password == null){
            return new ModelAndView("alerts/sRegisterEmptyAlert");
        }
        try {
            Student student = studentService.signIn(email, password);
            httpSession.setAttribute("student", student);
            return new ModelAndView(new RedirectView("/sHome"));
        } catch (SignInFailedException e) {
            return new ModelAndView("alerts/loginError");
        }
    }

    @RequestMapping(value = "/sHome", method = RequestMethod.GET)
    public ModelAndView sHome(HttpSession httpSession, ModelMap modelMap){
        Student student = (Student) httpSession.getAttribute("student");
        Long sid = student.getId();
        Account account = accountService.getAccount(sid);
        Card card = cardService.getCard(sid);
        int level = studentService.getLevel(student.getEmail());
        modelMap.put("student", student);
        modelMap.put("account", account);
        modelMap.put("card", card);
        modelMap.put("level", level);
        return new ModelAndView("/sHome");
    }

    @RequestMapping(value = "/sModify", method = RequestMethod.GET)
    public ModelAndView sModify(HttpSession httpSession, ModelMap modelMap){
        Student student = (Student) httpSession.getAttribute("student");
        Card card = cardService.getCard(student.getId());
        modelMap.put("student", student);
        modelMap.put("card", card);
        return new ModelAndView("/sModify");
    }

    @RequestMapping(value = "/sModifyName", method = RequestMethod.POST)
    public ModelAndView sModifyName(HttpServletRequest request, HttpSession httpSession, ModelMap modelMap){
        Student student = (Student) httpSession.getAttribute("student");
        String modifiedName = request.getParameter("userName");
        if(modifiedName == ""){
            return new ModelAndView("alerts/sRegisterEmptyAlert");
        }
        student.setUserName(modifiedName);
        studentService.update(student);
        return new ModelAndView("alerts/modifySuccess");
    }

    @RequestMapping(value = "/sModifyCard", method = RequestMethod.POST)
    public ModelAndView sModifyCard(HttpServletRequest request, HttpSession httpSession, ModelMap modelMap){
        Student student = (Student) httpSession.getAttribute("student");
        String modifiedCard = request.getParameter("cardNumber");
        Card card = cardService.getCard(student.getId());
        if(modifiedCard == ""){
            return new ModelAndView("alerts/sRegisterEmptyAlert");
        }
        if(card == null){
            try {
                cardService.createCard(student.getId(), modifiedCard);
            } catch (CreateSameCardException e) {
                return new ModelAndView("alerts/sameCardAlert");
            }
        }else {
            Card card1 = cardService.getCard(modifiedCard);
            if(card1 != null){
                return new ModelAndView("alerts/sameCardAlert");
            }
            cardService.deleteCard(student.getId());
            try {
                cardService.createCard(student.getId(), modifiedCard);
            } catch (CreateSameCardException e) {
                return new ModelAndView("alerts/sameCardAlert");
            }
        }
        return new ModelAndView("alerts/modifySuccess");
    }

    @RequestMapping(value = "/sOrder", method = RequestMethod.GET)
    public ModelAndView sOrder(HttpSession httpSession, ModelMap modelMap){
        Student student = (Student) httpSession.getAttribute("student");
        List<OrderVO> orderReserved = orderService.getAllOfStudentByState(student.getId(), OrderState.RESERVED);
        List<OrderVO> orderPayed = orderService.getAllOfStudentByState(student.getId(), OrderState.PAYED);
        List<OrderVO> orderCancelled = orderService.getAllOfStudentByState(student.getId(), OrderState.CANCELLED);
        modelMap.put("ordersReserved", orderReserved);
        modelMap.put("ordersPayed", orderPayed);
        modelMap.put("ordersCancelled", orderCancelled);
        return new ModelAndView("/sOrder");
    }

    @RequestMapping(value = "/sCourseMine", method = RequestMethod.GET)
    public ModelAndView sCourseMine(HttpSession httpSession, ModelMap modelMap){
        Student student = (Student) httpSession.getAttribute("student");
        List<SCourseVO> underWayOrder =  courseService.findAllOfStudent(true, student.getId());
        List<SCourseVO> outOfWayOrder = courseService.findAllOfStudent(false, student.getId());
        System.out.println(underWayOrder);
        System.out.println(outOfWayOrder);
        modelMap.put("uOrders", underWayOrder);
        modelMap.put("oOrders", outOfWayOrder);
        return new ModelAndView("sCourseMine");
    }

    @RequestMapping(value = "/sAllCourse", method = RequestMethod.GET)
    public ModelAndView sAllCourse(ModelMap modelMap){
        List<Course> uCourses = courseService.findAll(true); //进行中的所有课程
        List<Course> oCourses = courseService.findAll(false); //已结束的所有课程
        modelMap.put("uCourses", uCourses);
        modelMap.put("oCourses", oCourses);
        return new ModelAndView("/sAllCourse");
    }

    @RequestMapping(value = "/sCourseDetailPage")
    public ModelAndView sCourseDetailPage(HttpSession httpSession, ModelMap modelMap){
        Course course = (Course) httpSession.getAttribute("course");
        List<Classroom> classrooms = classroomService.findAll(course.getId());
        List<SClassroomVO> sClassroomVOS = classroomService.toSClassroomVO(classrooms);
        System.out.println(classrooms);
        modelMap.put("course", course);
        modelMap.put("classrooms", sClassroomVOS);
        return new ModelAndView("/sCourseDetail");
    }

    @RequestMapping(value = "/sCourseDetail/{id}")
    public ModelAndView sCourseDetail(@PathVariable Long id, HttpSession httpSession){
        Course course = courseService.getCourse(id);
        httpSession.setAttribute("course", course);
        return new ModelAndView(new RedirectView("/sCourseDetailPage"));
    }

    @RequestMapping(value = "/sOrder/{id}")
    public ModelAndView sOrder(@PathVariable Long id, HttpSession httpSession, ModelMap modelMap){
        Student student = (Student) httpSession.getAttribute("student");
        Classroom classroom = classroomService.getClass(id);
        Course course = courseService.getCourse(classroom.getCourseId());
        List<Classroom> classrooms = new ArrayList<>();
        classrooms.add(classroom);
        SClassroomVO sClassroomVO = classroomService.toSClassroomVO(classrooms).get(0);
        if(course.getBeginDate().before(new Date())){
            modelMap.put("message", "课程已经在进行中，不可预订！");
            return new ModelAndView("alerts/OrderAlert");
        }
        if(classroom.getStudentNumNow() == classroom.getStudentNumPlan()){
            modelMap.put("message", "课程招收人数已满，不可预订！");
            return new ModelAndView("alerts/OrderAlert");
        }
        Order order = new Order();
        order.setPrice(sClassroomVO.getPriceTotal());
        order.setStudentId(student.getId());
        order.setClassId(classroom.getId());
        order.setCreateTime(new Date());
        order.setIsReserved(1);
        orderService.save(order);
        return new ModelAndView("alerts/OrderSuccess");
    }

    @RequestMapping(value = "/sOrderCancel/{id}")
    public ModelAndView sOrderCancel(@PathVariable Long id, HttpSession httpSession){
        orderService.cancel(id);
        return new ModelAndView(new RedirectView("/sOrder"));
    }

    //传入的是课程id，先转为班级id
    @RequestMapping(value = "/sMyCourseDetail/{id}")
    public ModelAndView sMyCourseDetail(@PathVariable Long id, HttpSession httpSession, ModelMap modelMap){
        Student student = (Student) httpSession.getAttribute("student");
        Classroom classroom = classroomService.getClassroom(student.getId(), id);
        List<LearnSign> learnSigns = null;
        Course course = courseService.getCourse(id);
        int grade = 0;
        if(classroom != null){
            grade = gradeService.getGrade(student.getId(), classroom.getId());
            learnSigns =learnSignService.findAll(student.getId(), classroom.getId());
        }
        modelMap.put("learnSigns", learnSigns);
        modelMap.put("classroom", classroom);
        modelMap.put("grade", grade);
        modelMap.put("course", course);
        return new ModelAndView("/sMyCourseDetail");
    }

    @RequestMapping(value = "/sCardPage")
    public ModelAndView sCardPage(HttpSession session, ModelMap modelMap){
        Student student = (Student) session.getAttribute("student");
        Card card = cardService.getCard(student.getId());
        String hasCard = "no";
        if(card!=null){
            hasCard = "yes";
        }
        modelMap.put("card", card);
        modelMap.put("hasCard", hasCard);
        return new ModelAndView("/sCard");
    }

    @RequestMapping(value = "/sAddCardMoney")
    public ModelAndView sAddCardMoney(HttpSession session){
        Student student = (Student) session.getAttribute("student");
        try {
            cardService.update(student.getId(), 100);
        } catch (NoCardException e) {
            e.printStackTrace();//此处不会有无卡情况
        }
        return new ModelAndView(new RedirectView("/sCardPage"));
    }
}
