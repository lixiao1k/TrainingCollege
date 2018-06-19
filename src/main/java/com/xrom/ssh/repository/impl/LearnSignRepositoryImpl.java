package com.xrom.ssh.repository.impl;

import com.xrom.ssh.entity.*;
import com.xrom.ssh.repository.ClassroomRepository;
import com.xrom.ssh.repository.CourseRepository;
import com.xrom.ssh.repository.LearnSignRepository;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LearnSignRepositoryImpl extends BaseRepositoryImpl implements LearnSignRepository {
    @Autowired(required = true)
    private ClassroomRepository classroomRepository;

    @Autowired(required = true)
    private CourseRepository courseRepository;

    @Override
    public List<LearnSign> findAll(Long cid) {
        List<LearnSign> signs;
        Session session = getCurrentSession();
        Transaction tx = session.beginTransaction();
        signs = session.createQuery("from LearnSign where class_id = :CID")
                .setParameter("CID", cid)
                .list();
        return signs;
    }

    @Override
    public List<LearnSign> findAll(Long sid, Long cid) {
        Session session = null;
        Transaction tx = null;
        List<LearnSign> signs= null;
        try {
            session = getCurrentSession();
            tx = session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery("select * from LearnSign where student_id=:SID and class_id=:CID");
            sqlQuery.setLong("SID", sid);
            sqlQuery.setLong("CID", cid);
            sqlQuery.addEntity(LearnSign.class);
            signs = sqlQuery.list();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }
        return signs;
    }

    @Override
    public LearnSign get(Long sid, Long cid, int week) {
        Session session = getCurrentSession();
        LearnSign learnSign = (LearnSign) session.createQuery("from LearnSign where studentId = :SID and " +
                "classId = :CID and week = :WEEK")
                .setParameter("SID", sid)
                .setParameter("CID", cid)
                .setParameter("WEEK", week)
                .uniqueResult();
        return learnSign;
    }

    @Override
    public LearnSign load(Long id) {
        return (LearnSign) getCurrentSession().load(LearnSign.class, id);
    }

    @Override
    public LearnSign get(Long id) {
        return (LearnSign) getCurrentSession().get(LearnSign.class, id);
    }

    @Override
    public List<LearnSign> findAll() {
        Session session = null;
        Transaction tx = null;
        List<LearnSign> signs= null;
        try {
            session = getCurrentSession();
            tx = session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery("select * from LearnSign");
            sqlQuery.addEntity(LearnSign.class);
            signs = sqlQuery.list();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }
        return signs;
    }

    @Override
    public void persist(LearnSign entity) {
        getCurrentSession().persist(entity);
    }

    @Override
    public Long save(LearnSign entity) {
        updateICourseSignA(entity);
        return (Long) getCurrentSession().save(entity);
    }
    //@管理信息系统，更新ICourseSignA
    private void updateICourseSignA(LearnSign learnSign){
        Classroom classroom = classroomRepository.get(learnSign.getClassId());
        Course course = courseRepository.get(classroom.getCourseId());
        Session session = getCurrentSession();
        ICourseSignA iCourseSignA = (ICourseSignA) session.createQuery("from ICourseSignA where courseId = :CID and " +
                "week = :WEEK")
                .setParameter("CID", classroom.getCourseId())
                .setParameter("WEEK", learnSign.getWeek())
                .uniqueResult();
        if(iCourseSignA != null){
            Transaction tx = session.beginTransaction();
            session.createQuery("update ICourseSignA signA set signA.signAmount = :AMOUNT " +
                    "where signA.courseId = :CID and signA.week = :WEEK")
                    .setParameter("AMOUNT", iCourseSignA.getSignAmount() + 1)
                    .setParameter("CID", iCourseSignA.getCourseId())
                    .setParameter("WEEK", iCourseSignA.getWeek())
                    .executeUpdate();
            tx.commit();
        }else{
            Transaction tx = session.beginTransaction();
            iCourseSignA = new ICourseSignA();
            iCourseSignA.setCode(course.getInstitutionCode());
            iCourseSignA.setCourseId(classroom.getCourseId());
            iCourseSignA.setSignAmount(1);
            iCourseSignA.setWeek(learnSign.getWeek());
            session.save(iCourseSignA);
            tx.commit();
        }
    }

    @Override
    public void saveOrUpdate(LearnSign entity) {

    }

    @Override
    public void delete(Long id) {
        LearnSign learnSign = get(id);
        getCurrentSession().delete(learnSign);
    }

    @Override
    public void flush() {
        getCurrentSession().flush();
    }

    @Override
    public void update(LearnSign entity) {
        getCurrentSession().update(entity);
    }
}
