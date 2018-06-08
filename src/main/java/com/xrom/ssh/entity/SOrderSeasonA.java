package com.xrom.ssh.entity;

//@管理信息系统
//记录以季度为单位的学生订单总数、总额、平均订单单价

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "SOrderSeasonA")
public class SOrderSeasonA implements Serializable{
    private static final long serialVersionUID = 7489728L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "sid")
    private Long sid;

    //season = (year-2000)*4 + (int)Math.floor(month/3)
    @Column(name = "season")
    private int season;

    //成交订单数
    @Column(name = "payedOrder", columnDefinition = "INT default 0")
    private int payedOrder;

    //退课订单数
    @Column(name = "brokenOrder", columnDefinition = "INT default 0")
    private int brokenOrder;

    //成交订单总金额
    @Column(name = "totalPrice", columnDefinition = "INT default 0")
    private int totalPrice;

    //平均订单价格
    @Column(name = "perPrice", columnDefinition = "INT default 0")
    private int perPrice;

}
