package com.xrom.ssh.controller;


import com.xrom.ssh.entity.*;
import com.xrom.ssh.entity.vo.OrderVO;
import com.xrom.ssh.entity.vo.SClassroomVO;
import com.xrom.ssh.entity.vo.SCourseVO;
import com.xrom.ssh.entity.vo.SPayInfoVO;
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
    public ModelAndView sSignIn(HttpServletRequest request, HttpSession httpSession, ModelMap modelMap){
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if(email == null || password == null){
            return new ModelAndView("alerts/sRegisterEmptyAlert");
        }
        try {
            Student student = studentService.signIn(email, password);
            //用户状态为2，说明资格被取消，无法登陆
            if(student.getUserState() == 2){
                modelMap.put("errorMessage", "被取消资格用户无法登陆！");
                modelMap.put("title", "登陆失败");
                return new ModelAndView("alerts/sError");
            }
            httpSession.setAttribute("student", student);
            return new ModelAndView(new RedirectView("/sHome"));
        } catch (SignInFailedException e) {
            return new ModelAndView("alerts/loginError");
        }
    }

    //请求学生主页
    @RequestMapping(value = "/sHome", method = RequestMethod.GET)
    public ModelAndView sHome(HttpSession httpSession, ModelMap modelMap){
        if(httpSession.getAttribute("student") == null){
            return new ModelAndView(new RedirectView("/"));
        }
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

    //展示学生信息修改页面
    @RequestMapping(value = "/sModify", method = RequestMethod.GET)
    public ModelAndView sModify(HttpSession httpSession, ModelMap modelMap){
        if(httpSession.getAttribute("student") == null){
            return new ModelAndView(new RedirectView("/"));
        }
        Student student = (Student) httpSession.getAttribute("student");
        Card card = cardService.getCard(student.getId());
        modelMap.put("student", student);
        modelMap.put("card", card);
        return new ModelAndView("/sModify");
    }

    //修改用户名
    @RequestMapping(value = "/sModifyName", method = RequestMethod.POST)
    public ModelAndView sModifyName(HttpServletRequest request, HttpSession httpSession, ModelMap modelMap){
        if(httpSession.getAttribute("student") == null){
            return new ModelAndView(new RedirectView("/"));
        }
        Student student = (Student) httpSession.getAttribute("student");
        String modifiedName = request.getParameter("userName");
        if(modifiedName == ""){
            return new ModelAndView("alerts/sRegisterEmptyAlert");
        }
        student.setUserName(modifiedName);
        studentService.update(student);
        return new ModelAndView("alerts/modifySuccess");
    }

    //修改银行卡，如果字段为空，则删除银行卡
    @RequestMapping(value = "/sModifyCard", method = RequestMethod.POST)
    public ModelAndView sModifyCard(HttpServletRequest request, HttpSession httpSession, ModelMap modelMap){
        if(httpSession.getAttribute("student") == null){
            return new ModelAndView(new RedirectView("/"));
        }
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

    //展示用户的订单页面
    @RequestMapping(value = "/sOrder", method = RequestMethod.GET)
    public ModelAndView sOrder(HttpSession httpSession, ModelMap modelMap){
        if(httpSession.getAttribute("student") == null){
            return new ModelAndView(new RedirectView("/"));
        }
        Student student = (Student) httpSession.getAttribute("student");
        List<OrderVO> orderReserved = orderService.getAllOfStudentByState(student.getId(), OrderState.RESERVED);
        List<OrderVO> orderPayed = orderService.getAllOfStudentByState(student.getId(), OrderState.PAYED);
        List<OrderVO> orderCancelled = orderService.getAllOfStudentByState(student.getId(), OrderState.CANCELLED);
        List<OrderVO> orderDroped = orderService.getAllOfStudentByState(student.getId(), OrderState.DROPPED);
        modelMap.put("ordersReserved", orderReserved);
        modelMap.put("ordersPayed", orderPayed);
        modelMap.put("ordersCancelled", orderCancelled);
        modelMap.put("ordersDropped", orderDroped);
        return new ModelAndView("/sOrder");
    }

    //查看用户自己的课程
    @RequestMapping(value = "/sCourseMine", method = RequestMethod.GET)
    public ModelAndView sCourseMine(HttpSession httpSession, ModelMap modelMap){
        if(httpSession.getAttribute("student") == null){
            return new ModelAndView(new RedirectView("/"));
        }
        Student student = (Student) httpSession.getAttribute("student");
        List<SCourseVO> underWayOrder =  courseService.findAllOfStudent(true, student.getId());
        List<SCourseVO> outOfWayOrder = courseService.findAllOfStudent(false, student.getId());
        System.out.println(underWayOrder);
        System.out.println(outOfWayOrder);
        modelMap.put("uOrders", underWayOrder);
        modelMap.put("oOrders", outOfWayOrder);
        return new ModelAndView("sCourseMine");
    }

    //查看平台所有的课程
    @RequestMapping(value = "/sAllCourse", method = RequestMethod.GET)
    public ModelAndView sAllCourse(ModelMap modelMap, HttpSession httpSession){
        if(httpSession.getAttribute("student") == null){
            return new ModelAndView(new RedirectView("/"));
        }
        List<Course> uCourses = courseService.findAll(true); //进行中的所有课程
        List<Course> oCourses = courseService.findAll(false); //已结束的所有课程
        modelMap.put("uCourses", uCourses);
        modelMap.put("oCourses", oCourses);
        return new ModelAndView("/sAllCourse");
    }

    //查看课程的详细页面，包含班级信息
    @RequestMapping(value = "/sCourseDetailPage")
    public ModelAndView sCourseDetailPage(HttpSession httpSession, ModelMap modelMap){
        if(httpSession.getAttribute("student") == null){
            return new ModelAndView(new RedirectView("/"));
        }
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
        if(httpSession.getAttribute("student") == null){
            return new ModelAndView(new RedirectView("/"));
        }
        Course course = courseService.getCourse(id);
        httpSession.setAttribute("course", course);
        return new ModelAndView(new RedirectView("/sCourseDetailPage"));
    }

    //下订单， id为班级ID
    @RequestMapping(value = "/sOrder/{id}")
    public ModelAndView sOrder(@PathVariable Long id, HttpSession httpSession, ModelMap modelMap){
        if(httpSession.getAttribute("student") == null){
            return new ModelAndView(new RedirectView("/"));
        }
        Student student = (Student) httpSession.getAttribute("student");
        Classroom classroom = classroomService.getClass(id);
        Course course = courseService.getCourse(classroom.getCourseId());
        List<Classroom> classrooms = new ArrayList<>();
        classrooms.add(classroom);
        SClassroomVO sClassroomVO = classroomService.toSClassroomVO(classrooms).get(0);
        if(course.getEndDate().before(new Date())){
            modelMap.put("message", "课程已经结束中，不可预订！");
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
        if(httpSession.getAttribute("student") == null){
            return new ModelAndView(new RedirectView("/"));
        }
        orderService.cancel(id);
        return new ModelAndView(new RedirectView("/sOrder"));
    }

    //传入的是课程id，先转为班级id
    @RequestMapping(value = "/sMyCourseDetail/{id}")
    public ModelAndView sMyCourseDetail(@PathVariable Long id, HttpSession httpSession, ModelMap modelMap){
        if(httpSession.getAttribute("student") == null){
            return new ModelAndView(new RedirectView("/"));
        }
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
        if(session.getAttribute("student") == null){
            return new ModelAndView(new RedirectView("/"));
        }
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
        if(session.getAttribute("student") == null){
            return new ModelAndView(new RedirectView("/"));
        }
        Student student = (Student) session.getAttribute("student");
        try {
            cardService.update(student.getId(), 100);
        } catch (NoCardException e) {
            e.printStackTrace();//此处不会有无卡情况
        }
        return new ModelAndView(new RedirectView("/sCardPage"));
    }

    @RequestMapping(value = "/sPayInfoPage/{orderId}")
    public ModelAndView sPayInfo(@PathVariable Long orderId, HttpSession session, ModelMap modelMap){
        if(session.getAttribute("student") == null){
            return new ModelAndView(new RedirectView("/"));
        }
        Student student = (Student) session.getAttribute("student");
        SPayInfoVO sPayInfoVO = orderService.getPayInfoVO(orderId, student.getId());
        modelMap.put("sPayInfoVO", sPayInfoVO);
        return new ModelAndView("/sPayInfo");
    }

    @RequestMapping(value = "/sPayCancel")
    public ModelAndView sPayCancel(HttpSession session){
        if(session.getAttribute("student") == null){
            return new ModelAndView(new RedirectView("/"));
        }
        return new ModelAndView(new RedirectView("/sOrder"));
    }

    @RequestMapping(value = "/sPay/{orderId}")
    public ModelAndView sPay(@PathVariable Long orderId, HttpSession session, ModelMap modelMap){
        if(session.getAttribute("student") == null){
            return new ModelAndView(new RedirectView("/"));
        }
        Order order = orderService.get(orderId);
        //如果订单已经支付，无需再次支付
        if(order.getIsPayed() == 1){
            modelMap.put("errorMessage", "此订单已支付！无需再次支付！");
            modelMap.put("title", "支付失败");
            return new ModelAndView("alerts/sError");
        }

        Student student = (Student) session.getAttribute("student");
        SPayInfoVO sPayInfoVO = orderService.getPayInfoVO(orderId, student.getId());
        if(sPayInfoVO.getCardNumber() == null || sPayInfoVO.getCardNumber() == ""){
            modelMap.put("errorMessage", "没有绑定银行卡，请您先绑定银行卡！");
            modelMap.put("title", "支付失败");
            return new ModelAndView("alerts/sError");
        }
        if(sPayInfoVO.getMoneyNeedPay() > sPayInfoVO.getCardBalance()){
            modelMap.put("errorMessage", "银行卡余额不足，请先前往充值！");
            modelMap.put("title", "支付失败");
            return new ModelAndView("alerts/sError");
        }
        orderService.pay(orderId, student.getId(), sPayInfoVO.getMoneyNeedPay());
        accountService.updateAccount(student.getId(), -(sPayInfoVO.getMoneyFromBP()*10));
        accountService.updateBpBalance(student.getId(), sPayInfoVO.getMoneyNeedPay()-sPayInfoVO.getMoneyFromBP()*10);
        accountService.updateTotalConsumption(student.getId(), sPayInfoVO.getMoneyNeedPay());
        modelMap.put("title", "支付成功！");
        modelMap.put("successMessage", "订单支付成功");
        return new ModelAndView("alerts/sSuccess");
    }

    @RequestMapping(value = "/sUnsubscribe/{orderId}")
    public ModelAndView sUnsubscribe(@PathVariable Long orderId, ModelMap modelMap, HttpSession session){
        if(session.getAttribute("student") == null){
            return new ModelAndView(new RedirectView("/"));
        }
        Order order = orderService.get(orderId);
        Classroom classroom = classroomService.getClass(order.getClassId());
        Course course = courseService.getCourse(classroom.getCourseId());
        Long days = ((course.getBeginDate().getTime()-new Date().getTime())/(1000l*60l*60l*24l));
        if(days < -7){
            modelMap.put("title", "退课失败");
            modelMap.put("errorMessage", "退课失败，课程已经开始超过1周");
            return new ModelAndView("alerts/sError");
        }else {
            return new ModelAndView(new RedirectView("/sUnsubscribeInfo/"+order.getId()));
        }
    }

    //退款时展示退款信息
    @RequestMapping(value = "/sUnsubscribeInfo/{orderId}")
    public ModelAndView sUnsubscribeInfo(@PathVariable Long orderId, ModelMap modelMap, HttpSession httpSession){
        if(httpSession.getAttribute("student") == null){
            return new ModelAndView(new RedirectView("/"));
        }
        OrderVO orderVO = orderService.getUnsubscribeInfo(orderId);
        modelMap.put("orderVO", orderVO);
        return new ModelAndView("/sUnsubscribeInfo");
    }

    @RequestMapping(value = "/sUnscubscribeEnsure/{orderId}")
    public ModelAndView sUnsubscribeEnsure(@PathVariable Long orderId, HttpSession session, ModelMap modelMap){
        if(session.getAttribute("student") == null){
            return new ModelAndView(new RedirectView("/"));
        }
        Student student = (Student) session.getAttribute("student");
        OrderVO orderVO = orderService.getUnsubscribeInfo(orderId);
        orderService.dropClass(orderId, orderVO.getAmountReturned());
        try {
            cardService.update(student.getId(), orderVO.getAmountReturned());
        } catch (NoCardException e) {
            modelMap.put("errorMessage", "没有绑定银行卡，请您先绑定银行卡！");
            modelMap.put("title", "支付失败");
            return new ModelAndView("alerts/sError");
        }
        accountService.updateTotalConsumption(student.getId(), -orderVO.getAmountReturned());
        modelMap.put("title", "退课成功！");
        modelMap.put("successMessage", "课程退订成功");
        return new ModelAndView("alerts/sSuccess");
    }

    @RequestMapping(value = "/sLogOut")
    public ModelAndView sLogOut(HttpSession httpSession){
        httpSession.invalidate();
        return new ModelAndView(new RedirectView("/"));
    }

    //@管理信息系统



    @RequestMapping(value = "/sOrderAnalyse", method = RequestMethod.GET)
    public String sOrderAnalyse(){
        return "sOrderAnalyse";
    }

    //个人年订单统计图表数据请求
    @RequestMapping(value = "/sOrderYearA", method = RequestMethod.GET)
    @ResponseBody
    public List<SOrderYearA> sOrderYearA(HttpSession session){
        Student student = (Student) session.getAttribute("student");
        List<SOrderYearA> list = orderService.getSOrderYearA(student.getId());
        return list;
    }

    //个人季度订单统计图表数据请求
    @RequestMapping(value = "/sOrderSeasonA", method = RequestMethod.GET)
    @ResponseBody
    public List<SOrderSeasonA> sOrderSeasonA(HttpSession session){
        Student student = (Student) session.getAttribute("student");
        List<SOrderSeasonA> list = orderService.getSOrderSeasonA(student.getId());
        return list;
    }

    //个人月份订单统计图表数据请求
    @RequestMapping(value = "/sOrderMonthA", method = RequestMethod.GET)
    @ResponseBody
    public List<SOrderMonthA> sOrderMonthA(HttpSession session){
        Student student = (Student) session.getAttribute("student");
        List<SOrderMonthA> list = orderService.getSOrderMonthA(student.getId());
        return list;
    }

    @RequestMapping(value = "/sOrderTypeA", method = RequestMethod.GET)
    @ResponseBody
    public SOrderTypeA sOrderTypeA(HttpSession session){
        Student student = (Student) session.getAttribute("student");
        return orderService.getSOrderTypeA(student.getId());
    }
}
