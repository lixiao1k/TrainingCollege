package com.xrom.ssh.entity;

//@管理信息系统
//@统计各个类型课程订单数的比例
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "SOrderTypeA")
public class SOrderTypeA implements Serializable{

    private static final long serialVersionUID = 79728L;

    @Id
    private Long sid;

    //文科订单的数量
    @Column(name = "wenAmount", columnDefinition = "INT default 0")
    private int wenAmount;

    //理科订单的数量
    @Column(name = "liAmount", columnDefinition = "INT default 0")
    private int liAmount;

    //工科订单的数量
    @Column(name = "gongAmount", columnDefinition = "INT default 0")
    private int gongAmount;

    //商学订单的数量
    @Column(name = "shangAmount", columnDefinition = "INT default 0")
    private int shangAmount;

    //医学订单的数量
    @Column(name = "yiAmount", columnDefinition = "INT default 0")
    private int yiAmount;

    //成交订单总数
    @Column(name = "payedOrderTotal", columnDefinition = "INT default 0")
    private int payedOrderTotal;

    //退课订单总数
    @Column(name = "brokenOrderTotal", columnDefinition = "INT default 0")
    private int brokenOrderTotal;
}
