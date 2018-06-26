package com.xrom.ssh.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.xrom.ssh.entity.*;
import com.xrom.ssh.entity.vo.BillsVO;
import com.xrom.ssh.entity.vo.OrderVO;
import com.xrom.ssh.enums.OrderState;
import com.xrom.ssh.exceptions.GradeExistException;
import com.xrom.ssh.exceptions.NoInstitutionException;
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
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class InstitutionController {

    @Autowired(required = true)
    private RegisterApplicationService registerApplicationService;

    @Autowired(required = true)
    private InstitutionService institutionService;

    @Autowired(required = true)
    private ModifyApplicationService modifyApplicationService;

    @Autowired(required = true)
    private TeacherService teacherService;

    @Autowired(required = true)
    private CourseService courseService;

    @Autowired(required = true)
    private ClassroomService classroomService;

    @Autowired(required = true)
    private LearnSignService learnSignService;

    @Autowired(required = true)
    private GradeService gradeService;

    @Autowired(required = true)
    private OrderService orderService;

    @Autowired(required = true)
    private AccountService accountService;

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
        //注册时的注册信息不能有空
        if(name == "" || address == "" || phone == "" || password == "" || briefDescription == ""){
            return "alerts/sRegisterEmptyAlert";
        }
        registerApplicationService.register(name, phone, briefDescription, address, password);
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

    //访问机构主页
    @RequestMapping(value = "/iHome", method = RequestMethod.GET)
    public ModelAndView iHome(HttpSession httpSession, ModelMap modelMap){
        if(httpSession.getAttribute("institution")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        Institution institution = (Institution) httpSession.getAttribute("institution");
        modelMap.put("institution", institution);
        return new ModelAndView("/iHome");
    }

    //访问修改信息页面
    @RequestMapping(value = "iModify", method = RequestMethod.GET)
    public ModelAndView iModify(HttpSession httpSession){
        if(httpSession.getAttribute("institution")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        return new ModelAndView("/iModify");
    }

    //生成修改申请
    @RequestMapping(value = "iModifyApplication", method = RequestMethod.POST)
    public ModelAndView iModifyApplication(HttpServletRequest request, HttpSession httpSession){
        if(httpSession.getAttribute("institution")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        Institution institution = (Institution) httpSession.getAttribute("institution");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String description = request.getParameter("description");
        //修改的信息都得写，不管改不改，就是这么任性
        if(name == "" || address == "" || phone == "" || description == ""){
            //错误信息提示页面
            return new ModelAndView("alerts/sRegisterEmptyAlert");
        }
        try {
            modifyApplicationService.modifyApplication(institution.getCode(), address, description, phone, name);
        } catch (NoInstitutionException e) {
            e.printStackTrace();
        }
        return new ModelAndView("alerts/iRegisterSuccess");
    }

    //访问机构的教师管理页面
    @RequestMapping(value = "iTeachers", method = RequestMethod.GET)
    public ModelAndView iTeachers(HttpSession httpSession, Model model){
        if(httpSession.getAttribute("institution")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        Institution institution = (Institution) httpSession.getAttribute("institution");
        String code = institution.getCode();
        List<Teacher> teachers = teacherService.findTeachersOfInstitution(code);
        model.addAttribute("teachers",teachers);
        return new ModelAndView("/iTeachers");
    }

    //删除教师操作
    @RequestMapping(value = "/teacher_delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteTeacher(@PathVariable Long id, HttpSession httpSession){
        if(httpSession.getAttribute("institution")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        teacherService.deleteTeacher(id);
        return new ModelAndView(new RedirectView("/iTeachers"));
    }

    //添加教师操作
    @RequestMapping(value = "/iAddTeacher", method = RequestMethod.POST)
    public ModelAndView iAddTeacher(HttpServletRequest request, HttpSession httpSession){
        if(httpSession.getAttribute("institution")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        Institution institution = (Institution) httpSession.getAttribute("institution");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String major = request.getParameter("major");
        teacherService.createTeacher(name,phone,major,institution.getCode());
        return new ModelAndView(new RedirectView("/iTeachers"));
    }

    //访问发布计划的页面
    @RequestMapping(value = "/iAddCoursePage", method = RequestMethod.GET)
    public ModelAndView iAddCoursePage(HttpSession httpSession){
        if(httpSession.getAttribute("institution")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        return new ModelAndView("/iAddCoursePage");
    }

    //发布计划，发布课程
    @RequestMapping(value = "/iAddCourse", method = RequestMethod.POST)
    public ModelAndView iAddCourse(HttpServletRequest request, HttpSession httpSession, Model model){
        if(httpSession.getAttribute("institution")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        Institution institution = (Institution) httpSession.getAttribute("institution");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String beginDateStr = request.getParameter("beginDate");
        String endDateStr = request.getParameter("endDate");
        Date beginDate = null;
        Date endDate = null;
        try {
            beginDate = simpleDateFormat.parse(beginDateStr);
            endDate = simpleDateFormat.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String description = request.getParameter("description");
        String hourPerWeek = request.getParameter("hourPerWeek");
        String weeks = request.getParameter("weeks");
        String price = request.getParameter("price");
        String major = request.getParameter("major");
        Long courseid = courseService.createCourse(beginDate, endDate, major, description, Integer.parseInt(hourPerWeek),
                Integer.parseInt(weeks), institution.getCode(), Integer.parseInt(price));
        return new ModelAndView("alerts/courseCreateSuccess");
    }

    //访问课程的详细信息，展示班级等信息
    @RequestMapping(value = "/iGetCourseDetail/{id}", method = RequestMethod.GET)
    public ModelAndView iCourseDetail(@PathVariable Long id, HttpSession httpSession, ModelMap modelMap){
        if(httpSession.getAttribute("institution")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        Course course = courseService.getCourse(id);
        httpSession.setAttribute("course",course);
        return new ModelAndView(new RedirectView("/iCourseDetail"));
    }

    //展示机构所有课程
    @RequestMapping(value = "/iCourses", method = RequestMethod.GET)
    public ModelAndView iCourses(HttpSession httpSession, ModelMap modelMap){
        if(httpSession.getAttribute("institution")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        Institution institution = (Institution) httpSession.getAttribute("institution");
        List<Course> courses = courseService.findAll(institution.getCode());
        modelMap.put("institution", institution);
        modelMap.put("courses", courses);
        return new ModelAndView("/iCourses");
    }
    //访问课程的详细信息，展示班级等信息
    @RequestMapping(value = "/iCourseDetail", method = RequestMethod.GET)
    public ModelAndView iCourseDetail(HttpSession httpSession, ModelMap modelMap){
        if(httpSession.getAttribute("institution")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        Course course = (Course) httpSession.getAttribute("course");
        List<Classroom> classrooms = classroomService.findAll(course.getId());
        modelMap.put("course", course);
        modelMap.put("classrooms", classrooms);
        return new ModelAndView("/iCourseDetail");
    }

    //增加班级目前的学生数
    @RequestMapping(value = "/iAddClassStudentNow/{id}", method = RequestMethod.GET)
    public ModelAndView iAddClassStudentNow(@PathVariable Long id, HttpSession httpSession){
        if(httpSession.getAttribute("institution")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        Classroom classroom = classroomService.getClass(id);
        int classStudentNow = classroom.getStudentNumNow();
        int classStudentPlan = classroom.getStudentNumPlan();
        if(classStudentNow >= classStudentPlan){
            //如果目前学生数大于计划学生数，提示错误
            return new ModelAndView("alerts/addStudentAlert");
        }
        //每次加一人
        classroomService.updateNumNow(id, 1);
        return new ModelAndView(new RedirectView("/iCourseDetail"));
    }

    //减少班级目前的学生数
    @RequestMapping(value = "/iMinusClassStudentNow/{id}", method = RequestMethod.GET)
    public ModelAndView iMinusClassStudentNow(@PathVariable Long id, HttpSession httpSession){
        if(httpSession.getAttribute("institution")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        Classroom classroom = classroomService.getClass(id);
        int classStudentNow = classroom.getStudentNumNow();
        if(classStudentNow <= 0){
            //如果人数小于0，提示错误
            return new ModelAndView("alerts/minusStudentAlert");
        }
        classroomService.updateNumNow(id, -1);
        return new ModelAndView(new RedirectView("/iCourseDetail"));
    }

    //为课程添加班级
    @RequestMapping(value = "/iAddClass", method = RequestMethod.POST)
    public ModelAndView iAddClass(HttpSession httpSession, HttpServletRequest request){
        if(httpSession.getAttribute("institution")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        Course course = (Course) httpSession.getAttribute("course");
        String teacherId = request.getParameter("teacherId");
        String studentsPlan = request.getParameter("studentsPlan");
        classroomService.createClass(course.getId(), Integer.parseInt(studentsPlan),0, Long.parseLong(teacherId));
        return new ModelAndView(new RedirectView("/iCourseDetail"));
    }

    //删除班级
    @RequestMapping(value = "/iDeleteClass/{id}", method = RequestMethod.GET)
    public ModelAndView iDeleteClass(@PathVariable Long id, HttpSession httpSession){
        if(httpSession.getAttribute("institution")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        classroomService.deleteClass(id);
        return new ModelAndView(new RedirectView("/iCourseDetail"));
    }

    //展示班级的先到页面
    @RequestMapping(value = "/iClassSignPage", method = RequestMethod.GET)
    public ModelAndView iClassSignPage(HttpSession httpSession, ModelMap modelMap){
        if(httpSession.getAttribute("institution")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        Course course = (Course) httpSession.getAttribute("course");
        Classroom classroom = (Classroom) httpSession.getAttribute("classroom");
        List<LearnSign> learnSigns = learnSignService.findAll(classroom.getId());
        modelMap.put("course", course);
        modelMap.put("classroom", classroom);
        modelMap.put("learnSigns", learnSigns);//签到记录
        return new ModelAndView("/iClassSign");
    }

    //访问班级的签到记录
    @RequestMapping(value = "/iClassSign/{id}", method = RequestMethod.GET)
    public ModelAndView iClassSign(@PathVariable Long id, HttpSession httpSession){
        if(httpSession.getAttribute("institution")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        Classroom classroom = classroomService.getClass(id);
        httpSession.setAttribute("classroom", classroom);
        return new ModelAndView(new RedirectView("/iClassSignPage"));
    }

    //添加签到记录
    @RequestMapping(value = "/iAddSign", method = RequestMethod.POST)
    public ModelAndView iAddSign(HttpSession httpSession, HttpServletRequest request){
        if(httpSession.getAttribute("institution")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        Classroom classroom = (Classroom) httpSession.getAttribute("classroom");
        Course course = courseService.getCourse(classroom.getCourseId());
        Long studentId = Long.parseLong(request.getParameter("studentId"));
        int week = Integer.parseInt(request.getParameter("week"));
        if(week <= course.getWeeks()){
            learnSignService.createSign(studentId, classroom.getId(), week);
        }
        return new ModelAndView(new RedirectView("/iClassSignPage"));
    }

    //访问班级成绩管理界面
    @RequestMapping(value = "/iGradePage/{id}")
    public ModelAndView iGradePage(@PathVariable Long id, HttpSession httpSession, ModelMap modelMap){
        if(httpSession.getAttribute("institution")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        Course course = (Course) httpSession.getAttribute("course");
        Classroom classroom = classroomService.getClass(id);
        httpSession.setAttribute("classroom",classroom);
        List<Grade> grades = gradeService.findAll(id);
        modelMap.put("course", course);
        modelMap.put("classroom", classroom);
        modelMap.put("grades", grades);
        return new ModelAndView("/iGrade");
    }

    //添加成绩
    @RequestMapping(value = "/iAddGrade")
    public ModelAndView iAddGrade(HttpSession httpSession, HttpServletRequest request){
        if(httpSession.getAttribute("institution")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        Classroom classroom = (Classroom) httpSession.getAttribute("classroom");
        String gradeStr = request.getParameter("grade");
        String studentIdStr = request.getParameter("studentId");
        int grade = (int) Float.parseFloat(gradeStr);
        try {
            gradeService.createGrade(Long.parseLong(studentIdStr), classroom.getId(), grade);
        } catch (GradeExistException e) {
            return new ModelAndView("alerts/gradeRepeatAlert");
        }
        return new ModelAndView(new RedirectView("/iGradePage/"+Long.toString(classroom.getId())));
    }


    //展示机构所有的订单
    @RequestMapping(value = "/iOrders")
    public ModelAndView iOrders(HttpSession httpSession, ModelMap modelMap){
        if(httpSession.getAttribute("institution")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        Institution institution = (Institution) httpSession.getAttribute("institution");
        List<OrderVO> orderVOSPayed = orderService.getAllOfInstituteByState(institution.getCode(), OrderState.PAYED);
        List<OrderVO> orderVOSDropped = orderService.getAllOfInstituteByState(institution.getCode(), OrderState.DROPPED);
        System.out.println(orderVOSDropped);
        modelMap.put("orderVOSPayed", orderVOSPayed);
        modelMap.put("orderVOSDropped", orderVOSDropped);
        return new ModelAndView("/iOrders");
    }

    //线下支付界面
    @RequestMapping(value = "/iPayOfflinePage")
    public ModelAndView iPayOfflinePage(HttpSession httpSession){
        if(httpSession.getAttribute("institution")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        return new ModelAndView("/iPayOffline");
    }

    //支持线下支付
    @RequestMapping(value = "/iPayOffline", method = RequestMethod.POST)
    public ModelAndView iPayOffline(HttpServletRequest request, ModelMap modelMap, HttpSession session){
        if(session.getAttribute("institution")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        Institution institution = (Institution) session.getAttribute("institution");
        Long orderId = Long.parseLong(request.getParameter("orderId"));
        Order order = orderService.get(orderId);
        if(order == null){
            modelMap.put("title", "错误");
            modelMap.put("errorMessage", "没有此订单");
            return new ModelAndView("alerts/sError");
        }
        Classroom classroom = classroomService.getClass(order.getClassId());
        Course course = null;
        if(classroom != null){
            course = courseService.getCourse(classroom.getCourseId());
        }
        if(course != null && !course.getInstitutionCode().equals(institution.getCode())){
            modelMap.put("title", "错误");
            modelMap.put("errorMessage", "不是此机构订单，无权操作");
            return new ModelAndView("/alerts/sError");
        }
        modelMap.put("Order", order);
        return new ModelAndView("/iPayOfflineInfo");
    }

    //支付信息中的取消支付action
    @RequestMapping(value = "/iPayOfflineCancel")
    public ModelAndView iPayOfflineCancel(HttpSession httpSession){
        if(httpSession.getAttribute("institution")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        return new ModelAndView(new RedirectView("/iPayOfflinePage"));
    }

    //确定支付
    @RequestMapping(value = "/iPayOfflineEnsure/{orderId}")
    public ModelAndView iPayOfflineEnsure(@PathVariable Long orderId, ModelMap modelMap, HttpSession httpSession){
        if(httpSession.getAttribute("institution")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        Order order = orderService.get(orderId);
        orderService.payOffline(orderId, order.getPrice());
        Account account = accountService.getAccount(order.getStudentId());
        if(account != null){
            accountService.updateTotalConsumption(order.getStudentId(), order.getPrice());
        }
        modelMap.put("title", "支付成功！");
        modelMap.put("successMessage", "订单支付成功");
        return new ModelAndView("alerts/sSuccess");
    }

    //查看机构账目
    @RequestMapping(value = "/iBillsPage")
    public ModelAndView iBillsPage(HttpSession session, ModelMap modelMap){
        if(session.getAttribute("institution")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        Institution institution = (Institution) session.getAttribute("institution");
        List<BillsVO> payedBills = orderService.getAllPayedBillsOfInstitute(institution.getCode());
        int payedSum = orderService.getBillsSum(payedBills);
        List<BillsVO> droppedBills = orderService.getAllDropedBillsOfInstitute(institution.getCode());
        int droppedSum = orderService.getBillsSum(droppedBills);
        List<BillsVO> payedOfflineBills = orderService.getAllOfflineBillsOfInstitute(institution.getCode());
        int payedOfflineSum = orderService.getBillsSum(payedOfflineBills);
        modelMap.put("payedBills", payedBills);
        modelMap.put("payedSum", payedSum);
        modelMap.put("droppedBills", droppedBills);
        modelMap.put("droppedSum", droppedSum);
        modelMap.put("payedOfflineBills", payedOfflineBills);
        modelMap.put("payedOfflineSum", payedOfflineSum);
        System.out.println(payedOfflineBills);
        return new ModelAndView("/iBills");
    }

    @RequestMapping(value = "/iLogOut")
    public ModelAndView iLogOut(HttpSession session){
        session.invalidate();
        return new ModelAndView(new RedirectView("/"));
    }

    //@管理信息系统
    @RequestMapping(value = "/iAnalyse")
    public ModelAndView iAnalyse(HttpSession session){
        if(session.getAttribute("institution")==null){
            return new ModelAndView(new RedirectView("/"));
        }
        return new ModelAndView("/iAnalyse");
    }


    //获取IOrderA
    @RequestMapping(value = "/iGetIOrderA")
    @ResponseBody
    public IOrderA iGetIOrderA(HttpSession session){
        if(session.getAttribute("institution") == null){
            return null;
        }
        Institution institution = (Institution) session.getAttribute("institution");
        IOrderA iOrderA = institutionService.getIOrderA(institution.getCode());
        return iOrderA;
    }


    @RequestMapping(value = "/iGetOrderYearA")
    @ResponseBody
    public List<IOrderYearA> iGetOrderYearA(HttpSession session){
        Institution institution = (Institution) session.getAttribute("institution");
        List<IOrderYearA> list = orderService.getIOrderYearA(institution.getCode());
        return list;
    }


    @RequestMapping(value = "/iGetOrderSeasonA")
    @ResponseBody
    public List<IOrderSeasonA> iGetOrderSeasonA(HttpSession session){
        Institution institution = (Institution) session.getAttribute("institution");
        List<IOrderSeasonA> list = orderService.getIOrderSeasonA(institution.getCode());
        return list;
    }

    @RequestMapping(value = "/iGetOrderMonthA")
    @ResponseBody
    public List<IOrderMonthA> iGetOrderMonthA(HttpSession session){
        Institution institution = (Institution) session.getAttribute("institution");
        List<IOrderMonthA> list = orderService.getIOrderMonthA(institution.getCode());
        return list;
    }

    @RequestMapping(value = "/iGetCourseTypeA")
    @ResponseBody
    public ICourseTypeA iGetCourseTypeA(HttpSession session){
        Institution institution = (Institution) session.getAttribute("institution");
        return courseService.getICourseTypeA(institution.getCode());
    }

    @RequestMapping(value = "/iGetCourseA")
    @ResponseBody
    public HashMap iGetCourseA(HttpSession session){
        Institution institution = (Institution) session.getAttribute("institution");
        return courseService.getICourseA(institution.getCode());
    }

    @RequestMapping(value = "/iGetCourseSignA/{courseId}")
    @ResponseBody
    public HashMap iGetCourseSignA(@PathVariable Long courseId){
        ICourseA iCourseA = courseService.getICourseA(courseId);
        List<ICourseSignA> courseSignAS = learnSignService.getICourseSignA(courseId);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("courseA", iCourseA);
        hashMap.put("courseSignAS", courseSignAS);
        return hashMap;
    }


    @RequestMapping(value = "/iGetTeacherA")
    @ResponseBody
    public HashMap iGetTeacherA(HttpSession session){
        Institution institution = (Institution) session.getAttribute("institution");
        return teacherService.getITeacherA(institution.getCode());
    }


}
