package com.xrom.ssh.repository.impl;

import com.xrom.ssh.entity.Institution;
import com.xrom.ssh.repository.InstitutionRepository;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InstitutionRepositoryImpl implements InstitutionRepository {
    @Autowired
    private SessionFactory sessionFactory;


    private Session getCurrentSession(){
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public Institution load(String id) {
        return (Institution) getCurrentSession().load(Institution.class, id);
    }

    @Override
    public Institution get(String id) {
        return (Institution) getCurrentSession().get(Institution.class, id);
    }

    @Override
    public List<Institution> findAll() {
        Session session = null;
        Transaction tx = null;
        List<Institution> list = null;
        try {
            session = getCurrentSession();
            tx = session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery("select * from institution");
            sqlQuery.addEntity(Institution.class);
            list = sqlQuery.list();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }
        return list;
    }

    @Override
    public void persist(Institution entity) {
        getCurrentSession().persist(entity);

    }

    @Override
    public String save(Institution entity) {
        return (String) getCurrentSession().save(entity);
    }

    @Override
    public void saveOrUpdate(Institution entity) {

    }

    @Override
    public void delete(String id) {
        Institution institution = get(id);
        getCurrentSession().delete(institution);
    }

    @Override
    public void flush() {
        getCurrentSession().flush();
    }

    @Override
    public void update(Institution entity) {
        getCurrentSession().update(entity);
    }
}
