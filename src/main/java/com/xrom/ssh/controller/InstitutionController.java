package com.xrom.ssh.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.xrom.ssh.entity.*;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        if(name == "" || address == "" || phone == "" || description == ""){
            return new ModelAndView("alerts/sRegisterEmptyAlert");
        }
        try {
            modifyApplicationService.modifyApplication(institution.getCode(), address, description, phone, name);
        } catch (NoInstitutionException e) {
            e.printStackTrace();
        }
        return new ModelAndView("alerts/iRegisterSuccess");
    }

    @RequestMapping(value = "iTeachers", method = RequestMethod.GET)
    public ModelAndView iTeachers(HttpSession httpSession, Model model){
        Institution institution = (Institution) httpSession.getAttribute("institution");
        String code = institution.getCode();
        List<Teacher> teachers = teacherService.findTeachersOfInstitution(code);
        model.addAttribute("teachers",teachers);
        return new ModelAndView("/iTeachers");
    }

    @RequestMapping(value = "/teacher_delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteTeacher(@PathVariable Long id){
        teacherService.deleteTeacher(id);
        return new ModelAndView(new RedirectView("/iTeachers"));
    }

    @RequestMapping(value = "/iAddTeacher", method = RequestMethod.POST)
    public ModelAndView iAddTeacher(HttpServletRequest request, HttpSession httpSession){
        Institution institution = (Institution) httpSession.getAttribute("institution");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String major = request.getParameter("major");
        teacherService.createTeacher(name,phone,major,institution.getCode());
        return new ModelAndView(new RedirectView("/iTeachers"));
    }

    @RequestMapping(value = "/iAddCoursePage", method = RequestMethod.GET)
    public ModelAndView iAddCoursePage(){
        return new ModelAndView("/iAddCoursePage");
    }

    @RequestMapping(value = "/iAddCourse", method = RequestMethod.POST)
    public ModelAndView iAddCourse(HttpServletRequest request, HttpSession httpSession, Model model){
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

    @RequestMapping(value = "/iGetCourseDetail/{id}", method = RequestMethod.GET)
    public ModelAndView iCourseDetail(@PathVariable Long id, HttpSession httpSession, ModelMap modelMap){
        Course course = courseService.getCourse(id);
        httpSession.setAttribute("course",course);
        return new ModelAndView(new RedirectView("/iCourseDetail"));
    }

    @RequestMapping(value = "/iCourses", method = RequestMethod.GET)
    public ModelAndView iCourses(HttpSession httpSession, ModelMap modelMap){
        Institution institution = (Institution) httpSession.getAttribute("institution");
        List<Course> courses = courseService.findAll(institution.getCode());
        modelMap.put("institution", institution);
        modelMap.put("courses", courses);
        return new ModelAndView("/iCourses");
    }

    @RequestMapping(value = "/iCourseDetail", method = RequestMethod.GET)
    public ModelAndView iCourseDetail(HttpSession httpSession, ModelMap modelMap){
        Course course = (Course) httpSession.getAttribute("course");
        List<Classroom> classrooms = classroomService.findAll(course.getId());
        modelMap.put("course", course);
        modelMap.put("classrooms", classrooms);
        return new ModelAndView("/iCourseDetail");
    }

    @RequestMapping(value = "/iAddClassStudentNow/{id}", method = RequestMethod.GET)
    public ModelAndView iAddClassStudentNow(@PathVariable Long id){
        Classroom classroom = classroomService.getClass(id);
        int classStudentNow = classroom.getStudentNumNow();
        int classStudentPlan = classroom.getStudentNumPlan();
        if(classStudentNow >= classStudentPlan){
            return new ModelAndView("alerts/addStudentAlert");
        }
        classroomService.updateNumNow(id, 1);
        return new ModelAndView(new RedirectView("/iCourseDetail"));
    }

    @RequestMapping(value = "/iMinusClassStudentNow/{id}", method = RequestMethod.GET)
    public ModelAndView iMinusClassStudentNow(@PathVariable Long id){
        Classroom classroom = classroomService.getClass(id);
        int classStudentNow = classroom.getStudentNumNow();
        if(classStudentNow <= 0){
            return new ModelAndView("alerts/minusStudentAlert");
        }
        classroomService.updateNumNow(id, -1);
        return new ModelAndView(new RedirectView("/iCourseDetail"));
    }

    @RequestMapping(value = "/iAddClass", method = RequestMethod.POST)
    public ModelAndView iAddClass(HttpSession httpSession, HttpServletRequest request){
        Course course = (Course) httpSession.getAttribute("course");
        String teacherId = request.getParameter("teacherId");
        String studentsPlan = request.getParameter("studentsPlan");
        classroomService.createClass(course.getId(), Integer.parseInt(studentsPlan),0, Long.parseLong(teacherId));
        return new ModelAndView(new RedirectView("/iCourseDetail"));
    }

    @RequestMapping(value = "/iDeleteClass/{id}", method = RequestMethod.GET)
    public ModelAndView iDeleteClass(@PathVariable Long id){
        classroomService.deleteClass(id);
        return new ModelAndView(new RedirectView("/iCourseDetail"));
    }

    @RequestMapping(value = "/iClassSignPage", method = RequestMethod.GET)
    public ModelAndView iClassSignPage(HttpSession httpSession, ModelMap modelMap){
        Course course = (Course) httpSession.getAttribute("course");
        Classroom classroom = (Classroom) httpSession.getAttribute("classroom");
        List<LearnSign> learnSigns = learnSignService.findAll(classroom.getId());
        modelMap.put("course", course);
        modelMap.put("classroom", classroom);
        modelMap.put("learnSigns", learnSigns);
        return new ModelAndView("/iClassSign");
    }

    @RequestMapping(value = "/iClassSign/{id}", method = RequestMethod.GET)
    public ModelAndView iClassSign(@PathVariable Long id, HttpSession httpSession){
        Classroom classroom = classroomService.getClass(id);
        httpSession.setAttribute("classroom", classroom);
        return new ModelAndView(new RedirectView("/iClassSignPage"));
    }

    @RequestMapping(value = "/iAddSign", method = RequestMethod.POST)
    public ModelAndView iAddSign(HttpSession httpSession, HttpServletRequest request){
        Classroom classroom = (Classroom) httpSession.getAttribute("classroom");
        Long studentId = Long.parseLong(request.getParameter("studentId"));
        String dateStr = request.getParameter("date");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        LearnSign learnSign = new LearnSign();
        learnSign.setClassId(classroom.getId());
        learnSign.setStudentId(studentId);
        learnSign.setDate(date);
        learnSignService.createSign(learnSign);
        return new ModelAndView(new RedirectView("/iClassSignPage"));
    }

    @RequestMapping(value = "/iGradePage/{id}")
    public ModelAndView iGradePage(@PathVariable Long id, HttpSession httpSession, ModelMap modelMap){
        Course course = (Course) httpSession.getAttribute("course");
        Classroom classroom = classroomService.getClass(id);
        httpSession.setAttribute("classroom",classroom);
        List<Grade> grades = gradeService.findAll(id);
        modelMap.put("course", course);
        modelMap.put("classroom", classroom);
        modelMap.put("grades", grades);
        return new ModelAndView("/iGrade");
    }

    @RequestMapping(value = "/iAddGrade")
    public ModelAndView iAddGrade(HttpSession httpSession, HttpServletRequest request){
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
}
