package com.xrom.ssh.repository.impl;

import com.xrom.ssh.entity.ModifyApplication;
import com.xrom.ssh.entity.RegisterApplication;
import com.xrom.ssh.repository.RegisterApplicationRepository;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RegisterApplicationRepositoryImpl extends BaseRepositoryImpl implements RegisterApplicationRepository {
    @Override
    public void agree(Long id) {
        RegisterApplication application = get(id);
        application.setIs_agreed(1);
        application.setIs_rejected(0);
        update(application);
        flush();
    }

    @Override
    public void reject(Long id) {
        RegisterApplication application = get(id);
        application.setIs_rejected(1);
        application.setIs_agreed(0);
        update(application);
    }

    @Override
    public RegisterApplication load(Long id) {
        return (RegisterApplication) getCurrentSession().load(RegisterApplication.class, id);
    }

    @Override
    public RegisterApplication get(Long id) {
        return (RegisterApplication) getCurrentSession().get(RegisterApplication.class, id);
    }

    @Override
    public List<RegisterApplication> findAll() {
        Session session = null;
        Transaction tx = null;
        List<RegisterApplication> list = null;
        try {
            session = getCurrentSession();
            tx = session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery("select * from application_register");
            sqlQuery.addEntity(RegisterApplication.class);
            list = sqlQuery.list();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }
        return list;
    }

    @Override
    public void persist(RegisterApplication entity) {
        getCurrentSession().persist(entity);
    }

    @Override
    public Long save(RegisterApplication entity) {
        return (Long) getCurrentSession().save(entity);
    }

    @Override
    public void saveOrUpdate(RegisterApplication entity) {

    }

    @Override
    public void delete(Long id) {
        RegisterApplication application = get(id);
        getCurrentSession().delete(application);
    }

    @Override
    public void flush() {
        getCurrentSession().flush();
    }

    @Override
    public void update(RegisterApplication entity) {
        getCurrentSession().update(entity);
    }
}
