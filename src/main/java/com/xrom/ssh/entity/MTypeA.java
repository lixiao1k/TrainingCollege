package com.xrom.ssh.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

//平台课程类型统计信息
public class MTypeA implements Serializable{

    private static final long serialVersionUID = 72888L;

    @Id
    @Column(name = "type")
    private String type;

    @Column(name = "totalMoney")
    private int totalMoney;

    @Column(name = "totalOrders")
    private int totalOrders;

}
