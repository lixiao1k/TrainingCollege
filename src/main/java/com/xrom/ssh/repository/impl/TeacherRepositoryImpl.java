package com.xrom.ssh.repository.impl;

import com.xrom.ssh.entity.ITeacherA;
import com.xrom.ssh.entity.Teacher;
import com.xrom.ssh.repository.TeacherRepository;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeacherRepositoryImpl extends BaseRepositoryImpl implements TeacherRepository {

    @Override
    public Teacher load(Long id) {
        return (Teacher) getCurrentSession().load(Teacher.class, id);
    }

    @Override
    public Teacher get(Long id) {
        return (Teacher) getCurrentSession().get(Teacher.class, id);
    }

    @Override
    public List<Teacher> findAll() {
        return null;
    }

    @Override
    public void persist(Teacher entity) {
        getCurrentSession().persist(entity);
    }

    @Override
    public Long save(Teacher entity) {
        return (Long) getCurrentSession().save(entity);
    }

    @Override
    public void saveOrUpdate(Teacher entity) {

    }

    @Override
    public void delete(Long id) {
        Teacher teacher = get(id);
        getCurrentSession().delete(teacher);
    }

    @Override
    public void flush() {
        getCurrentSession().flush();
    }

    @Override
    public void update(Teacher entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public List<Teacher> findTeachersOfInstitution(String institutionCode) {
        Session session = null;
        Transaction tx = null;
        List<Teacher> list = null;
        try {
            session = getCurrentSession();
            tx = session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery("select * from teacher where institution_code=:code ");
            sqlQuery.setString("code", institutionCode);
            sqlQuery.addEntity(Teacher.class);
            list = sqlQuery.list();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ITeacherA getTeacherA(Long tid){
        Session session = getCurrentSession();
        ITeacherA iTeacherA = (ITeacherA) session.createQuery("from ITeacherA where tid = :TID")
                .setParameter("TID", tid)
                .uniqueResult();
        return iTeacherA;
    }
}
