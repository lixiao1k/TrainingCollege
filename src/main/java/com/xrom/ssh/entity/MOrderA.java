package com.xrom.ssh.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.security.acl.LastOwnerException;

//平台信息实体类，包含属性有平台总成交额、总成交订单数
@Data
@Entity
@Table(name = "MOrderA")
public class MOrderA implements Serializable{

    private static final long serialVersionUID = 7338L;

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "totalMoney")
    private int totalMoney;

    @Column(name = "totalOrders")
    private int totalOrders;

    @Column(name = "totalInstitution")
    private int totalInstitution;

    @Column(name = "totalStudents")
    private int totalStudents;

}
