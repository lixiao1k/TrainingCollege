package com.xrom.ssh.repository.impl;

import com.xrom.ssh.entity.Account;
import com.xrom.ssh.repository.AccountRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession(){
        return this.sessionFactory.getCurrentSession();
    }


    @Override
    public Account load(Long id) {
        return (Account) getCurrentSession().load(Account.class, id);
    }

    @Override
    public Account get(Long id) {
        return (Account) getCurrentSession().get(Account.class,id);
    }

    @Override
    public List<Account> findAll() {
        return null;
    }

    @Override
    public void persist(Account entity) {
        getCurrentSession().persist(entity);
    }

    @Override
    public Long save(Account entity) {
        return (Long) getCurrentSession().save(entity);
    }

    @Override
    public void saveOrUpdate(Account entity) {

    }

    @Override
    public void delete(Long id) {
        Account account = load(id);
        getCurrentSession().delete(account);
    }

    @Override
    public void flush() {
        getCurrentSession().flush();
    }

    @Override
    public void update(Account entity) {
        getCurrentSession().update(entity);
    }
}
