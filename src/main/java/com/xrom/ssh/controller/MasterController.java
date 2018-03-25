package com.xrom.ssh.controller;

import com.sun.media.sound.ModelAbstractChannelMixer;
import com.xrom.ssh.entity.Institution;
import com.xrom.ssh.entity.ModifyApplication;
import com.xrom.ssh.entity.RegisterApplication;
import com.xrom.ssh.entity.Student;
import com.xrom.ssh.entity.vo.BillsVO;
import com.xrom.ssh.entity.vo.MInstitutionVO;
import com.xrom.ssh.entity.vo.MStudentVO;
import com.xrom.ssh.enums.ApplicationState;
import com.xrom.ssh.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.rmi.StubNotFoundException;
import java.security.PublicKey;
import java.util.List;

@Controller
public class MasterController {

    @Autowired(required = true)
    private RegisterApplicationService registerApplicationService;

    @Autowired(required = true)
    private ModifyApplicationService modifyApplicationService;

    @Autowired(required = true)
    private InstitutionService institutionService;

    @Autowired(required = true)
    private StudentService studentService;

    @Autowired(required = true)
    private OrderService orderService;

    @RequestMapping(value = "/mSignInPage", method = RequestMethod.GET)
    public String mSignInPage(){
        return "mSignInPage";
    }

    @RequestMapping(value = "/mSignIn", method = RequestMethod.POST)
    public ModelAndView mSignIn(HttpServletRequest request, HttpSession session){
        String password = request.getParameter("password");
        if(password.equals("root")){
            session.setAttribute("master", "root");
            return new ModelAndView("mHome");
        }else {
            return new ModelAndView("alerts/loginError");
        }

    }

    @RequestMapping(value = "/mHome", method = RequestMethod.GET)
    public ModelAndView mHome(HttpServletRequest request, HttpSession httpSession){
        if(httpSession.getAttribute("master")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        return new ModelAndView("mHome");
    }


    @RequestMapping(value = "/mRegisterApplication", method = RequestMethod.GET)
    public ModelAndView mRegisterApplication(ModelMap modelMap, HttpSession httpSession){
        if(httpSession.getAttribute("master")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        List<RegisterApplication> applications = registerApplicationService.findAll(ApplicationState.RESERVED);
        modelMap.put("applications", applications);
        return new ModelAndView("/mRegisterApplication");
    }

    @RequestMapping(value = "/mModifyApplication", method = RequestMethod.GET)
    public ModelAndView mModifyApplication(ModelMap modelMap, HttpSession httpSession){
        if(httpSession.getAttribute("master")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        List<ModifyApplication> applications =modifyApplicationService.findAll(ApplicationState.RESERVED);
        modelMap.put("applications", applications);
        return new ModelAndView("/mModifyApplication");
    }

    @RequestMapping(value = "/mRegisterAgree/{id}")
    public ModelAndView mRegisterAgree(@PathVariable Long id, HttpSession httpSession){
        if(httpSession.getAttribute("master")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        registerApplicationService.agree(id);
        return new ModelAndView(new RedirectView("/mRegisterApplication"));
    }

    @RequestMapping(value = "/mRegisterReject/{id}")
    public ModelAndView mRegisterReject(@PathVariable Long id, HttpSession httpSession){
        if(httpSession.getAttribute("master")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        registerApplicationService.reject(id);
        return new ModelAndView(new RedirectView("/mRegisterApplication"));
    }

    @RequestMapping(value = "/mModifyAgree/{id}")
    public ModelAndView mModifyAgree(@PathVariable Long id, HttpSession httpSession){
        if(httpSession.getAttribute("master")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        modifyApplicationService.agree(id);
        return new ModelAndView(new RedirectView("/mModifyApplication"));
    }

    @RequestMapping(value = "/mModifyReject/{id}")
    public ModelAndView mModifyReject(@PathVariable Long id, HttpSession httpSession){
        if(httpSession.getAttribute("master")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        modifyApplicationService.reject(id);
        return new ModelAndView(new RedirectView("/mModifyApplication"));
    }

    //查看机构统计信息
    @RequestMapping(value = "/mInstitutions")
    public ModelAndView mInstitutions(ModelMap modelMap, HttpSession httpSession){
        if(httpSession.getAttribute("master")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        List<MInstitutionVO> mInstitutionVOS = institutionService.getAllMInstitutionVOs();
        modelMap.put("mInstitutionVOs", mInstitutionVOS);
        return new ModelAndView("/mInstitutions");
    }

    @RequestMapping(value = "/mStudents")
    public ModelAndView mStudents(ModelMap modelMap, HttpSession httpSession){
        if(httpSession.getAttribute("master")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        List<MStudentVO> mStudentVOSNonCancelled = studentService.getAllStudent(false);
        List<MStudentVO> mStudentVOSCancelled = studentService.getAllStudent(true);
        modelMap.put("studentsNonCancelled", mStudentVOSNonCancelled);
        modelMap.put("studentsCancelled", mStudentVOSCancelled);
        return new ModelAndView("/mStudents");
    }

    //会员取消资格
    @RequestMapping(value = "/mStudentCancel/{id}")
    public ModelAndView mStudentCancel(@PathVariable Long id, HttpSession httpSession){
        if(httpSession.getAttribute("master")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        Student student = studentService.getStudent(id);
        studentService.cancel(student.getEmail());
        return new ModelAndView(new RedirectView("/mStudents"));
    }

    @RequestMapping(value = "/mBillsPage")
    public ModelAndView mBillsPage(ModelMap modelMap, HttpSession httpSession){
        if(httpSession.getAttribute("master")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        List<BillsVO> payedBills = orderService.getAllPayedBills();
        int payedSum = orderService.getBillsSum(payedBills);
        List<BillsVO> droppedBills = orderService.getAllDropedBills();
        int droppedSum = orderService.getBillsSum(droppedBills);
        List<BillsVO> payedOfflineBills = orderService.getAllOfflineBills();
        int payedOfflineSum = orderService.getBillsSum(payedOfflineBills);
        modelMap.put("payedBills", payedBills);
        modelMap.put("payedSum", payedSum);
        modelMap.put("droppedBills", droppedBills);
        modelMap.put("droppedSum", droppedSum);
        modelMap.put("payedOfflineBills", payedOfflineBills);
        modelMap.put("payedOfflineSum", payedOfflineSum);
        return new ModelAndView("/mBills");
    }

    @RequestMapping(value = "/mInstitutionFinancial")
    public ModelAndView mInstitutionFinancial(ModelMap modelMap, HttpSession httpSession){
        if(httpSession.getAttribute("master")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        List<MInstitutionVO> mInstitutionVOS = institutionService.getAllInstitutionFinancial();
        modelMap.put("mInstitutionVOs", mInstitutionVOS);
        return new ModelAndView("/mInstitutionFinancial");
    }

    @RequestMapping(value = "/mLogOut")
    public ModelAndView mLogOut(HttpSession session){
        session.invalidate();
        return new ModelAndView(new RedirectView("/"));
    }


}
