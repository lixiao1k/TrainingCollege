package com.xrom.ssh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MasterController {
    @RequestMapping(value = "/mSignInPage", method = RequestMethod.GET)
    public String mSignInPage(){
        return "mSignInPage";
    }

    @RequestMapping(value = "/mSignIn", method = RequestMethod.POST)
    public ModelAndView mSignIn(HttpServletRequest request){
        String password = request.getParameter("password");
        if(password.equals("root")){
            return new ModelAndView("mHome");
        }else {
            return new ModelAndView("alerts/loginError");
        }

    }

    @RequestMapping(value = "/mHome", method = RequestMethod.GET)
    public ModelAndView mHome(HttpServletRequest request){
        return new ModelAndView("mHome");
    }
}
