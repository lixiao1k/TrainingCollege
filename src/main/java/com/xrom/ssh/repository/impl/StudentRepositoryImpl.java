package com.xrom.ssh.repository.impl;

import com.xrom.ssh.entity.Student;
import com.xrom.ssh.repository.StudentRepository;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentRepositoryImpl extends BaseRepositoryImpl implements StudentRepository {
    @Override
    public Student load(Long id) {
        return (Student) getCurrentSession().load(Student.class, id);
    }

    @Override
    public Student get(Long id) {
        return (Student) getCurrentSession().get(Student.class, id);
    }

    @Override
    public List<Student> findAll() {
        Session session = null;
        Transaction tx = null;
        List<Student> list = null;
        try {
            session = getCurrentSession();
            tx = session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery("select * from student");
            sqlQuery.addEntity(Student.class);
            list = sqlQuery.list();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }
        return list;
    }

    @Override
    public void persist(Student entity) {
        getCurrentSession().persist(entity);
    }

    @Override
    public Long save(Student entity) {
        return (Long) getCurrentSession().save(entity);
    }

    @Override
    public void saveOrUpdate(Student entity) {
    }

    @Override
    public void delete(Long id) {
        Student student = load(id);
        getCurrentSession().delete(student);
        flush();
    }

    @Override
    public void flush() {
        getCurrentSession().flush();
    }

    @Override
    public void update(Student entity) {
        getCurrentSession().update(entity);
        flush();
    }

    @Override
    public Student getStudent(String mail) {
        Session session = null;
        Transaction tx = null;
        List<Student> list = null;
        try {
            session = getCurrentSession();
            tx = session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery("select * from student where email=:EM");
            sqlQuery.setString("EM", mail);
            sqlQuery.addEntity(Student.class);
            list = sqlQuery.list();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }
        if(list == null || list.size() == 0){
            return null;
        }else {
            return list.get(0);
        }
    }

    @Override
    public Student getStudent(String mail, String password) {
        Session session = null;
        Transaction tx = null;
        List<Student> list = null;
        try {
            session = getCurrentSession();
            tx = session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery("select * from student where email=:EM and " +
                    "password=:PW");
            sqlQuery.setString("EM", mail);
            sqlQuery.setString("PW",password);
            sqlQuery.addEntity(Student.class);
            list = sqlQuery.list();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }
        if(list == null || list.size() == 0){
            return null;
        }else {
            return list.get(0);
        }
    }
}
