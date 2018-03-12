package com.xrom.ssh.repository.impl;

import com.xrom.ssh.entity.Grade;
import com.xrom.ssh.entity.ModifyApplication;
import com.xrom.ssh.repository.GradeRepository;
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
}
