package com.xrom.ssh.entity;

import javax.persistence.Column;
import javax.persistence.Id;

//平台月份统计信息纵览
public class MOrderMonthA {

    @Id
    @Column(name = "month")
    private int month;

    @Column(name = "totalMoney")
    private int totalMoney;

    @Column(name = "totalOrders")
    private int totalOrders;

    @Column(name = "newStudents")
    private int newStudents;

    @Column(name = "newInstitutions")
    private int newInstitutions;

}
