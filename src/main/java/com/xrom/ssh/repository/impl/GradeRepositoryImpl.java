package com.xrom.ssh.repository.impl;

import com.xrom.ssh.entity.Grade;
import com.xrom.ssh.entity.ModifyApplication;
import com.xrom.ssh.entity.SOrderGradeA;
import com.xrom.ssh.repository.GradeRepository;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GradeRepositoryImpl extends BaseRepositoryImpl implements GradeRepository {
    @Override
    public Grade get(Long sid, Long cid) {
        Session session = null;
        Transaction tx = null;
        List<Grade> grades= null;
        try {
            session = getCurrentSession();
            tx = session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery("select * from grade where student_id=:SID and class_id=:CID");
            sqlQuery.setLong("SID", sid);
            sqlQuery.setLong("CID", cid);
            sqlQuery.addEntity(Grade.class);
            grades = sqlQuery.list();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }
        if(grades != null && grades.size() != 0){
            return grades.get(0);
        }else {
            return null;
        }
    }

    @Override
    public List<Grade> findAll(Long cid) {
        Session session = null;
        Transaction tx = null;
        List<Grade> grades= null;
        try {
            session = getCurrentSession();
            tx = session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery("select * from grade where class_id=:CID");
            sqlQuery.setLong("CID", cid);
            sqlQuery.addEntity(Grade.class);
            grades = sqlQuery.list();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }
        return grades;
    }

    @Override
    public Grade load(Long id) {
        return (Grade) getCurrentSession().load(Grade.class, id);
    }

    @Override
    public Grade get(Long id) {
        return (Grade) getCurrentSession().get(Grade.class, id);
    }

    @Override
    public List<Grade> findAll() {
        return null;
    }

    @Override
    public void persist(Grade entity) {
        getCurrentSession().persist(entity);
    }

    @Override
    public Long save(Grade entity) {
        return (Long) getCurrentSession().save(entity);
    }

    @Override
    public void saveOrUpdate(Grade entity) {

    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public void flush() {

    }

    @Override
    public void update(Grade entity) {

    }

    //@管理信息系统
    @Override
    public void updateSOrderGradeA(Grade grade){
        String level;
        int gradeValue = grade.getGrade();
        if(gradeValue >= 90){
            level = "excellent";
        }else if(gradeValue >= 75){
            level = "good";
        }else if(gradeValue >= 60){
            level = "pass";
        }else {
            level = "fail";
        }
        Session session = getCurrentSession();
        SOrderGradeA sOrderGradeA = (SOrderGradeA) session.createQuery("from SOrderGradeA where sid = :SID")
                .setParameter("SID", grade.getStudentId())
                .uniqueResult();
        if(sOrderGradeA != null){
            System.out.println("not null");
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("update SOrderGradeA gradeA set gradeA." + level + " = :AMOUNT where gradeA.sid = :SID");
            if(gradeValue >= 90){
                query.setParameter("AMOUNT", sOrderGradeA.getExcellent() + 1);
            }else if (gradeValue >= 75){
                query.setParameter("AMOUNT", sOrderGradeA.getGood() + 1);
            }else if (gradeValue >= 60){
                query.setParameter("AMOUNT", sOrderGradeA.getPass() + 1);
            }else {
                query.setParameter("AMOUNT", sOrderGradeA.getFail() + 1);
            }
            query.setParameter("SID", sOrderGradeA.getSid())
                    .executeUpdate();
            tx.commit();
        }else {
            sOrderGradeA = new SOrderGradeA();
            sOrderGradeA.setSid(grade.getStudentId());
            if(gradeValue >= 90){
                sOrderGradeA.setExcellent(1);
            }else if (gradeValue >= 75){
                sOrderGradeA.setGood(1);
            }else if (gradeValue >= 60){
                sOrderGradeA.setPass(1);
            }else {
                sOrderGradeA.setFail(1);
            }
            Transaction tx = session.beginTransaction();
            session.save(sOrderGradeA);
            tx.commit();
        }

    }
}
