package com.xrom.ssh.entity;

//@管理信息系统
//记录以月为单位的学生订单总数、总额、平均订单单价

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "SOrderMonthA")
public class SOrderMonthA implements Serializable{
    private static final long serialVersionUID = 7446766728L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "sid")
    private Long sid;

    //month = (year-2000)*12 + monthOfYear
    @Column(name = "month")
    private int month;

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
