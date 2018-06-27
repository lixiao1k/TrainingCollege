package com.xrom.ssh.repository.impl;

import com.xrom.ssh.entity.MOrderA;
import com.xrom.ssh.entity.MOrderMonthA;
import com.xrom.ssh.entity.Student;
import com.xrom.ssh.repository.StudentRepository;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
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
        addStudentA(entity);
        return (Long) getCurrentSession().save(entity);
    }

    //@管理信息系统
    private void addStudentA(Student entity){
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        int year = calendar.get(Calendar.YEAR);
        int month =  (year - 2000)*12 + calendar.get(Calendar.MONTH);

        Session session = getCurrentSession();
        MOrderMonthA mOrderMonthA = (MOrderMonthA) session.createQuery("from MOrderMonthA where month = :MONTH")
                .setParameter("MONTH", month)
                .uniqueResult();
        if(mOrderMonthA != null){
            Transaction tx = session.beginTransaction();
            session.createQuery("update MOrderMonthA monthA set monthA.newStudents = :STUDENT " +
                    "where monthA.month = :MONTH")
                    .setParameter("STUDENT", mOrderMonthA.getNewStudents() + 1)
                    .setParameter("MONTH", month)
                    .executeUpdate();
            tx.commit();
        }else {
            Transaction tx = session.beginTransaction();
            mOrderMonthA = new MOrderMonthA();
            mOrderMonthA.setMonth(month);
            mOrderMonthA.setNewStudents(1);
            tx.commit();
        }

        MOrderA mOrderA = (MOrderA) session.createQuery("from MOrderA where id = 1").uniqueResult();
        if(mOrderA != null){
            Transaction tx = session.beginTransaction();
            session.createQuery("update MOrderA orderA set orderA.totalStudents = :STUDENTS " +
                    "where id = 1")
                    .setParameter("STUDENTS", mOrderA.getTotalStudents() + 1)
                    .executeUpdate();
            tx.commit();
        }else {
            Transaction tx = session.beginTransaction();
            mOrderA = new MOrderA();
            mOrderA.setTotalStudents(1);
            mOrderA.setId((long) 1);
            session.save(mOrderA);
            tx.commit();
        }
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
