package com.xrom.ssh.repository.impl;

import com.xrom.ssh.entity.Course;
import com.xrom.ssh.entity.ModifyApplication;
import com.xrom.ssh.repository.ModifyApplicationRepository;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ModifyApplicationRepositoryImpl extends BaseRepositoryImpl implements ModifyApplicationRepository {
    @Override
    public ModifyApplication load(Long id) {
        return (ModifyApplication) getCurrentSession().load(ModifyApplication.class, id);
    }

    @Override
    public ModifyApplication get(Long id) {
        return (ModifyApplication) getCurrentSession().get(ModifyApplication.class, id);
    }

    @Override
    public List<ModifyApplication> findAll() {
        Session session = null;
        Transaction tx = null;
        List<ModifyApplication> list = null;
        try {
            session = getCurrentSession();
            tx = session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery("select * from application_modify");
            sqlQuery.addEntity(ModifyApplication.class);
            list = sqlQuery.list();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }
        return list;
    }

    @Override
    public void persist(ModifyApplication entity) {
        getCurrentSession().persist(entity);

    }

    @Override
    public Long save(ModifyApplication entity) {
        return (Long) getCurrentSession().save(entity);
    }

    @Override
    public void saveOrUpdate(ModifyApplication entity) {

    }

    @Override
    public void delete(Long id) {
        ModifyApplication modifyApplication = get(id);
        getCurrentSession().delete(modifyApplication);
    }

    @Override
    public void flush() {
        getCurrentSession().flush();
    }

    @Override
    public void update(ModifyApplication entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public void reject(Long id) {
        ModifyApplication modifyApplication = get(id);
        modifyApplication.setIsRejected(1);
        modifyApplication.setIsAgreed(0);
        update(modifyApplication);
    }

    @Override
    public void agree(Long id) {
        ModifyApplication modifyApplication = get(id);
        modifyApplication.setIsAgreed(1);
        modifyApplication.setIsRejected(0);
        update(modifyApplication);
    }

    @Override
    public List<ModifyApplication> findAll(String institutionCode) {
        Session session = null;
        Transaction tx = null;
        List<ModifyApplication> list = null;
        try {
            session = getCurrentSession();
            tx = session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery("select * from application_modify where institution_code=:IC");
            sqlQuery.setString("IC", institutionCode);
            sqlQuery.addEntity(ModifyApplication.class);
            list = sqlQuery.list();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }
        return list;
    }
}
