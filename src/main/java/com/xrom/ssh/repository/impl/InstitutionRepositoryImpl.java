package com.xrom.ssh.repository.impl;

import com.xrom.ssh.entity.*;
import com.xrom.ssh.repository.InstitutionRepository;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.xml.crypto.Data;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public class InstitutionRepositoryImpl extends BaseRepositoryImpl implements InstitutionRepository {

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

    @Override
    public Institution get(String code, String password) {
        Session session = null;
        Transaction tx = null;
        List<Institution> list = null;
        try {
            session = getCurrentSession();
            tx = session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery("select * from institution where code=:CODE and password=:PW");
            sqlQuery.setString("CODE", code);
            sqlQuery.setString("PW", password);
            sqlQuery.addEntity(Institution.class);
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

    //@管理信息系统
    @Override
    public IOrderA getIOrderA(String code) {
        Session session = getCurrentSession();
        IOrderA iOrderA = (IOrderA) session.createQuery("from IOrderA where code = :CODE")
                .setParameter("CODE", code)
                .uniqueResult();
        return iOrderA;
    }

    //@管理信息系统
    public void addInstitution(RegisterApplication institution){
        Session session = getCurrentSession();
        MAreaA mAreaA = (MAreaA) session.createQuery("from MAreaA where province = :PROVINCE")
                .setParameter("PROVINCE", institution.getAddress())
                .uniqueResult();
        if(mAreaA != null){
            Transaction tx = session.beginTransaction();
            session.createQuery("update MAreaA areaA set areaA.totalInstitutions = :INSTITUTION " +
                    "where areaA.province = :PROVINCE")
                    .setParameter("INSTITUTION", mAreaA.getTotalInstitutions() + 1)
                    .setParameter("PROVINCE", institution.getAddress())
                    .executeUpdate();
            tx.commit();
        }else{
            mAreaA = new MAreaA();
            mAreaA.setProvince(institution.getAddress());
            mAreaA.setTotalInstitutions(1);
        }


        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        int year = calendar.get(Calendar.YEAR);
        int month =  (year - 2000)*12 + calendar.get(Calendar.MONTH);

        //更新月度新增机构数
        MOrderMonthA mOrderMonthA = (MOrderMonthA) session.createQuery("from MOrderMonthA where month = :MONTH")
                .setParameter("MONTH", month)
                .uniqueResult();
        if(mOrderMonthA != null){
            Transaction tx = session.beginTransaction();
            session.createQuery("update MOrderMonthA monthA set monthA.newInstitutions = :INSTITUTION " +
                    "where monthA.month = :MONTH")
                    .setParameter("INSTITUTION", mOrderMonthA.getNewInstitutions() + 1)
                    .setParameter("MONTH", month)
                    .executeUpdate();
            tx.commit();
        }else {
            Transaction tx = session.beginTransaction();
            mOrderMonthA = new MOrderMonthA();
            mOrderMonthA.setMonth(month);
            mOrderMonthA.setNewInstitutions(1);
            tx.commit();
        }

        MOrderA mOrderA = (MOrderA) session.createQuery("from MOrderA where id=1").uniqueResult();
        if(mOrderA != null){
            Transaction tx = session.beginTransaction();
            session.createQuery("update MOrderA orderA set orderA.totalInstitution = :INSTITUTION " +
                    "where orderA.id = 1")
                    .setParameter("INSTITUTION", mOrderA.getTotalInstitution() + 1)
                    .executeUpdate();
            tx.commit();
        }else {
            Transaction tx = session.beginTransaction();
            mOrderA = new MOrderA();
            mOrderA.setId((long) 1);
            mOrderA.setTotalInstitution(1);
            tx.commit();
        }
    }
}
