package com.xrom.ssh.controller;

import com.xrom.ssh.entity.ModifyApplication;
import com.xrom.ssh.entity.RegisterApplication;
import com.xrom.ssh.enums.ApplicationState;
import com.xrom.ssh.service.ModifyApplicationService;
import com.xrom.ssh.service.RegisterApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MasterController {

    @Autowired(required = true)
    RegisterApplicationService registerApplicationService;

    @Autowired(required = true)
    ModifyApplicationService modifyApplicationService;

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


    @RequestMapping(value = "/mRegisterApplication", method = RequestMethod.GET)
    public ModelAndView mRegisterApplication(ModelMap modelMap){
        List<RegisterApplication> applications = registerApplicationService.findAll(ApplicationState.RESERVED);
        modelMap.put("applications", applications);
        return new ModelAndView("/mRegisterApplication");
    }

    @RequestMapping(value = "/mModifyApplication", method = RequestMethod.GET)
    public ModelAndView mModifyApplication(ModelMap modelMap){
        List<ModifyApplication> applications =modifyApplicationService.findAll(ApplicationState.RESERVED);
        modelMap.put("applications", applications);
        return new ModelAndView("/mModifyApplication");
    }

    @RequestMapping(value = "/mRegisterAgree/{id}")
    public ModelAndView mRegisterAgree(@PathVariable Long id){
        registerApplicationService.agree(id);
        return new ModelAndView(new RedirectView("/mRegisterApplication"));
    }

    @RequestMapping(value = "/mRegisterReject/{id}")
    public ModelAndView mRegisterReject(@PathVariable Long id){
        registerApplicationService.reject(id);
        return new ModelAndView(new RedirectView("/mRegisterApplication"));
    }


}
