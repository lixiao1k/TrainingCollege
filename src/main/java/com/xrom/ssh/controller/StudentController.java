package com.xrom.ssh.controller;


import com.xrom.ssh.entity.Account;
import com.xrom.ssh.entity.Card;
import com.xrom.ssh.entity.Student;
import com.xrom.ssh.exceptions.SignInFailedException;
import com.xrom.ssh.exceptions.UsedMailException;
import com.xrom.ssh.service.AccountService;
import com.xrom.ssh.service.CardService;
import com.xrom.ssh.service.StudentService;
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

@Controller
public class StudentController {
    @Autowired(required = true)
    StudentService studentService;

    @Autowired(required = true)
    CardService cardService;

    @Autowired(required = true)
    AccountService accountService;



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
}
