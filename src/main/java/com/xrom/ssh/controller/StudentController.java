package com.xrom.ssh.controller;


import com.xrom.ssh.entity.Account;
import com.xrom.ssh.entity.Card;
import com.xrom.ssh.entity.Student;
import com.xrom.ssh.entity.vo.OrderVO;
import com.xrom.ssh.entity.vo.SCourseVO;
import com.xrom.ssh.enums.OrderState;
import com.xrom.ssh.exceptions.CreateSameCardException;
import com.xrom.ssh.exceptions.SignInFailedException;
import com.xrom.ssh.exceptions.UsedMailException;
import com.xrom.ssh.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class StudentController {
    @Autowired(required = true)
    StudentService studentService;

    @Autowired(required = true)
    CardService cardService;

    @Autowired(required = true)
    AccountService accountService;

    @Autowired(required = true)
    OrderService orderService;

    @Autowired(required = true)
    CourseService courseService;

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


}
