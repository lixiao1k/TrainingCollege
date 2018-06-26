package com.xrom.ssh.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

//平台月份统计信息纵览
@Data
@Entity
@Table(name = "MOrderMonthA")
public class MOrderMonthA implements Serializable{

    private static final long serialVersionUID = 7567238L;

    @Id
    @Column(name = "month")
    private int month;

    @Column(name = "totalMoney", columnDefinition = "INT default 0")
    private int totalMoney;

    @Column(name = "totalOrders", columnDefinition = "INT default 0")
    private int totalOrders;

    @Column(name = "newStudents", columnDefinition = "INT default 0")
    private int newStudents;

    @Column(name = "newInstitutions", columnDefinition = "INT default 0")
    private int newInstitutions;

}
