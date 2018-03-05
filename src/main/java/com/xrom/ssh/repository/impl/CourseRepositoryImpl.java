package com.xrom.ssh.repository.impl;

import com.xrom.ssh.entity.Course;
import com.xrom.ssh.repository.CourseRepository;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseRepositoryImpl extends BaseRepositoryImpl implements CourseRepository {


    @Override
    public Course load(Long id) {
        return (Course) getCurrentSession().load(Course.class, id);
    }

    @Override
    public Course get(Long id) {
        return (Course) getCurrentSession().get(Course.class, id);
    }



    @Override
    public List<Course> findAll() {
        Session session = null;
        Transaction tx = null;
        List<Course> list = null;
        try {
            session = getCurrentSession();
            tx = session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery("select * from course");
            sqlQuery.addEntity(Course.class);
            list = sqlQuery.list();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }
        return list;
    }

    @Override
    public void persist(Course entity) {
        getCurrentSession().persist(entity);
    }

    @Override
    public Long save(Course entity) {
        return (Long) getCurrentSession().save(entity);
    }

    @Override
    public void saveOrUpdate(Course entity) {

    }

    @Override
    public void delete(Long id) {
        Course course = get(id);
        getCurrentSession().delete(course);

    }

    @Override
    public void flush() {
        getCurrentSession().flush();
    }

    @Override
    public void update(Course entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public List<Course> findAllByType(String type) {
        Session session = null;
        Transaction tx = null;
        List<Course> list = null;
        try {
            session = getCurrentSession();
            tx = session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery("select * from course where type=:tn");
            sqlQuery.setString("tn", type);
            sqlQuery.addEntity(Course.class);
            list = sqlQuery.list();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }
        return list;
    }

    @Override
    public List<Course> findAll(String institution) {
        Session session = null;
        Transaction tx = null;
        List<Course> list = null;
        try {
            session = getCurrentSession();
            tx = session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery("select * from course where institution_code=:ic ");
            sqlQuery.setString("ic", institution);
            sqlQuery.addEntity(Course.class);
            list = sqlQuery.list();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }
        return list;
    }
}
