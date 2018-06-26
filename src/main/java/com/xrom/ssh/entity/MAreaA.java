package com.xrom.ssh.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

//统计地域相关的信息，属性包括地域、总成交额、总成交订单数、总机构数
@Data
@Entity
@Table(name = "MAreaA")
public class MAreaA implements Serializable{

    private static final long serialVersionUID = 6631238L;

    @Id
    @Column(name = "province")
    private String province;

    @Column(name = "totalMoney", columnDefinition = "INT default 0")
    private int totalMoney;

    @Column(name = "totalOrders", columnDefinition = "INT default 0")
    private int totalOrders;

    @Column(name = "totalInstitutions", columnDefinition = "INT default 0")
    private int totalInstitutions;

}
