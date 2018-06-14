package com.xrom.ssh.entity;


//@管理信息系统
//统计机构各类型课程成交金额比例

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "ICourseTypeA")
public class ICourseTypeA implements Serializable{
    private static final long serialVersionUID = 1731238L;

    @Id
    @Column(name = "code", length = 7, nullable = false, unique = true)
    private String code;

    @Column(name = "wenOrders", columnDefinition = "INT default 0")
    private int wenOrders;

    @Column(name = "liOrders", columnDefinition = "INT default 0")
    private int liOrders;

    @Column(name = "gongOrders", columnDefinition = "INT default 0")
    private int gongOrders;

    @Column(name = "shangOrders", columnDefinition = "INT default 0")
    private int shangOrders;

    @Column(name = "yiOrders", columnDefinition = "INT default 0")
    private int yiOrders;

    @Column(name = "wenTotalPrice", columnDefinition = "INT default 0")
    private int wenTotalPrice;

    @Column(name = "liTotalPrice", columnDefinition = "INT default 0")
    private int liTotalPrice;

    @Column(name = "gongTotalPrice", columnDefinition = "INT default 0")
    private int gongTotalPrice;

    @Column(name = "shangTotalPrice", columnDefinition = "INT default 0")
    private int shangTotalPrice;

    @Column(name = "yiTotalPrice", columnDefinition = "INT default 0")
    private int yiTotalPrice;

}
