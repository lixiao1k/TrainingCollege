package com.xrom.ssh.repository.impl;

import com.xrom.ssh.entity.*;
import com.xrom.ssh.repository.ClassroomRepository;
import com.xrom.ssh.repository.CourseRepository;
import com.xrom.ssh.repository.OrderRepository;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public class OrderRepositoryImpl extends BaseRepositoryImpl implements OrderRepository {
    @Autowired(required = true)
    private CourseRepository courseRepository;

    @Autowired(required = true)
    private ClassroomRepository classroomRepository;

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
    //@pay true是pay false是breake
    @Override
    public void payOrBreakOrder(Order order, boolean pay){
        Date payedTime = order.getPayedTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(payedTime);
        int year = calendar.get(Calendar.YEAR);
        int season = (int)Math.floor(calendar.get(Calendar.MONTH)/4) + (year - 2000)*4;
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
            Transaction tx = session.beginTransaction();
            session.createQuery("update SOrderYearA yearA set yearA.payedOrder = :PAYED, yearA.totalPrice = :TOTAL, " +
                    "yearA.perPrice = :PER where yearA.sid = :SID and yearA.year = :YEAR")
                    .setParameter("PAYED",sOrderYearA.getPayedOrder() + (pay ? 1 : -1))//根据付款或者是退课做出不同的操作
                    .setParameter("TOTAL", sOrderYearA.getTotalPrice() + (pay ? order.getPrice() : -order.getPrice()))
                    .setParameter("PER", (sOrderYearA.getTotalPrice() + (pay ? order.getPrice() : -order.getPrice()))/(sOrderYearA.getPayedOrder() + (pay ? 1 : -1)))
                    .setParameter("SID", order.getStudentId())
                    .setParameter("YEAR", year)
                    .executeUpdate();
            tx.commit();
        }else if(pay){
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
                    .setParameter("PAYED", sOrderSeasonA.getPayedOrder() + (pay ? 1 : -1))
                    .setParameter("TOTAL", sOrderSeasonA.getTotalPrice() + (pay ? order.getPrice() : -order.getPrice()))
                    .setParameter("PER", (sOrderSeasonA.getTotalPrice() + (pay ? order.getPrice() : -order.getPrice()))/(sOrderSeasonA.getPayedOrder() + (pay ? 1 : -1)))
                    .setParameter("SID", order.getStudentId())
                    .setParameter("SEASON", season)
                    .executeUpdate();
            tx.commit();
        }else if(pay){
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
                    .setParameter("PAYED", sOrderMonthA.getPayedOrder() + (pay ? 1 : -1))
                    .setParameter("TOTAL", sOrderMonthA.getTotalPrice() + (pay ? order.getPrice() : -order.getPrice()))
                    .setParameter("PER", (sOrderMonthA.getTotalPrice() + (pay ? order.getPrice() : -order.getPrice()))/(sOrderMonthA.getPayedOrder() + (pay ? 1 : -1)))
                    .setParameter("SID", order.getStudentId())
                    .setParameter("MONTH", month)
                    .executeUpdate();
            tx.commit();
        }else if (pay){
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

        //课程类型分析数据
        updateOrderTypeOfStu(order, pay);

    }

    //@管理信息系统
    //学员各类型课程订单数的比例
    private void updateOrderTypeOfStu(Order order, boolean pay){
        Classroom classroom = classroomRepository.get(order.getClassId());
        Course course = courseRepository.get(classroom.getCourseId());
        String type = course.getType();
        Session session = getCurrentSession();
        SOrderTypeA sOrderTypeA = (SOrderTypeA) session.createQuery("from SOrderTypeA where sid = :SID")
                .setParameter("SID", order.getStudentId())
                .uniqueResult();
        String dbTypeName = "";
        if(type.equals("文")){
            dbTypeName = "wenAmount";
        }else if (type.equals("理")){
            dbTypeName = "liAmount";
        }else if (type.equals("工")){
            dbTypeName = "gongAmount";
        }else if (type.equals("商")){
            dbTypeName = "shangAmount";
        }else {
            dbTypeName = "yiAmount";
        }

        if (sOrderTypeA != null){
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("update SOrderTypeA typeA set typeA." + dbTypeName + " = :AMOUNT, typeA.payedOrderTotal = :PAY, " +
                    "typeA.brokenOrderTotal = :BREAK where typeA.sid = :SID");
            if(type.equals("文")){
                query.setParameter("AMOUNT", sOrderTypeA.getWenAmount() + (pay ? 1 : -1));
            }else if (type.equals("理")){
                query.setParameter("AMOUNT", sOrderTypeA.getLiAmount() + (pay ? 1 : -1));
            }else if (type.equals("工")){
                query.setParameter("AMOUNT", sOrderTypeA.getGongAmount() + (pay ? 1 : -1));
            }else if (type.equals("商")){
                query.setParameter("AMOUNT", sOrderTypeA.getShangAmount() + (pay ? 1 : -1));
            }else {
                query.setParameter("AMOUNT", sOrderTypeA.getYiAmount() + (pay ? 1 : -1));
            }
            if(pay){
                query.setParameter("PAY", sOrderTypeA.getPayedOrderTotal() + 1);
                query.setParameter("BREAK", sOrderTypeA.getBrokenOrderTotal());
            }else {
                query.setParameter("PAY", sOrderTypeA.getPayedOrderTotal() - 1);
                query.setParameter("BREAK", sOrderTypeA.getBrokenOrderTotal() + 1);
            }
            query.setParameter("SID", order.getStudentId());
            query.executeUpdate();
            tx.commit();
        }else if(pay){
            Transaction tx = session.beginTransaction();
            sOrderTypeA = new SOrderTypeA();
            if(type.equals("文")){
                sOrderTypeA.setWenAmount(1);
            }else if (type.equals("理")){
                sOrderTypeA.setLiAmount(1);
            }else if (type.equals("工")){
                sOrderTypeA.setGongAmount(1);
            }else if (type.equals("商")){
                sOrderTypeA.setShangAmount(1);
            }else {
                sOrderTypeA.setYiAmount(1);
            }
            sOrderTypeA.setPayedOrderTotal(1);
            sOrderTypeA.setSid(order.getStudentId());
            session.save(sOrderTypeA);
            tx.commit();
        }
    }

    @Override
    public List<SOrderYearA> getSOrderYearA(Long studentId){
        Session session = getCurrentSession();
        List<SOrderYearA> list = session.createQuery("from SOrderYearA yearA where yearA.sid = :SID")
                .setParameter("SID", studentId)
                .list();
        return list;
    }

    @Override
    public List<SOrderSeasonA> getSOrderSeasonA(Long studentId){
        Session session = getCurrentSession();
        List<SOrderSeasonA> list = session.createQuery("from SOrderSeasonA seasonA where seasonA.sid = :SID")
                .setParameter("SID", studentId)
                .list();
        return list;
    }

    @Override
    public List<SOrderMonthA> getSOrderMonthA(Long studentId){
        Session session = getCurrentSession();
        List<SOrderMonthA> list =  session.createQuery("from SOrderMonthA monthA where monthA.sid = :SID")
                .setParameter("SID", studentId)
                .list();
        return list;
    }

    @Override
    public SOrderTypeA getSOrderTypeA(Long studentId){
        Session session = getCurrentSession();
        SOrderTypeA sOrderTypeA = (SOrderTypeA) session.createQuery("from SOrderTypeA typeA where typeA.sid = :SID")
                .setParameter("SID", studentId)
                .uniqueResult();
        return sOrderTypeA;
    }

}










