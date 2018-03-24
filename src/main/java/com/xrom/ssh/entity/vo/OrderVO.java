package com.xrom.ssh.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderVO implements Serializable{
    private static final Long serialVersionUID = 748378L;

    private Long orderId;

    private Long classId;

    private Long studentId;

    private Long teacherId;

    private Long courseId;

    private String type;

    private String description;

    private String teacherName;

    private String institutionPhone;

    private String teacherPhone;

    private Date createTime;

    private Date payedTime;

    private Date dropTime;

    private Date courseBeginTime;

    private int price;

    private int payment;

    private int amountReturned;

    private int isReserved;

    private int isCancelled;

    private int isPayed;

    private int isPayedOffline;

    private int isUnSubscribed;

}
