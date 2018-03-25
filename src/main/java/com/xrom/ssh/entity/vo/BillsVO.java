package com.xrom.ssh.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BillsVO implements Serializable{
    private static final long serialVersionUID = 7996755628L;

    private Long orderId;
    //条目操作，是进账还是出账或者是线下支付
    private String action;
    //条目生成时间
    private Date time;
    //金额，进账为正，出账为负
    private int moneyChange;

}
