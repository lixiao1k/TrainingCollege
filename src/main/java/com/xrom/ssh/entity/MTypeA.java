package com.xrom.ssh.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

//平台课程类型统计信息
@Data
@Entity
@Table(name = "MTypeA")
public class MTypeA implements Serializable{

    private static final long serialVersionUID = 72888L;

    @Id
    @Column(name = "type")
    private String type;

    @Column(name = "totalMoney", columnDefinition = "INT default 0")
    private int totalMoney;

    @Column(name = "totalOrders", columnDefinition = "INT default 0")
    private int totalOrders;

}
