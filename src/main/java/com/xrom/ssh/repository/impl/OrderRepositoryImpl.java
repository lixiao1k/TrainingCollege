package com.xrom.ssh.repository.impl;

import com.xrom.ssh.entity.*;
import com.xrom.ssh.repository.OrderRepository;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
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

    //@管理信息系统
    //当支付了Order时，就应该更新订单的分析数据
    @Override
    public void payOrder(Order order){
        Date payedTime = order.getPayedTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(payedTime);
        int year = calendar.get(Calendar.YEAR);
        int season = (int)Math.floor(calendar.get(Calendar.MONTH)/3) + (year - 2000)*4;
        int month = (year - 2000)*12 + calendar.get(Calendar.MONTH);
        Session session = getCurrentSession();
        SOrderYearA sOrderYearA = (SOrderYearA) session
                .createQuery("from SOrderYearA where sid = :SID and year = :YEAR")
                .setParameter("SID", order.getStudentId())
                .setParameter("YEAR", year)
                .uniqueResult();
        SOrderSeasonA sOrderSeasonA = (SOrderSeasonA) session
                .createQuery("from SOrderSeasonA where sid = :SID and season = :SEASON")
                .setParameter("SID", order.getStudentId())
                .setParameter("SEASON", season)
                .uniqueResult();
        SOrderMonthA sOrderMonthA = (SOrderMonthA) session
                .createQuery("from SOrderMonthA where sid = :SID and month = :MONTH")
                .setParameter("SID", order.getStudentId())
                .setParameter("MONTH", month)
                .uniqueResult();
        if(sOrderYearA != null){
            System.out.println("不等于null");
            System.out.println(sOrderYearA);
            Transaction tx = session.beginTransaction();
            session.createQuery("update SOrderYearA yearA set yearA.payedOrder = :PAYED, yearA.totalPrice = :TOTAL, " +
                    "yearA.perPrice = :PER where yearA.sid = :SID and yearA.year = :YEAR")
                    .setParameter("PAYED",sOrderYearA.getPayedOrder()+1)
                    .setParameter("TOTAL", sOrderYearA.getTotalPrice() + order.getPrice())
                    .setParameter("PER", (sOrderYearA.getTotalPrice() + order.getPrice())/(sOrderYearA.getPayedOrder() + 1))
                    .setParameter("SID", order.getStudentId())
                    .setParameter("YEAR", year)
                    .executeUpdate();
            tx.commit();
        }else {
            System.out.println("等于null");
            Transaction tx = session.beginTransaction();
            sOrderYearA = new SOrderYearA();
            sOrderYearA.setSid(order.getStudentId());
            sOrderYearA.setPayedOrder(1);
            sOrderYearA.setTotalPrice(order.getPrice());
            sOrderYearA.setPerPrice(order.getPrice());
            sOrderYearA.setYear(year);
            session.save(sOrderYearA);
            tx.commit();
        }
        if(sOrderSeasonA != null){
            Transaction tx = session.beginTransaction();
            session.createQuery("update SOrderSeasonA seasonA set seasonA.payedOrder = :PAYED, seasonA.totalPrice = :TOTAL, " +
                    "seasonA.perPrice = :PER where seasonA.sid = :SID and seasonA.season = :SEASON")
                    .setParameter("PAYED", sOrderSeasonA.getPayedOrder() + 1)
                    .setParameter("TOTAL", sOrderSeasonA.getTotalPrice() + order.getPrice())
                    .setParameter("PER", (sOrderSeasonA.getTotalPrice() + order.getPrice())/(sOrderSeasonA.getPayedOrder() + 1))
                    .setParameter("SID", order.getStudentId())
                    .setParameter("SEASON", season)
                    .executeUpdate();
            tx.commit();
        }else {
            Transaction tx = session.beginTransaction();
            sOrderSeasonA = new SOrderSeasonA();
            sOrderSeasonA.setPayedOrder(1);
            sOrderSeasonA.setSid(order.getStudentId());
            sOrderSeasonA.setTotalPrice(order.getPrice());
            sOrderSeasonA.setPerPrice(order.getPrice());
            sOrderSeasonA.setSeason(season);
            session.save(sOrderSeasonA);
            tx.commit();
        }
        if(sOrderMonthA != null){
            Transaction tx = session.beginTransaction();
            session.createQuery("update SOrderMonthA monthA set monthA.payedOrder = :PAYED, monthA.totalPrice = :TOTAL, " +
                    "monthA.perPrice = :PER where monthA.sid = :SID and monthA.month = :MONTH")
                    .setParameter("PAYED", sOrderMonthA.getPayedOrder() + 1)
                    .setParameter("TOTAL", sOrderMonthA.getTotalPrice() + order.getPrice())
                    .setParameter("PER", (sOrderMonthA.getTotalPrice() + order.getPrice())/(sOrderMonthA.getPayedOrder() + 1))
                    .setParameter("SID", order.getStudentId())
                    .setParameter("MONTH", month)
                    .executeUpdate();
            tx.commit();
        }else {
            Transaction tx = session.beginTransaction();
            sOrderMonthA = new SOrderMonthA();
            sOrderMonthA.setPayedOrder(1);
            sOrderMonthA.setSid(order.getStudentId());
            sOrderMonthA.setTotalPrice(order.getPrice());
            sOrderMonthA.setPerPrice(order.getPrice());
            sOrderMonthA.setMonth(month);
            session.save(sOrderMonthA);
            tx.commit();
        }

    }
}










