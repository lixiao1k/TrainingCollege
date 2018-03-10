package com.xrom.ssh.controller;

import com.xrom.ssh.entity.Institution;
import com.xrom.ssh.service.InstitutionService;
import com.xrom.ssh.service.RegisterApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class InstitutionController {

    @Autowired(required = true)
    private RegisterApplicationService registerApplicationService;

    @Autowired(required = true)
    private InstitutionService institutionService;

    @RequestMapping(value = "/iSignUpPage", method = RequestMethod.GET)
    public String iSignUpPage(){
        return "iSignUpPage";
    }

    @RequestMapping(value = "/iSignInPage", method = RequestMethod.GET)
    public String iSignInPage(){
        return "iSignInPage";
    }

    @RequestMapping(value = "/iSignUp", method = RequestMethod.POST)
    public String iSignUp(HttpServletRequest request){
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String briefDescription = request.getParameter("briefDescription");
        if(name == "" || address == "" || phone == "" || password == "" || briefDescription == ""){
            return "alerts/sRegisterEmptyAlert";
        }
        registerApplicationService.register(name, phone, briefDescription, address);
        return "alerts/iRegisterSuccess";
    }

    @RequestMapping(value = "/iSignIn", method = RequestMethod.POST)
    public ModelAndView iSignIn(HttpServletRequest request, HttpSession httpSession){
        String code = request.getParameter("code");
        String password = request.getParameter("password");
        Boolean isSuccessful = institutionService.signIn(code, password);
        if(isSuccessful == true){
            Institution institution = institutionService.getInstitution(code);
            httpSession.setAttribute("institution", institution);
            return new ModelAndView(new RedirectView("/iHome"));
        }else {
            return new ModelAndView("/alerts/loginError");
        }
    }

    @RequestMapping(value = "/iHome", method = RequestMethod.GET)
    public ModelAndView iHome(HttpSession httpSession, ModelMap modelMap){
        Institution institution = (Institution) httpSession.getAttribute("institution");
        modelMap.put("institution", institution);
        return new ModelAndView("/iHome");
    }

    @RequestMapping(value = "iModify", method = RequestMethod.GET)
    public ModelAndView iModify(){
        return new ModelAndView("/iModify");
    }

    @RequestMapping(value = "iModifyApplication", method = RequestMethod.POST)
    public ModelAndView iModifyApplication(HttpServletRequest request, HttpSession httpSession){
        Institution institution = (Institution) httpSession.getAttribute("institution");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String description = request.getParameter("description");
        System.out.println(institution);
        System.out.println(name);
        System.out.println(address);
        System.out.println(phone);
        System.out.println(description);
        return new ModelAndView("alerts/iRegisterSuccess");
    }
}
