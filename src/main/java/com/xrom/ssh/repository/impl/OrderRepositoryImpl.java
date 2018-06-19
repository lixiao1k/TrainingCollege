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
                    "yearA.perPrice = :PER, yearA.brokenOrder = :BROKEN where yearA.sid = :SID and yearA.year = :YEAR")
                    .setParameter("PAYED",sOrderYearA.getPayedOrder() + (pay ? 1 : -1))//根据付款或者是退课做出不同的操作
                    .setParameter("TOTAL", sOrderYearA.getTotalPrice() + (pay ? order.getPrice() : -order.getPrice()))
                    .setParameter("PER", (sOrderYearA.getTotalPrice() + (pay ? order.getPrice() : -order.getPrice()))/(sOrderYearA.getPayedOrder() + (pay ? 1 : -1)))
                    .setParameter("SID", order.getStudentId())
                    .setParameter("YEAR", year)
                    .setParameter("BROKEN",sOrderYearA.getBrokenOrder() + (pay ? 0 : 1))
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
                    "seasonA.perPrice = :PER, seasonA.brokenOrder = :BROKEN where seasonA.sid = :SID and seasonA.season = :SEASON")
                    .setParameter("PAYED", sOrderSeasonA.getPayedOrder() + (pay ? 1 : -1))
                    .setParameter("TOTAL", sOrderSeasonA.getTotalPrice() + (pay ? order.getPrice() : -order.getPrice()))
                    .setParameter("PER", (sOrderSeasonA.getTotalPrice() + (pay ? order.getPrice() : -order.getPrice()))/(sOrderSeasonA.getPayedOrder() + (pay ? 1 : -1)))
                    .setParameter("SID", order.getStudentId())
                    .setParameter("SEASON", season)
                    .setParameter("BROKEN", sOrderSeasonA.getBrokenOrder() + (pay ? 0 : 1))
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
                    "monthA.perPrice = :PER, monthA.brokenOrder = :BROKEN where monthA.sid = :SID and monthA.month = :MONTH")
                    .setParameter("PAYED", sOrderMonthA.getPayedOrder() + (pay ? 1 : -1))
                    .setParameter("TOTAL", sOrderMonthA.getTotalPrice() + (pay ? order.getPrice() : -order.getPrice()))
                    .setParameter("PER", (sOrderMonthA.getTotalPrice() + (pay ? order.getPrice() : -order.getPrice()))/(sOrderMonthA.getPayedOrder() + (pay ? 1 : -1)))
                    .setParameter("SID", order.getStudentId())
                    .setParameter("MONTH", month)
                    .setParameter("BROKEN", sOrderMonthA.getBrokenOrder() + (pay ? 0 : 1))
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
    //更新机构的订单统计信息
    @Override
    public void payOrBreakOrder4Institution(Order order, boolean pay){
        Date payedTime = order.getPayedTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(payedTime);
        int year = calendar.get(Calendar.YEAR);
        int season = (int)Math.floor(calendar.get(Calendar.MONTH)/4) + (year - 2000)*4;
        int month = (year - 2000)*12 + calendar.get(Calendar.MONTH);
        Classroom classroom = classroomRepository.get(order.getClassId());
        Course course = courseRepository.get(classroom.getCourseId());
        Session session = getCurrentSession();
        IOrderYearA iOrderYearA = (IOrderYearA) session
                .createQuery("from IOrderYearA where code = :CODE and year = :YEAR")
                .setParameter("CODE", course.getInstitutionCode())
                .setParameter("YEAR", year)
                .uniqueResult();

        IOrderSeasonA iOrderSeasonA = (IOrderSeasonA) session
                .createQuery("from IOrderSeasonA where code = :CODE and season = :SEASON")
                .setParameter("CODE", course.getInstitutionCode())
                .setParameter("SEASON", season)
                .uniqueResult();

        IOrderMonthA iOrderMonthA = (IOrderMonthA) session
                .createQuery("from IOrderMonthA where code = :CODE and month = :MONTH")
                .setParameter("CODE", course.getInstitutionCode())
                .setParameter("MONTH", month)
                .uniqueResult();

        if(iOrderYearA != null){
            Transaction tx = session.beginTransaction();
            session.createQuery("update IOrderYearA yearA set yearA.allOrder = :ALLORDER, " +
                    "yearA.brokenOrder = :BROKENORDER, yearA.payedOffline = :OFFLINE, " +
                    "yearA.payedOnline = :ONLINE, yearA.payedOrder = :PAYED, yearA.totalPrice = :TOTALPRICE" +
                    " where yearA.code = :CODE and yearA.year = :YEAR")
                    .setParameter("ALLORDER", iOrderYearA.getAllOrder() + (pay ? 1 : 0))
                    .setParameter("BROKENORDER", iOrderYearA.getBrokenOrder() + (pay ? 0 : 1))
                    .setParameter("OFFLINE", iOrderYearA.getPayedOffline() + (order.getIsPayedOffline() == 1 && pay ? 1 : 0))
                    .setParameter("ONLINE", iOrderYearA.getPayedOnline() + (order.getIsPayedOffline() == 0 && pay ? 1 : 0))
                    .setParameter("PAYED", iOrderYearA.getPayedOrder() + (pay ? 1 : -1))
                    .setParameter("TOTALPRICE", iOrderYearA.getTotalPrice() + (pay ? order.getPrice() : -order.getPrice()))
                    .setParameter("CODE", course.getInstitutionCode())
                    .setParameter("YEAR", year)
                    .executeUpdate();
            tx.commit();
        }else if(pay){
            Transaction tx = session.beginTransaction();
            iOrderYearA = new IOrderYearA();
            iOrderYearA.setCode(course.getInstitutionCode());
            iOrderYearA.setYear(year);
            iOrderYearA.setAllOrder(1);
            iOrderYearA.setPayedOrder(1);
            iOrderYearA.setTotalPrice(order.getPrice());
            if(order.getIsPayedOffline() == 1){
                iOrderYearA.setPayedOffline(1);
            }else {
                iOrderYearA.setPayedOnline(1);
            }
            session.save(iOrderYearA);
            tx.commit();
        }

        if(iOrderSeasonA != null){
            Transaction tx = session.beginTransaction();
            session.createQuery("update IOrderSeasonA seasonA set seasonA.allOrder = :ALLORDER, " +
                    "seasonA.brokenOrder = :BROKENORDER, seasonA.payedOffline = :OFFLINE, " +
                    "seasonA.payedOnline = :ONLINE, seasonA.payedOrder = :PAYED, seasonA.totalPrice = :TOTALPRICE" +
                    " where seasonA.code = :CODE and seasonA.season = :SEASON")
                    .setParameter("ALLORDER", iOrderSeasonA.getAllOrder() + (pay ? 1 : 0))
                    .setParameter("BROKENORDER", iOrderSeasonA.getBrokenOrder() + (pay ? 0 : 1))
                    .setParameter("OFFLINE", iOrderSeasonA.getPayedOffline() + (order.getIsPayedOffline() == 1 && pay ? 1 : 0))
                    .setParameter("ONLINE", iOrderSeasonA.getPayedOnline() + (order.getIsPayedOffline() == 0 && pay ? 1 : 0))
                    .setParameter("PAYED", iOrderSeasonA.getPayedOrder() + (pay ? 1 : -1))
                    .setParameter("TOTALPRICE", iOrderSeasonA.getTotalPrice() + (pay ? order.getPrice() : -order.getPrice()))
                    .setParameter("CODE", course.getInstitutionCode())
                    .setParameter("SEASON", season)
                    .executeUpdate();
            tx.commit();
        }else if(pay){
            Transaction tx = session.beginTransaction();
            iOrderSeasonA = new IOrderSeasonA();
            iOrderSeasonA.setCode(course.getInstitutionCode());
            iOrderSeasonA.setAllOrder(1);
            iOrderSeasonA.setPayedOrder(1);
            iOrderSeasonA.setSeason(season);
            iOrderSeasonA.setTotalPrice(order.getPrice());
            if(order.getIsPayedOffline() == 1){
                iOrderSeasonA.setPayedOffline(1);
            }else {
                iOrderSeasonA.setPayedOnline(1);
            }
            session.save(iOrderSeasonA);
            tx.commit();
        }

        if(iOrderMonthA != null){
            Transaction tx = session.beginTransaction();
            session.createQuery("update IOrderMonthA monthA set monthA.allOrder = :ALLORDER, " +
                    "monthA.brokenOrder = :BROKENORDER, monthA.payedOffline = :OFFLINE, " +
                    "monthA.payedOnline = :ONLINE, monthA.payedOrder = :PAYED, monthA.totalPrice = :TOTALPRICE " +
                    "where monthA.code = :CODE and monthA.month = :MONTH")
                    .setParameter("ALLORDER", iOrderMonthA.getAllOrder() + (pay ? 1 : 0))
                    .setParameter("BROKENORDER", iOrderMonthA.getBrokenOrder() + (pay ? 0 : 1))

                    .setParameter("OFFLINE", iOrderMonthA.getPayedOffline() + (order.getIsPayedOffline() == 1 && pay ? 1 : 0))
                    .setParameter("ONLINE", iOrderMonthA.getPayedOnline() + (order.getIsPayedOffline() == 0 && pay ? 1 : 0))
                    .setParameter("PAYED", iOrderMonthA.getPayedOrder() + (pay ? 1 : -1))
                    .setParameter("TOTALPRICE", iOrderMonthA.getTotalPrice() + (pay ? order.getPrice() : -order.getPrice()))
                    .setParameter("CODE", course.getInstitutionCode())
                    .setParameter("MONTH", month)
                    .executeUpdate();
            tx.commit();
        }else if (pay){
            Transaction tx = session.beginTransaction();
            iOrderMonthA = new IOrderMonthA();
            iOrderMonthA.setCode(course.getInstitutionCode());
            iOrderMonthA.setAllOrder(1);
            iOrderMonthA.setPayedOrder(1);
            iOrderMonthA.setMonth(month);
            iOrderMonthA.setTotalPrice(order.getPrice());
            if(order.getIsPayedOffline() == 1){
                iOrderMonthA.setPayedOffline(1);
            }else {
                iOrderMonthA.setPayedOnline(1);
            }
            session.save(iOrderMonthA);
            tx.commit();
        }

        //机构总订单的统计信息更新
        IOrderA iOrderA = (IOrderA) session.createQuery("from IOrderA where code = :CODE")
                .setParameter("CODE", course.getInstitutionCode())
                .uniqueResult();

        //机构学生信息统计
        IStudentA iStudentA = (IStudentA) session.createQuery("from IStudentA where sid = :SID and code = :CODE")
                .setParameter("SID", order.getStudentId())
                .setParameter("CODE", course.getInstitutionCode())
                .uniqueResult();

        if(iOrderA != null){
            Transaction tx = session.beginTransaction();
            session.createQuery("update IOrderA orderA set " +
                    "orderA.allOrder = :ALLORDER, orderA.brokenOrder = :BROKENORDER, " +
                    "orderA.payedOffline = :OFFLINE, orderA.payedOnline = :ONLINE, " +
                    "orderA.payedOrder = :PAYED, orderA.studentAmount = :STUAMOUNT, " +
                    "orderA.totalPrice = :TOTALPRICE where orderA.code = :CODE")
                    .setParameter("ALLORDER", iOrderA.getAllOrder() + (pay ? 1 : 0))
                    .setParameter("BROKENORDER", iOrderA.getBrokenOrder() + (pay ? 0 : 1))
                    .setParameter("OFFLINE", iOrderA.getPayedOffline() + (order.getIsPayedOffline() == 1 && pay ? 1 : 0))
                    .setParameter("ONLINE", iOrderA.getPayedOnline() + (order.getIsPayedOffline() == 1 && pay ? 0 : 1))
                    .setParameter("PAYED", iOrderA.getPayedOrder() + (pay ? 1 : -1))
                    .setParameter("STUAMOUNT", iOrderA.getStudentAmount() + (iStudentA == null && pay ? 1 : 0))
                    .setParameter("TOTALPRICE", iOrderA.getTotalPrice() + (pay ? order.getPrice() : -order.getPrice()))
                    .setParameter("CODE", course.getInstitutionCode())
                    .executeUpdate();
            tx.commit();
        }else if (pay){
            Transaction tx = session.beginTransaction();
            iOrderA = new IOrderA();
            iOrderA.setCode(course.getInstitutionCode());
            iOrderA.setAllOrder(1);
            iOrderA.setPayedOrder(1);
            iOrderA.setTotalPrice(order.getPrice());
            iOrderA.setStudentAmount(1);
            if(order.getIsPayedOffline() == 1){
                iOrderA.setPayedOffline(1);
            }else {
                iOrderA.setPayedOnline(1);
            }
            session.save(iOrderA);
            tx.commit();
        }

        //此机构不存在此学生记录，加入
        if(iStudentA == null){
            Transaction tx = session.beginTransaction();
            iStudentA = new IStudentA();
            iStudentA.setSid(order.getStudentId());
            iStudentA.setCode(course.getInstitutionCode());
            session.save(iStudentA);
            tx.commit();
        }

        updateCourseA4Institution(order, pay);
        updateTeacherA4Institution(order, pay);
        updateCourseType4Institution(order, pay);
    }

    //更新ICourseA表的payedOrder
    private void updateCourseA4Institution(Order order, boolean pay){
        Classroom classroom = classroomRepository.get(order.getClassId());
        Course course = courseRepository.get(classroom.getCourseId());
        Session session = getCurrentSession();
        ICourseA iCourseA = (ICourseA) session.createQuery("from ICourseA where courseId = :COURSEID")
                .setParameter("COURSEID",course.getId())
                .uniqueResult();
        if(iCourseA != null){
            Transaction tx = session.beginTransaction();
            session.createQuery("update ICourseA courseA set courseA.payedOrder = :PAYED where " +
                    "courseA.courseId = :ID")
                    .setParameter("PAYED", iCourseA.getPayedOrder() + (pay ? 1 : -1))
                    .setParameter("ID", course.getId())
                    .executeUpdate();
            tx.commit();
        }
    }


    //更新ITeacherA中的教师订单总数、总订单金额
    private void updateTeacherA4Institution(Order order, boolean pay){
        Classroom classroom = classroomRepository.get(order.getClassId());
        Session session = getCurrentSession();
        ITeacherA iTeacherA = (ITeacherA) session.createQuery("from ITeacherA where tid = :ID")
                .setParameter("ID", classroom.getTeacherId())
                .uniqueResult();
        if(iTeacherA != null){
            Transaction tx = session.beginTransaction();
            session.createQuery("update ITeacherA teacherA set teacherA.payedOrder = :PAY, " +
                    "teacherA.totalPrice = :TOTALPRICE where teacherA.tid = :ID")
                    .setParameter("PAY", iTeacherA.getPayedOrder() + (pay ? 1 : -1))
                    .setParameter("ID", classroom.getTeacherId())
                    .setParameter("TOTALPRICE", iTeacherA.getTotalPrice() + (pay ? order.getPrice() : -order.getPrice()))
                    .executeUpdate();
            tx.commit();
        }
    }

    //下单或者退课是更新ICOurseType，机构的课程类型分析表。记录课程类型的订单数和订单总额
    private void updateCourseType4Institution(Order order, boolean pay){
        Session session = getCurrentSession();
        Classroom classroom = classroomRepository.get(order.getClassId());
        Course course = courseRepository.get(classroom.getCourseId());
        String type = course.getType();
        String dbTypeName;
        if(type.equals("文")){
            dbTypeName = "wen";
        }else if (type.equals("理")){
            dbTypeName = "li";
        }else if (type.equals("工")){
            dbTypeName = "gong";
        }else if (type.equals("商")){
            dbTypeName = "shang";
        }else {
            dbTypeName = "yi";
        }

        ICourseTypeA iCourseTypeA = (ICourseTypeA) session.createQuery("from ICourseTypeA where code = :CODE")
                .setParameter("CODE", course.getInstitutionCode())
                .uniqueResult();
        if(iCourseTypeA != null){
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("update ICourseTypeA typeA set typeA." + dbTypeName + "Orders = :PAY, " +
                    "typeA." + dbTypeName + "TotalPrice = :PRICE where typeA.code = :CODE");
            if(type.equals("文")){
                query.setParameter("PAY", iCourseTypeA.getWenOrders() + (pay ? 1 : -1));
                query.setParameter("PRICE", iCourseTypeA.getWenTotalPrice() + (pay ? order.getPrice() : -order.getPrice()));
            }else if (type.equals("理")){
                query.setParameter("PAY", iCourseTypeA.getLiOrders() + (pay ? 1 : -1));
                query.setParameter("PRICE", iCourseTypeA.getLiTotalPrice() + (pay ? order.getPrice() : -order.getPrice()));
            }else if (type.equals("工")){
                query.setParameter("PAY", iCourseTypeA.getGongOrders() + (pay ? 1 : -1));
                query.setParameter("PRICE", iCourseTypeA.getGongTotalPrice() + (pay ? order.getPrice() : -order.getPrice()));
            }else if(type.equals("商")){
                query.setParameter("PAY", iCourseTypeA.getShangOrders() + (pay ? 1 : -1));
                query.setParameter("PRICE", iCourseTypeA.getShangTotalPrice() + (pay ? order.getPrice() : -order.getPrice()));
            }else {
                query.setParameter("PAY", iCourseTypeA.getYiOrders() + (pay ? 1 : -1));
                query.setParameter("PRICE", iCourseTypeA.getYiTotalPrice() + (pay ? order.getPrice() : -order.getPrice()));
            }
            query.setParameter("CODE", course.getInstitutionCode());
            query.executeUpdate();
            tx.commit();
        }else{
            Transaction tx = session.beginTransaction();
            iCourseTypeA = new ICourseTypeA();
            iCourseTypeA.setCode(course.getInstitutionCode());
            if(type.equals("文")){
                iCourseTypeA.setWenOrders(1);
                iCourseTypeA.setWenTotalPrice(order.getPrice());
            }else if (type.equals("理")){
                iCourseTypeA.setLiOrders(1);
                iCourseTypeA.setLiTotalPrice(order.getPrice());
            }else if (type.equals("工")){
                iCourseTypeA.setGongOrders(1);
                iCourseTypeA.setGongTotalPrice(order.getPrice());
            }else if (type.equals("商")){
                iCourseTypeA.setShangOrders(1);
                iCourseTypeA.setShangTotalPrice(order.getPrice());
            }else {
                iCourseTypeA.setYiOrders(1);
                iCourseTypeA.setYiTotalPrice(order.getPrice());
            }
            session.save(iCourseTypeA);
            tx.commit();
        }
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
        String dbTypeName;
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

    @Override
    public SOrderGradeA getSOrderGradeA(Long studentId){
        Session session = getCurrentSession();
        SOrderGradeA sOrderGradeA = (SOrderGradeA) session.createQuery("from SOrderGradeA gradeA where gradeA.sid = :SID")
                .setParameter("SID", studentId)
                .uniqueResult();
        return sOrderGradeA;
    }


}










