package com.xrom.ssh.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


//总订单统计
@Data
@Entity
@Table(name = "IOrderA")
public class IOrderA implements Serializable{
    private static final long serialVersionUID = 7731238L;

    @Id
    @Column(name = "code", length = 7, nullable = false, unique = true)
    private String code;

    //总学员数
    @Column(name = "studentAmount", columnDefinition = "INT default 0")
    private int studentAmount;

    //总成交订单数
    @Column(name = "payedOrder", columnDefinition = "INT default 0")
    private int payedOrder;

    //总订单数，是总成交订单数与总退单数的总和
    @Column(name = "allOrder", columnDefinition = "INT default 0")
    private int allOrder;

    //总退单数
    @Column(name = "brokenOrder", columnDefinition = "INT default 0")
    private int brokenOrder;

    //线上购买订单总数
    @Column(name = "payedOnline", columnDefinition = "INT default 0")
    private int payedOnline;

    //线下购买订单总数
    @Column(name = "payedOffline", columnDefinition = "INT default 0")
    private int payedOffline;

    //总订单金额
    @Column(name = "totalPrice", columnDefinition = "INT default 0")
    private int totalPrice;
}
