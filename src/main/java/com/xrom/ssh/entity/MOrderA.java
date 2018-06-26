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

    @Column(name = "totalMoney", columnDefinition = "INT default 0")
    private int totalMoney;

    @Column(name = "totalOrders", columnDefinition = "INT default 0")
    private int totalOrders;

    @Column(name = "totalInstitution", columnDefinition = "INT default 0")
    private int totalInstitution;

    @Column(name = "totalStudents", columnDefinition = "INT default 0")
    private int totalStudents;

}
