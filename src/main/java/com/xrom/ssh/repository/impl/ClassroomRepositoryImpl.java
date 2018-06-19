package com.xrom.ssh.repository.impl;

import com.xrom.ssh.entity.*;
import com.xrom.ssh.repository.ClassroomRepository;
import com.xrom.ssh.repository.CourseRepository;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClassroomRepositoryImpl extends BaseRepositoryImpl implements ClassroomRepository {

    @Autowired(required = true)
    private CourseRepository courseRepository;

    @Override
    public Classroom load(Long id) {
        return (Classroom) getCurrentSession().load(Classroom.class, id);
    }

    @Override
    public Classroom get(Long id) {
        return (Classroom) getCurrentSession().get(Classroom.class, id);
    }

    @Override
    public List<Classroom> findAll() {
        Session session = null;
        Transaction tx = null;
        List<Classroom> list = null;
        try {
            session = getCurrentSession();
            tx = session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery("select * from class");
            sqlQuery.addEntity(Classroom.class);
            list = sqlQuery.list();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }
        return list;
    }

    @Override
    public List<Classroom> findAll(Long courseId) {
        Session session = null;
        Transaction tx = null;
        List<Classroom> list = null;
        try {
            session = getCurrentSession();
            tx = session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery("select * from class where course_id=:CI");
            sqlQuery.setLong("CI", courseId);
            sqlQuery.addEntity(Classroom.class);
            list = sqlQuery.list();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }
        return list;
    }


    @Override
    public void persist(Classroom entity) {
        getCurrentSession().persist(entity);
    }

    @Override
    public Long save(Classroom entity) {
        return (Long) getCurrentSession().save(entity);
    }

    @Override
    public void saveOrUpdate(Classroom entity) {

    }

    @Override
    public void delete(Long id) {
        Classroom classroom = get(id);
        getCurrentSession().delete(classroom);
    }

    @Override
    public void flush() {
        getCurrentSession().flush();
    }

    @Override
    public void update(Classroom entity) {
        getCurrentSession().update(entity);
    }

    //@管理信息系统
    //创建班级时，更新有关的信息统计表
    @Override
    public void addClassUpdateA(Classroom classroom){
        Course course = courseRepository.get(classroom.getCourseId());
        Session session = getCurrentSession();
        ICourseA iCourseA = (ICourseA) session.createQuery("from ICourseA where courseId = :COURSEID")
                .setParameter("COURSEID", classroom.getCourseId())
                .uniqueResult();

        //更新课程的总计划招收人数
        if(iCourseA != null){
            Transaction tx = session.beginTransaction();
            session.createQuery("update ICourseA courseA set courseA.studentNumPlan = :NUMPLAN" +
                    " where courseA.courseId = :ID")
                    .setParameter("NUMPLAN", iCourseA.getStudentNumPlan() + classroom.getStudentNumPlan())
                    .setParameter("ID", classroom.getCourseId())
                    .executeUpdate();
            tx.commit();
        }else {
            //这边save就是不行，为什么？？？？？？？？？？
//            Session session1 = getCurrentSession();
//            Transaction tx = session1.beginTransaction();
//            iCourseA = new ICourseA();
//            iCourseA.setInstitutionCode(course.getInstitutionCode());
//            iCourseA.setStudentNumPlan(classroom.getStudentNumPlan());
//            iCourseA.setCourseId(course.getId());
//            session.save(iCourseA);
//            tx.commit();
            Transaction tx = session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery("insert into ICourseA values (?, ?, ?, ?);");
            sqlQuery.setParameter(0, course.getId());
            sqlQuery.setParameter(1,course.getInstitutionCode());
            sqlQuery.setParameter(2,0);
            sqlQuery.setParameter(3,classroom.getStudentNumPlan());
            sqlQuery.executeUpdate();
            tx.commit();
            System.out.println("wulalallal");
        }


        ITeacherA iTeacherA = (ITeacherA) session.createQuery("from ITeacherA where tid = :ID")
                .setParameter("ID", classroom.getTeacherId())
                .uniqueResult();

        //更新教师的总计划招收人数
        if(iTeacherA != null){
            Transaction tx = session.beginTransaction();
            session.createQuery("update ITeacherA teacherA set teacherA.studentNumPlan = :NUMPLAN " +
                    "where teacherA.tid = :ID")
                    .setParameter("NUMPLAN", iTeacherA.getStudentNumPlan() + classroom.getStudentNumPlan())
                    .setParameter("ID", classroom.getTeacherId())
                    .executeUpdate();
            tx.commit();
        }else {
            //同理，save不了，在OrderRepositoryImpl中却可以save？？？？？？？？
//            Transaction tx = session.beginTransaction();
//            iTeacherA = new ITeacherA();
//            iTeacherA.setTid(classroom.getTeacherId());
//            iTeacherA.setStudentNumPlan(classroom.getStudentNumPlan());
//            System.out.println(iTeacherA);
//            session.save(iTeacherA);
//            tx.commit();
            Transaction tx = session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery("insert into ITeacherA values (?, ?, ?, ?)");
            sqlQuery.setParameter(0, classroom.getTeacherId());
            sqlQuery.setParameter(1, 0);
            sqlQuery.setParameter(2, classroom.getStudentNumPlan());
            sqlQuery.setParameter(3, 0);
            sqlQuery.executeUpdate();
            tx.commit();
            System.out.println("husahkfnkmal");
        }


    }
}
