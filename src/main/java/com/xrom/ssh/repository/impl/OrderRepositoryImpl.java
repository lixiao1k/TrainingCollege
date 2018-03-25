package com.xrom.ssh.repository.impl;

import com.xrom.ssh.entity.LearnSign;
import com.xrom.ssh.entity.Order;
import com.xrom.ssh.repository.OrderRepository;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepositoryImpl extends BaseRepositoryImpl implements OrderRepository {
    @Override
    public List<Order> findAll(Long studentId) {
        Session session = null;
        Transaction tx = null;
        List<Order> orders= null;
        try {
            session = getCurrentSession();
            tx = session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery("select * from orders where student_id=:SID");
            sqlQuery.setLong("SID", studentId);
            sqlQuery.addEntity(Order.class);
            orders = sqlQuery.list();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }
        return orders;
    }

    @Override
    public List<Order> findClassOrders(Long classId) {
        Session session = null;
        Transaction tx = null;
        List<Order> orders= null;
        try {
            session = getCurrentSession();
            tx = session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery("select * from orders where class_id=:CID");
            sqlQuery.setLong("CID", classId);
            sqlQuery.addEntity(Order.class);
            orders = sqlQuery.list();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }
        return orders;
    }

    @Override
    public Order getOrder(Long classId, Long studentId) {
        Session session = null;
        Transaction tx = null;
        List<Order> orders= null;
        try {
            session = getCurrentSession();
            tx = session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery("select * from orders where class_id=:CID and " +
                    "student_id=:SID");
            sqlQuery.setLong("CID", classId);
            sqlQuery.setLong("SID", studentId);
            sqlQuery.addEntity(Order.class);
            orders = sqlQuery.list();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }
        if(orders == null || orders.size() == 0){
            return null;
        }else {
            return orders.get(0);
        }
    }

    @Override
    public Order load(Long id) {
        return (Order) getCurrentSession().load(Order.class, id);
    }

    @Override
    public Order get(Long id) {
        return (Order) getCurrentSession().get(Order.class, id);
    }

    @Override
    public List<Order> findAll() {
        System.out.println("Task");
        Session session = null;
        Transaction tx = null;
        List<Order> orders= null;
        try {
            session = getCurrentSession();
            tx = session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery("select * from orders");
            sqlQuery.addEntity(Order.class);
            orders = sqlQuery.list();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }
        System.out.println("Task1");
        return orders;
    }

    @Override
    public void persist(Order entity) {
        getCurrentSession().persist(entity);
    }

    @Override
    public Long save(Order entity) {
        return (Long) getCurrentSession().save(entity);
    }

    @Override
    public void saveOrUpdate(Order entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void flush() {
        getCurrentSession().flush();
    }

    @Override
    public void update(Order entity) {
        getCurrentSession().update(entity);
    }
}
