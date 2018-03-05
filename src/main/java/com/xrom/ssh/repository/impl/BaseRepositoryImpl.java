package com.xrom.ssh.repository.impl;

import com.xrom.ssh.entity.Course;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BaseRepositoryImpl {
    @Autowired
    private SessionFactory sessionFactory;

    protected Session getCurrentSession(){
        return this.sessionFactory.getCurrentSession();
    }
}
