package com.xrom.ssh.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "IOrderSeasonA")
public class IOrderSeasonA implements Serializable{
    private static final long serialVersionUID = 79738L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "code", length = 7)
    private String code;


    //season = (year-2000)*4 + (int)Math.floor(month/3)
    @Column(name = "season")
    private int season;


    //总的订单数量，包括成交订单数和退单数
    @Column(name = "allOrder", columnDefinition = "INT default 0")
    private int allOrder;

    //成交的订单数
    @Column(name = "payedOrder", columnDefinition = "INT default 0")
    private int payedOrder;

    //退单订单数
    @Column(name = "brokenOrder", columnDefinition = "INT default 0")
    private int brokenOrder;

    //线上购买订单数
    @Column(name = "payedOnline", columnDefinition = "INT default 0")
    private int payedOnline;

    //线下购买订单数
    @Column(name = "payedOffline", columnDefinition = "INT default 0")
    private int payedOffline;

    //总订单金额
    @Column(name = "totalPrice", columnDefinition = "INT default 0")
    private int totalPrice;
}
