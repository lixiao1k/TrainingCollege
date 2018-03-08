package com.xrom.ssh.repository.impl;

import com.xrom.ssh.entity.Grade;
import com.xrom.ssh.entity.LearnSign;
import com.xrom.ssh.repository.LearnSignRepository;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LearnSignRepositoryImpl extends BaseRepositoryImpl implements LearnSignRepository {
    @Override
    public List<LearnSign> findAll(Long cid) {
        Session session = null;
        Transaction tx = null;
        List<LearnSign> signs= null;
        try {
            session = getCurrentSession();
            tx = session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery("select * from learning_sign where class_id=:CID");
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
    public List<LearnSign> findAll(Long sid, Long cid) {
        Session session = null;
        Transaction tx = null;
        List<LearnSign> signs= null;
        try {
            session = getCurrentSession();
            tx = session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery("select * from learning_sign where student_id=:SID and class_id=:CID");
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
    public LearnSign get(Long sid, Long cid) {
        Session session = null;
        Transaction tx = null;
        List<LearnSign> signs= null;
        try {
            session = getCurrentSession();
            tx = session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery("select * from learning_sign where class_id=:CID and student_id=:SID");
            sqlQuery.setLong("CID", cid);
            sqlQuery.setLong("SID", sid);
            sqlQuery.addEntity(LearnSign.class);
            signs = sqlQuery.list();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }
        if(signs == null || signs.size() == 0){
            return null;
        }else {
            return signs.get(0);
        }
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
            SQLQuery sqlQuery = session.createSQLQuery("select * from learning_sign");
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
        return (Long) getCurrentSession().save(entity);
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
