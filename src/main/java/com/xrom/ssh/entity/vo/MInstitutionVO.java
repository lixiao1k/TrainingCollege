package com.xrom.ssh.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MInstitutionVO implements Serializable{
    private static final long serialVersionUID = 7998346728L;

    private String code;

    private String name;

    private String address;

    private String phone;

    private String description;

    private String password;

    private int coursesNum = 0;

    private int openedCoursesNum = 0;

    //机构获得的总线上支付总额
    private int payedSum = 0;

    //机构总线上退课费总额
    private int droppedSum = 0;

    //机构总线下支付总额
    private int payedOffline = 0;

    //平台需给机构支付多少前，为(payedSum-droppedSum)*7/10 - payedOffline*3/10
    private int payForInstitution;
}
