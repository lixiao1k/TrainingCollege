package com.xrom.ssh.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "class_id")
    private Long classId;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "price", columnDefinition = "INT default 0")
    private int price;

    @Column(name = "payment", columnDefinition = "INT default 0")
    private int payment;

    @Column(name = "is_reserved", columnDefinition = "INT default 1")
    private int isReserved;

    @Column(name = "is_cancelled", columnDefinition = "INT default 0")
    private int isCancelled;

    @Column(name = "is_payed", columnDefinition = "INT default 0")
    private int isPayed;

    @Column(name = "is_payed_offline", columnDefinition = "INT default 0")
    private int isPayedOffline;
}
