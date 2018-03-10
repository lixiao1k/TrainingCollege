package com.xrom.ssh.controller;


import com.xrom.ssh.entity.Student;
import com.xrom.ssh.exceptions.SignInFailedException;
import com.xrom.ssh.exceptions.UsedMailException;
import com.xrom.ssh.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class StudentController {
    @Autowired(required = true)
    StudentService studentService;

    @RequestMapping(value = "/sSignInPage", method = RequestMethod.GET)
    public String sSignInPage(){
        return "sSignInPage";
    }

    @RequestMapping(value = "/sSignUpPage", method = RequestMethod.GET)
    public String sSignUpPage(){
        return "sSignUpPage";
    }

    @RequestMapping(value = "/sSignUp", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
    public String sSignUp(HttpServletRequest request, Model model){
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
    public ModelAndView sSignIn(HttpServletRequest request, HttpServletResponse response){
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        System.out.println(email);
        System.out.println(password);
        if(email == null || password == null){
            return new ModelAndView("alerts/sRegisterEmptyAlert");
        }
        try {
            Student student = studentService.signIn(email, password);
            return new ModelAndView("/test","student", student);
        } catch (SignInFailedException e) {
            return new ModelAndView("alerts/loginError");
        }
    }
}
