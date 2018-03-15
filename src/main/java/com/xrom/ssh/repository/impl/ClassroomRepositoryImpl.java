package com.xrom.ssh.repository.impl;

import com.xrom.ssh.entity.Classroom;
import com.xrom.ssh.entity.RegisterApplication;
import com.xrom.ssh.repository.ClassroomRepository;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClassroomRepositoryImpl extends BaseRepositoryImpl implements ClassroomRepository {

    @Override
    public Classroom load(Long id) {
        return (Classroom) getCurrentSession().load(Classroom.class, id);
    }

    @Override
    public Classroom get(Long id) {
        return (Classroom) getCurrentSession().get(Classroom.class, id);
    }

    @Override
    public List<Classroom> findAll() {
        Session session = null;
        Transaction tx = null;
        List<Classroom> list = null;
        try {
            session = getCurrentSession();
            tx = session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery("select * from class");
            sqlQuery.addEntity(Classroom.class);
            list = sqlQuery.list();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }
        return list;
    }

    @Override
    public List<Classroom> findAll(Long courseId) {
        Session session = null;
        Transaction tx = null;
        List<Classroom> list = null;
        try {
            session = getCurrentSession();
            tx = session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery("select * from class where course_id=:CI");
            sqlQuery.setLong("CI", courseId);
            sqlQuery.addEntity(Classroom.class);
            list = sqlQuery.list();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }
        return list;
    }

//    @Override
//    public Classroom getClassroom(Long sid, Long courseId) {
//        Session session = null;
//        Transaction tx = null;
//        List<Classroom> list = null;
//        try {
//            session = getCurrentSession();
//            tx = session.beginTransaction();
//            SQLQuery sqlQuery = session.createSQLQuery("select a.id from class a, course b, orders c where " +
//                    "c.student_id=:SID and c.class_id=a.id and a.course_id=b.id and b.id=:CID");
//            sqlQuery.setLong("SID", sid);
//            sqlQuery.setLong("CID", courseId);
//            sqlQuery.addEntity(Classroom.class);
//            list = sqlQuery.list();
//            tx.commit();
//        }catch (Exception e){
//            tx.rollback();
//        }
//        return list;
//    }


    @Override
    public void persist(Classroom entity) {
        getCurrentSession().persist(entity);
    }

    @Override
    public Long save(Classroom entity) {
        return (Long) getCurrentSession().save(entity);
    }

    @Override
    public void saveOrUpdate(Classroom entity) {

    }

    @Override
    public void delete(Long id) {
        Classroom classroom = get(id);
        getCurrentSession().delete(classroom);
    }

    @Override
    public void flush() {
        getCurrentSession().flush();
    }

    @Override
    public void update(Classroom entity) {
        getCurrentSession().update(entity);
    }
}
