package com.xrom.ssh.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

//统计地域相关的信息，属性包括地域、总成交额、总成交订单数、总机构数
public class MAreaA implements Serializable{

    private static final long serialVersionUID = 6631238L;

    @Id
    @Column(name = "province")
    private String province;

    @Column(name = "totalMoney")
    private int totalMoney;

    @Column(name = "totalOrders")
    private int totalOrders;

    @Column(name = "totalInstitutions")
    private int totalInstitutions;

}
